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
package org.apache.myfaces.custom.jslistener;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JsValueChangeListenerTag
        extends UIComponentTagBase
{

    public String getComponentType()
    {
        return JsValueChangeListener.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.JsValueChangeListener";
    }

    private String _for;
    private String _property;
    private String _expressionValue;
    private String _bodyTagEvent;

    public void release() {
        super.release();
        _for=null;
        _property=null;
        _expressionValue=null;
        _bodyTagEvent=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "for", _for);
        setStringProperty(component, "property", _property);
        setStringProperty(component, "expressionValue", _expressionValue);
        setStringProperty(component, "bodyTagEvent", _bodyTagEvent);
    }

    public void setExpressionValue(String expressionValue)
    {
        _expressionValue = expressionValue;
    }

    public void setFor(String aFor)
    {
        _for = aFor;
    }

    public void setProperty(String property)
    {
        _property = property;
    }

    public void setBodyTagEvent(String bodyTagEvent)
    {
        _bodyTagEvent = bodyTagEvent;
    }

    /*

//private static final Log log = LogFactory.getLog(UpdateActionListenerTag.class);

private String _for;
private String _property;
private String _expressionValue;
private static Log log = LogFactory.getLog(JsValueChangeListenerTag.class);

public JsValueChangeListenerTag()
{
}

public void setFor(String aFor)
{
_for = aFor;
}

public void setExpressionValue(String expressionValue)
{
_expressionValue = expressionValue;
}

public void setProperty(String property)
{
_property = property;
}


public int doStartTag() throws JspException
{
try
{
if (_for == null) throw new JspException("for attribute not set");
if (_expressionValue == null) throw new JspException("expressionValue attribute not set");

//Find parent UIComponentTag
UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
if (componentTag == null)
{
throw new JspException("ValueChangeListenerTag has no UIComponentTag ancestor");
}

if (componentTag.getCreated())
{
String aFor = getValueOrBinding(_for);
String expressionValue = getValueOrBinding(_expressionValue);
String property = getValueOrBinding(_property);

AddResource.addJavaScriptAtPosition(
      JsValueChangeListenerTag.class, "JSListener.js", false, getFacesContext());

//Component was just created, so we add the Listener
UIComponent component = componentTag.getComponentInstance();

if(aFor!=null)
{
  UIComponent forComponent = component.findComponent(aFor);

  String forComponentId = null;

  if (forComponent == null)
  {
      if (log.isInfoEnabled())
      {
          log.info("Unable to find component '" + aFor + "' (calling findComponent on component '" + component.getClientId(getFacesContext()) + "') - will try to render component id based on the parent-id (on same level)");
      }
      if (aFor.length() > 0 && aFor.charAt(0) == UINamingContainer.SEPARATOR_CHAR)
      {
          //absolute id path
          forComponentId = aFor.substring(1);
      }
      else
      {
          //relative id path, we assume a component on the same level as the label component
          String labelClientId = component.getClientId(getFacesContext());
          int colon = labelClientId.lastIndexOf(UINamingContainer.SEPARATOR_CHAR);
          if (colon == -1)
          {
              forComponentId = aFor;
          }
          else
          {
              forComponentId = labelClientId.substring(0, colon + 1) + aFor;
          }
      }
  }
  else
  {
      forComponentId = forComponent.getClientId(getFacesContext());
  }

  expressionValue = expressionValue.replaceAll("\\'","\\\\'");
  expressionValue = expressionValue.replaceAll("\"","\\\"");


  String methodCall = "orgApacheMyfacesJsListenerSetExpressionProperty('"+
          component.getClientId(getFacesContext())+"','"+
          forComponentId+"',"+
          (property==null?"null":"'"+property+"'")+
          ",'"+expressionValue+"');";


  callMethod(component, "onchange",methodCall);

}
}

return Tag.SKIP_BODY;
}
catch(JspException ex)
{
log.error("Exception : ",ex);
throw ex;
}
}

private String getValueOrBinding(String valueOrBinding)
{
if(valueOrBinding == null)
return null;

String value = valueOrBinding;

if (UIComponentTag.isValueReference(valueOrBinding))
{
ValueBinding binding = getApplication().createValueBinding(valueOrBinding);
Object val = binding.getValue(getFacesContext());
value = (val==null?"":val.toString());
}

return value;
}

private void callMethod(UIComponent uiComponent, String propName, String value)
{
Object oldValue = uiComponent.getAttributes().get(propName);

if(oldValue != null)
{
String oldValueStr = oldValue.toString().trim();

//check if method call has already been added...
if(oldValueStr.indexOf(value)!=-1)
return;

if(oldValueStr.length()>0 && !oldValueStr.endsWith(";"))
oldValueStr +=";";

value = oldValueStr + value;

}

uiComponent.getAttributes().put(propName, value);
}

protected Application getApplication()
{
return getFacesContext().getApplication();
}

protected FacesContext getFacesContext()
{
return FacesContext.getCurrentInstance();
}           */
}


