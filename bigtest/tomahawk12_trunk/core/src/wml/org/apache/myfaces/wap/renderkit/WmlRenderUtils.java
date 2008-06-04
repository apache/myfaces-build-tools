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
package org.apache.myfaces.wap.renderkit;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WmlRenderUtils {
    private static Log log = LogFactory.getLog(RendererUtils.class);

    /** Writes options for the select element.
     * @param items is an instance of SelectItem or SelectItemGroup object. Alternatively array, collection or map of SelectItem objects.
     */
    public static void writeOptions(Object items, UIComponent component, ResponseWriter writer) throws java.io.IOException {
        log.debug("method writeOptions");

        if (items instanceof SelectItemGroup){
            log.debug("item is an instanceof SelectItemGroup");
            SelectItemGroup group = (SelectItemGroup)items;

            writer.startElement(Attributes.OPTGROUP,component);
            RendererUtils.writeAttribute(Attributes.TITLE, group.getLabel(), writer);

            SelectItem[] array = group.getSelectItems();
            for (int i = 0; i < array.length; i++)
                writeOption(array[i],component, writer);

            writer.endElement(Attributes.OPTGROUP);
        }
        else {
            if (items instanceof SelectItem){
                log.debug("item is an instance of SelectItem");
                writeOption((SelectItem)items,component, writer);
            }

            if (items instanceof SelectItem[]){
                log.debug("item is an instance of SelectItem[]");
                SelectItem[] array = (SelectItem[])items;
                for (int i = 0; i < array.length; i++)
                    writeOption(array[i],component, writer);
            }

            if (items instanceof Collection){
                log.debug("item is an instance of Collection");
                Iterator iter = ((Collection)items).iterator();
                while(iter.hasNext())
                    writeOptions(iter.next(),component, writer);
            }

            if (items instanceof Map){
                log.debug("item is an instance of Map");
                Iterator iter = ((Map)items).entrySet().iterator();
                while(iter.hasNext())
                    writeOption((SelectItem)iter.next(),component, writer);
            }
        }
    }

    /** Writes one option element for the select tag. */
    public static void writeOption(SelectItem item, UIComponent component, ResponseWriter writer) throws java.io.IOException {
        writer.startElement(Attributes.OPTION,component);
        RendererUtils.writeAttribute(Attributes.VALUE, item.getValue(), writer);
        writer.write(item.getLabel());
        writer.endElement(Attributes.OPTION);
    }
}
