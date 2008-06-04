/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.custom.dynaForm.metadata.impl.ejb;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.myfaces.custom.dynaForm.annot.ui.DisplayOnly;
import org.apache.myfaces.custom.dynaForm.annot.ui.IgnoreProperty;
import org.apache.myfaces.custom.dynaForm.annot.ui.ReadOnly;
import org.apache.myfaces.custom.dynaForm.annot.ui.UIComponent;
import org.apache.myfaces.custom.dynaForm.annot.ui.Min;
import org.apache.myfaces.custom.dynaForm.annot.ui.Max;
import org.apache.myfaces.custom.dynaForm.annot.ui.Range;
import org.apache.myfaces.custom.dynaForm.annot.ui.NotNull;
import org.apache.myfaces.custom.dynaForm.annot.ui.Length;
import org.apache.myfaces.custom.dynaForm.annot.ui.DataProvider;
import org.apache.myfaces.custom.dynaForm.metadata.Extractor;
import org.apache.myfaces.custom.dynaForm.metadata.MetaData;
import org.apache.myfaces.custom.dynaForm.metadata.RelationType;
import org.apache.myfaces.custom.dynaForm.metadata.Selection;

/**
 * Extract metadata from ejb3 beans
 */
public class EjbExtractor implements Extractor
{
	private final static Set<String> SYSTEM_METHODS = new TreeSet<String>(
			Arrays.asList(new String[]
			{ "hashCode", "getClass", "wait", "equals", "notify", "notifyAll",
					"toString" }));

	protected static class ContextInfo
	{
		private Boolean accessField;
		private String name;
		private boolean embedded;

		protected ContextInfo(final String name, final Boolean accessField, final boolean embedded)
		{
			super();
			this.name = name;
			this.accessField = accessField;
			this.embedded = embedded;
		}
	}

	protected static class Context
	{
		private boolean accessField = false;
		private Stack<ContextInfo> accessFields = new Stack<ContextInfo>();
		private int embeddLevel = 0;

		public void setAccessField(boolean accessField)
		{
			this.accessField = accessField;
		}

		public Boolean popAccessType()
		{
			return accessFields.pop().accessField;
		}

		public boolean getAccessField()
		{
			return accessField;
		}

		protected void startEmbedded(final String name, final boolean embedded)
		{
			embeddLevel++;

			String contextName = name;
			if (!accessFields.isEmpty())
			{
				contextName = accessFields.peek().name + "." + name;
			}

			accessFields.push(new ContextInfo(contextName, accessField, embedded));
		}

		protected void endEmbedded()
		{
			embeddLevel--;
			accessField = popAccessType();
		}

		public String getContextName()
		{
			if (accessFields.isEmpty())
			{
				return null;
			}

			return accessFields.peek().name;
		}

		public boolean isEmbedded()
		{
			if (accessFields.isEmpty())
			{
				return true;
			}

			return accessFields.peek().embedded;
		}
	}

	public EjbExtractor()
	{
	}

	/**
	 * the entity name as string
	 */
	public void getMetaData(MetaData metaData, Object entity)
	{
		if (!(entity instanceof String))
		{
			throw new IllegalArgumentException("passed entity argument not a string: " + entity);
		}

		Class entityClass;
		try
		{
			entityClass = Class.forName(entity.toString());
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

		Context context = new Context();

		create(context, metaData, entityClass);
	}

	/**
	 * get all super classes needed to be parsed.
	 */
	protected void createClassList(List<Class> classes, Class clazz)
	{
		Class superClass = clazz.getSuperclass();
		if (superClass != null && !superClass.equals(Object.class))
		{
			createClassList(classes, superClass);
		}

		classes.add(clazz);
	}

	/**
	 * create the metadata for the given class
	 */
	@SuppressWarnings("unchecked")
	protected void create(Context context, MetaData metaData, Class entityClass)
	{
		/* do not check if this is really a entity. this allows us to parse any given bean
		if (!entityClass.isAnnotationPresent(Entity.class))
		{
			throw new IllegalArgumentException(entityClass.getName()
					+ " is not a ejb3 bean");
		}
		*/

		List<Class> classes = new ArrayList<Class>(10);
		createClassList(classes, entityClass);

		for (Class clazz : classes)
		{
			boolean accessByField = context.getAccessField();

			Boolean determinedAccessByField = determineAccessByField(entityClass);
			if (determinedAccessByField != null)
			{
				accessByField = determinedAccessByField.booleanValue();
			}

			if (accessByField)
			{
				context.setAccessField(true);
				initFromFields(context, metaData, getFields(clazz));
			}
			else
			{
				context.setAccessField(false);
				initFromMethods(context, metaData, getMethods(clazz));
			}
		}
	}

	protected Boolean determineAccessByField(Class clazz)
	{
		Class checkClass = clazz;
		while (checkClass != null && !checkClass.equals(Object.class))
		{
			Method[] methods = checkClass.getDeclaredMethods();
			for (Method method : methods)
			{
				if (method.isSynthetic())
				{
					continue;
				}

				if (method.isAnnotationPresent(Id.class) || method.isAnnotationPresent(EmbeddedId.class))
				{
					return Boolean.FALSE;
				}
			}

			Field[] fields = checkClass.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.isSynthetic())
				{
					continue;
				}

				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class))
				{
					return Boolean.TRUE;
				}
			}

			checkClass = checkClass.getSuperclass();
		}

		return null;
	}

	protected Method[] getMethods(Class entityClass)
	{
		return ClassHelperFactory.get().getMethods(entityClass);
	}

	protected Field[] getFields(Class entityClass)
	{
		return ClassHelperFactory.get().getFields(entityClass);
	}

	/**
	 * ejb3 access through fields
	 */
	protected void initFromFields(Context context, MetaData metaData, Field[] fields)
	{
		for (Field field : fields)
		{
			if (!validModifier(field.getModifiers(), false)
					|| field.isSynthetic()
					|| hasAnnotationTransient(field))
			{
				continue;
			}
			String name = field.getName();
			Class type = field.getType();

			if (metaData.processField(createFullName(context, name)))
			{
				processField(context, metaData, field, name, type, true, true);
			}
		}
	}

	/**
	 * process the given field - or ist superclass if it is embedded
	 */
	protected void processField(Context context, MetaData metaData, AccessibleObject accessibleObject, String name, Class<?> type, Boolean canRead, Boolean canWrite)
	{
		if (accessibleObject.isAnnotationPresent(IgnoreProperty.class))
		{
			// skip this field
			return;
		}

		// embeddable if its annotation with @embedded - also check of @id, it might be a composite-key
		if (processEmbedded(context, metaData, accessibleObject, name, type))
		{
			return;
		}

		if (metaData.processFieldParent(name))
		{
			// we processed this field due to the fact that it was the parent of a requestedField
			embeddEntity(context, metaData, name, type);
			// now exit
			return;
		}

		MetaData.FieldImpl mdField = metaData.getOrCreateField(createFullName(context, name));
		mdField.setType(type);
		if (canRead != null && mdField.getCanRead() == null)
		{
			mdField.setCanRead(canRead);
		}
		if (canWrite != null && mdField.getCanWrite() == null)
		{
			mdField.setCanWrite(canWrite);
		}
		mdField.setEmbedded(context.isEmbedded());
		initFromType(context, mdField, type);
		initFromAnnotations(context, mdField, accessibleObject);
	}

	protected boolean processEmbedded(Context context, MetaData metaData, AccessibleObject accessibleObject, String name, Class<?> type)
	{
		if (accessibleObject.isAnnotationPresent(Embedded.class) || accessibleObject.isAnnotationPresent(Id.class))
		{
			if (type.isAnnotationPresent(Embeddable.class) || type.isAnnotationPresent(MappedSuperclass.class))
			{
				// process embedded type
				try
				{
					context.startEmbedded(name, true);
					create(context, metaData, type);
				}
				finally
				{
					context.endEmbedded();
				}
				return true;
			}
		}

		return false;
	}

	/**
	 * check if we should embedd this entity
	 */
	protected boolean checkEmbeddEntity(Context context, MetaData metaData, String name)
	{
		String checkName = createFullName(context, name) + ".";

		for (String fieldName : metaData.getRequestedFields())
		{
			if (fieldName.startsWith(checkName))
			{
				return true;
			}
		}

		return false;
	}

	protected String createFullName(Context context, String name)
	{
		if (context.getContextName() != null)
		{
			return context.getContextName() + "." + name;
		}
		else
		{
			return name;
		}
	}

	/**
	 * embedd this entity
	 */
	protected void embeddEntity(Context context, MetaData metaData, String name, Class entityType)
	{
		// process embedded type
		boolean previousLock = false;
		try
		{
			boolean processAll = metaData.getRequestedFields().contains(createFullName(context, name + ".*"));
			if (!processAll)
			{
				previousLock = metaData.setLockFields(true);
			}
			context.startEmbedded(name, false);
			create(context, metaData, entityType);
		}
		finally
		{
			context.endEmbedded();
			metaData.setLockFields(previousLock);
		}
	}

	/**
	 * init metadata from annotations
	 */
	protected void initFromAnnotations(Context context, MetaData.FieldImpl mdField, AccessibleObject accessibleObject)
	{
		if (accessibleObject.isAnnotationPresent(DisplayOnly.class))
		{
			// display only
			mdField.setDisplayOnly(true);
		}
		if (accessibleObject.isAnnotationPresent(ReadOnly.class))
		{
			ReadOnly readOnly = accessibleObject.getAnnotation(ReadOnly.class);

			// read-only only
			mdField.setCanWrite(false);
			if (readOnly.disabled())
			{
				mdField.setDisabled(true);
			}
		}

		if (accessibleObject.isAnnotationPresent(UIComponent.class))
		{
			UIComponent component = accessibleObject.getAnnotation(UIComponent.class);
			mdField.setWantedComponentType(component.type());
		}

		if (accessibleObject.isAnnotationPresent(Column.class))
		{
			// is required
			Column column = accessibleObject.getAnnotation(Column.class);
			mdField.setRequired(!column.nullable());
		}

		if (accessibleObject.isAnnotationPresent(Id.class))
		{
			// id column cant be written if its a generated value
			if (accessibleObject.isAnnotationPresent(GeneratedValue.class))
			{
				setSpecialFieldDisabled(mdField);
			}
		}

		if (accessibleObject.isAnnotationPresent(Version.class))
		{
			setSpecialFieldDisabled(mdField);
		}

		if (accessibleObject.isAnnotationPresent(OneToOne.class))
		{
			mdField.setRelationType(RelationType.ONE_TO_ONE);
		}
		if (accessibleObject.isAnnotationPresent(OneToMany.class))
		{
			mdField.setRelationType(RelationType.ONE_TO_MANY);
		}
		if (accessibleObject.isAnnotationPresent(ManyToOne.class))
		{
			mdField.setRelationType(RelationType.MANY_TO_ONE);
		}
		if (accessibleObject.isAnnotationPresent(ManyToMany.class))
		{
			mdField.setRelationType(RelationType.MANY_TO_MANY);
		}
		if (accessibleObject.isAnnotationPresent(DataProvider.class))
		{
			DataProvider dataProvider = accessibleObject.getAnnotation(DataProvider.class);
			mdField.setDataSource(dataProvider.value());
			mdField.setDataSourceDescription(dataProvider.description());
		}

		// get Temporal from model ...
		if (accessibleObject.isAnnotationPresent(Temporal.class))
		{
			Temporal temporal = accessibleObject.getAnnotation(Temporal.class);
			mdField.setTemporalType(temporal.value());
		}
		// ... but override with our own Temporal if required
		if (accessibleObject.isAnnotationPresent(org.apache.myfaces.custom.dynaForm.annot.ui.Temporal.class))
		{
			org.apache.myfaces.custom.dynaForm.annot.ui.Temporal temporal = accessibleObject.getAnnotation(org.apache.myfaces.custom.dynaForm.annot.ui.Temporal.class);
			mdField.setTemporalType(temporal.value());
		}

		Class<?> type = mdField.getType();
		if (type.isAnnotationPresent(Entity.class))
		{
			mdField.setEntityType(true);
		}

        if (accessibleObject.isAnnotationPresent(Min.class))
		{
			Min annot = accessibleObject.getAnnotation(Min.class);
			mdField.setMinValue((double) annot.value());
		}
		if (accessibleObject.isAnnotationPresent(Max.class))
		{
			Max annot = accessibleObject.getAnnotation(Max.class);
			mdField.setMaxValue((double) annot.value());
		}
		if (accessibleObject.isAnnotationPresent(Length.class))
		{
			Length annot = accessibleObject.getAnnotation(Length.class);
			mdField.setMinSize(annot.min());
			mdField.setMaxSize(annot.max());
		}
		if (accessibleObject.isAnnotationPresent(NotNull.class))
		{
			mdField.setRequired(true);
		}
		if (accessibleObject.isAnnotationPresent(Range.class))
		{
			Range annot = accessibleObject.getAnnotation(Range.class);
			mdField.setMinValue((double) annot.min());
			mdField.setMaxValue((double) annot.max());
		}
	}

	/**
	 * configure a special fields as disabled. e.g. used for Id, Version, ....
	 */
	protected void setSpecialFieldDisabled(MetaData.FieldImpl mdField)
	{
		mdField.setCanWrite(false);
		mdField.setDisabled(true);
	}

	/**
	 * ejb3 access through methods (properties)
	 */
	protected void initFromMethods(Context context, MetaData metaData, Method[] methods)
	{
		for (Method method : methods)
		{
			if (!validModifier(method.getModifiers(), true)
					|| method.isSynthetic()
					|| hasAnnotationTransient(method)
					|| SYSTEM_METHODS.contains(method.getName()))
			{
				continue;
			}
			String methodName = method.getName();
			String propertyName = convertMethodName(methodName);

			if (!metaData.processField(createFullName(context, propertyName)))
			{
				continue;
			}

			if (methodName.startsWith("get") || methodName.startsWith("is"))
			{
				Class[] parameters = method.getParameterTypes();
				if (parameters != null && parameters.length > 0)
				{
					// not a bean getter
					continue;
				}

				processField(context, metaData, method, propertyName, method.getReturnType(), true, null);
			}
			else if (methodName.startsWith("set"))
			{
				if (!void.class.equals(method.getReturnType()) && !Void.class.equals(method.getReturnType()))
				{
					// not a bean setter
					continue;
				}

				Class[] parameters = method.getParameterTypes();
				if (parameters != null && parameters.length != 1)
				{
					// not a bean setter
					continue;
				}

				if (metaData.processField(createFullName(context, propertyName)))
				{
					processField(context, metaData, method, propertyName, method.getParameterTypes()[0], null, true);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void initFromType(Context context, org.apache.myfaces.custom.dynaForm.metadata.MetaData.FieldImpl mdField, Class type)
	{
		if (type.isEnum())
		{
			EnumSet es = EnumSet.allOf(type);
			Object[] enums = es.toArray(new Object[]{es.size()});

			Selection[] selections = new Selection[enums.length];
			for (int i = 0; i<enums.length; i++)
			{
				Enum e = (Enum) enums[i];
				selections[i] = new Selection(e.name(), e);
			}

			mdField.setAllowedSelections(selections);
		}
		/*
		else if (Number.class.isAssignableFrom(type))
		{
		}        1
		*/
	}

	/**
	 * get rid of get/set/is in method names
	 */
	protected String convertMethodName(String name)
	{
		if (name.startsWith("get"))
		{
			name = name.substring("get".length());
		}
		else if (name.startsWith("set"))
		{
			name = name.substring("set".length());
		}
		else if (name.startsWith("is"))
		{
			name = name.substring("is".length());
		}
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}

	/**
	 * skip method/fields annotated with transient
	 */
	protected boolean hasAnnotationTransient(AccessibleObject accessibleObject)
	{
		return accessibleObject.isAnnotationPresent(Transient.class);
	}

	/**
	 * skip method/fields marked as static/transient
	 */
	protected boolean validModifier(int modifier, boolean isMethod)
	{
		if (isMethod && !Modifier.isPublic(modifier))
		{
			return false;
		}
		if (Modifier.isStatic(modifier))
		{
			return false;
		}
		if (Modifier.isTransient(modifier))
		{
			return false;
		}

		return true;
	}
}
