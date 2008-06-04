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
package org.apache.myfaces.custom.dynaForm.metadata.impl.hibernate;

import java.lang.reflect.AccessibleObject;

import org.apache.myfaces.custom.dynaForm.metadata.MetaData.FieldImpl;
import org.apache.myfaces.custom.dynaForm.metadata.impl.ejb.EjbExtractor;

/**
 * Extract hibernate specific data 
 */
public class HibernateExtractor extends EjbExtractor
{
	@Override
	protected void initFromAnnotations(Context context, FieldImpl mdField, AccessibleObject accessibleObject)
	{
        throw new UnsupportedOperationException();

        /*
		super.initFromAnnotations(context, mdField, accessibleObject);

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
		*/
	}
}
