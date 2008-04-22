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
package org.apache.myfaces.custom.dynaForm.component.dynaForm;

import org.apache.myfaces.custom.dynaForm.guiBuilder.Slipstream;
import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf.JsfGuiBuilder;
import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf.JsfGuiBuilderFactory;
import org.apache.myfaces.custom.dynaForm.guiBuilder.impl.jsf.NewComponentListener;
import org.apache.myfaces.custom.dynaForm.jsfext.ComponentUtils;
import org.apache.myfaces.custom.dynaForm.lib.ViewType;
import org.apache.myfaces.custom.dynaForm.metadata.MetaData;
import org.apache.myfaces.custom.dynaForm.metadata.impl.jsf.JsfExclusiveExtractor;
import org.apache.myfaces.custom.dynaForm.metadata.impl.jsf.JsfExtractor;
import org.apache.myfaces.custom.dynaForm.metadata.impl.jsf.JsfRequestFieldExtractor;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * the dynaForm renderer<br />
 * regardles what the component told you, this is where the work happens ;-)
 */
public class DynaFormRenderer extends Renderer
{
	public final static String CONTEXT_GUI_BUILDER = "org.apache.myfaces.custom.dynaForm.GUI_BUILDER";

	private final class AddComponentToTable implements NewComponentListener
	{
		private final UIComponent destCmp;

		private AddComponentToTable(UIComponent component)
		{
			super();
			this.destCmp = component;
		}

		@SuppressWarnings("unchecked")
		public void newComponent(String fieldName, UIComponent label, UIComponent component)
		{
			component.getAttributes().put(
					DynaForm.DYNA_FORM_CREATED, Boolean.TRUE);

			UIColumn column = new UIColumn();
			column.getChildren().add(component);
			column.setHeader(label);

			int addPos = -1;
			UIComponent dataIndicator = this.destCmp.findComponent("data");
			if (dataIndicator != null)
			{
				addPos = this.destCmp.getChildren().indexOf(dataIndicator);
			}

			if (addPos > -1)
			{
				this.destCmp.getChildren().add(addPos, column);
			}
			else
			{
				this.destCmp.getChildren().add(column);
			}
		}
	}

	protected static class AddComponentSimple implements NewComponentListener
	{
		private final UIComponent destCmp;

		protected AddComponentSimple(UIComponent component)
		{
			super();
			this.destCmp = component;
		}

		@SuppressWarnings("unchecked")
		public void newComponent(String fieldName, UIComponent label, UIComponent component)
		{
			component.getAttributes().put(
					DynaForm.DYNA_FORM_CREATED, Boolean.TRUE);

			this.destCmp.getChildren().add(label);
			this.destCmp.getChildren().add(component);
		}
	}

	@Override
	public boolean getRendersChildren()
	{
		return true;
	}

	/**
	 * on create view or in development mode this will build the component tree
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException
	{
		super.encodeBegin(context, component);

		DynaForm dynaForm = (DynaForm) component;

		UIComponent layoutComponent = findLayoutComponent(dynaForm);
		ViewType viewType = getViewType(layoutComponent);

		// create & add components
		boolean needAdd = processPreviouslyAdded(context, layoutComponent);
		if (needAdd)
		{
			addComponents(context, dynaForm, layoutComponent, viewType);
		}
	}

	/**
	 * determine the current view type
	 * <ul>
	 * <li>"list" means: the layout component "is a" or "is embedded in an" list component (UIData)</li>
	 * <li>"form" means: anything else</li>
	 * </ul>
	 */
	protected ViewType getViewType(UIComponent startComponent)
	{
        UIComponent component = startComponent;
        while (component != null && !(component instanceof DynaForm))
        {
            if (isTable(component))
            {
                return ViewType.LIST;
            }

            component = component.getParent();
        }

        return ViewType.FORM;
	}

	protected UIComponent findLayoutComponent(DynaForm dynaForm)
	{
		UIComponent layoutComponent = findComponentEx(dynaForm, dynaForm.getVar() + "-layout");
		if (layoutComponent == null)
		{
			throw new IllegalStateException("DynaForm '" + dynaForm.getId()
					+ "' has no layout component (id=\"layout\")");
		}
		return layoutComponent;
	}

	/**
	 * find a component "by id" by simply traversing the tree
	 */
	protected UIComponent findComponentEx(UIComponent base, String id)
	{
		if (id.equals(base.getId()))
		{
			return base;
		}

		Iterator iterChildren = base.getFacetsAndChildren();
		while (iterChildren.hasNext())
		{
			UIComponent child = (UIComponent) iterChildren.next();
			UIComponent found = findComponentEx(child, id);
			if (found != null)
			{
				return found;
			}
		}

		return null;
	}

	/**
	 * collect all input components recursiv starting with <code>component</code>
	 */
	protected void collectInputComponents(List<UIInput> components, UIComponent baseComponent)
	{
		if (baseComponent instanceof UIInput)
		{
			components.add((UIInput) baseComponent);
		}

		if (baseComponent.getChildren() == null)
		{
			return;
		}

		for (Object component : baseComponent.getChildren())
		{
			UIComponent componentChild = (UIComponent) component;
			collectInputComponents(components, componentChild);
		}
	}

	/**
	 * create and add the components to the layout component
	 */
	protected void addComponents(final FacesContext context,
			final DynaForm dynaForm, final UIComponent layoutComponent,
			final ViewType viewType)
	{
		MetaData metaData = extractMetaData(dynaForm);

		Slipstream slipstream = new Slipstream();
		slipstream.setModelMetaData(metaData);
		slipstream.setDisplayOnly(dynaForm.isDisplayOnly());

		if (dynaForm.getBundle() != null)
		{
			// get the bundle and attach it
			Map bundleMap = (Map) context.getApplication().createValueBinding("#{" + dynaForm.getBundle() + "}").getValue(context);
			slipstream.setLabelBundle(bundleMap);
		}

		JsfGuiBuilder guiBuilder = createGuiBuilder(context);

		guiBuilder.setContext(context);
		guiBuilder.setBackingBeanPrefix(dynaForm.getVar());

		if (isTable(layoutComponent))
		{
			guiBuilder.setNewComponentListener(new AddComponentToTable(layoutComponent));
		}
		else
		{
			guiBuilder.setNewComponentListener(new AddComponentSimple(layoutComponent));
		}

        String valueBindingPrefix = getValueBindingPrefix(dynaForm, layoutComponent);
        if (valueBindingPrefix == null)
        {
            throw new UnsupportedOperationException("can't determine the value binding prefix");
        }
        guiBuilder.setBackingEntityPrefix(valueBindingPrefix);

		slipstream.setGuiBuilder(guiBuilder);
		slipstream.process();
	}

    private boolean isTable(UIComponent layoutComponent)
    {
        return layoutComponent instanceof UIData || UIData.COMPONENT_FAMILY.equals(layoutComponent.getFamily());
    }

    protected String getValueBindingPrefix(DynaForm dynaForm, UIComponent layoutComponent)
    {
        String valueBindingPrefix = dynaForm.getValueBindingPrefix();
        if (valueBindingPrefix == null)
        {
            valueBindingPrefix = (String) layoutComponent.getAttributes().get("var");
        }

        return valueBindingPrefix;
    }

    private MetaData extractMetaData(final DynaForm dynaForm)
	{
		MetaData metaData = new MetaData();

		try
		{
			// lookup which fields to process
			new JsfRequestFieldExtractor().getMetaData(metaData, dynaForm);

			if (dynaForm.isExclusiveFields())
			{
				// the same as above, but keep their ordering and some additional metadata
				new JsfExclusiveExtractor().getMetaData(metaData, dynaForm);
				metaData.setLockFields(true);
			}

			// use the users extractor
			dynaForm.getExtractor().getMetaData(metaData, dynaForm.getConfiguration().getEntity());

			// processs the jsf stuff
			new JsfExtractor().getMetaData(metaData, dynaForm);

		}
		finally
		{
			metaData.setLockFields(false);
		}

		return metaData;
	}

	protected JsfGuiBuilder createGuiBuilder(final FacesContext facesContext)
	{
		return JsfGuiBuilderFactory.create(facesContext);
	}

	/**
	 * check if we already added components to the layout component.<br />
	 *
	 * if this is the case then:
	 * <ul>
	 * <li>keep them cached and avoid readd</li>
	 * <li><strike>in development mode: remove the components</strike></li>
	 * <li><strike>in production mode: keep them cached</strike></li>
	 * </ul>
	 *
	 * TODO: need to figure out whats the best way to recreate the components
	 * @return true if we have to add our components again
	 */
	protected boolean processPreviouslyAdded(FacesContext context,
			UIComponent root)
	{
		List rootComponentChildren = root.getChildren();
		if (rootComponentChildren != null)
		{
			for (Object component : rootComponentChildren)
			{
				UIComponent child = (UIComponent) component;
				if (Boolean.TRUE.equals(child.getAttributes().get(
					DynaForm.DYNA_FORM_CREATED)))
				{
					return false;
				}

				if (!processPreviouslyAdded(context, child))
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException
	{
		// TODO: remove this (and maybe the method at all) afer myfaces > 1.1.1
		// is out ==>
		ComponentUtils.renderChildren(context, component);
		// TODO: remove this afer myfaces > 1.1.1 is out <==
	}
}