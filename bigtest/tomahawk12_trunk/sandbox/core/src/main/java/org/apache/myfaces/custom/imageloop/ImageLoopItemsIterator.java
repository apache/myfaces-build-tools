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
package org.apache.myfaces.custom.imageloop;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Image items iterator.
 * @author Felix Röthenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class ImageLoopItemsIterator implements Iterator {

    private final Iterator _childs;
    private Iterator _nestedItems;
    private Object _nextItem;
    private ImageLoopItems _currentImageLoopItems;
    private String _collectionLabel;
    
    public ImageLoopItemsIterator(UIComponent htmlImageLoopItemsParent) {
        _childs = htmlImageLoopItemsParent.getChildren().iterator();
    }

    public boolean hasNext() {
        if(_nextItem != null)
        {
            return true;
        }
        if(_nestedItems != null)
        {
            if(_nestedItems.hasNext())
            {
                return true;
            }
            _nestedItems = null;
        }
        if (_childs.hasNext())
        {
            UIComponent child = (UIComponent) _childs.next();
            if (child instanceof UIGraphic)
            {
                UIGraphic uiGraphic = (UIGraphic) child;
                // UIGraphic.getUrl() is an alias for UIGraphic.getValue()
                String url = uiGraphic.getUrl();
                _nextItem = new GraphicItem(url);
                return true;
            }
            else if (child instanceof ImageLoopItems)
            {
                _currentImageLoopItems = ((ImageLoopItems) child);
                Object value = _currentImageLoopItems.getValue();

                if (value instanceof GraphicItem)
                {
                    _nextItem = value;
                    return true;
                }
                else if (value instanceof GraphicItem[])
                {
                    _nestedItems = Arrays.asList((GraphicItem[]) value)
                                    .iterator();
                    _collectionLabel = "Array";
                    return hasNext();
                }
                else if (value instanceof Collection)
                {
                    _nestedItems = ((Collection)value).iterator();
                    _collectionLabel = "Collection";
                    return hasNext();
                }
                else
                {
                    ValueBinding binding = _currentImageLoopItems.getValueBinding("value");

                    throw new IllegalArgumentException(
                        "Value binding '"
                        + (binding == null ? null : binding
                                        .getExpressionString())
                        + "'of ImageLoopItems with component-path "
                        + RendererUtils.getPathToComponent(child)
                        + " does not reference an Object of type GraphicItem, GraphicItem[] or Collection but of type : "
                        + ((value == null) ? null : value.getClass().getName()));
                }
            }
            else
            {
            }
        }
        return false;

    }

    public Object next() {
        if (!hasNext())
        {
            throw new NoSuchElementException();
        }
        if(_nextItem != null)
        {
            Object value = _nextItem;
            _nextItem = null;
            return value;
        }
        if (_nestedItems != null)
        {
            Object item = _nestedItems.next();
            if (!(item instanceof GraphicItem))
            {
                ValueBinding binding = _currentImageLoopItems
                                .getValueBinding("value");
                throw new IllegalArgumentException(
                _collectionLabel + " referenced by ImageLoopItems with binding '"
                + binding.getExpressionString()
                + "' and Component-Path : " + RendererUtils.getPathToComponent(_currentImageLoopItems)
                + " does not contain objects of type GraphicItem");
            }
            return item;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
