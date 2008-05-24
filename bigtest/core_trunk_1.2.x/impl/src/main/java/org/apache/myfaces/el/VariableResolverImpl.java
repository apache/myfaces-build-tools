/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.el;

import org.apache.myfaces.el.unified.resolver.FacesCompositeELResolver.Scope;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

/**
 * This variable resolver will be used for legacy variable resolvers which are registered through the faces config. If
 * it is invoked through the faces chain it will use the el resolver of {@link Application#getELResolver()}. If it is
 * invoked through the jsp chain it will create an value expression through {@link Application#getExpressionFactory()}
 * to resolve the variable.
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @author Anton Koinov
 * @author Mathias Broekelmann
 * @version $Revision$ $Date$
 */
@SuppressWarnings("deprecation")
public class VariableResolverImpl extends VariableResolver
{
    @Override
    public Object resolveVariable(FacesContext context, String name) throws EvaluationException
    {
        if (context == null)
        {
            throw new NullPointerException("context must not be null");
        }
        if (name == null)
        {
            throw new NullPointerException("name must not be null");
        }

        try
        {
            Scope scope = getScope(context);
            ELContext elcontext = context.getELContext();
            Application application = context.getApplication();
            if (Scope.Faces.equals(scope))
            {
                return application.getELResolver().getValue(elcontext, null, name);
            }
            else if (Scope.JSP.equals(scope))
            {
                ValueExpression expression = application.getExpressionFactory().createValueExpression(elcontext,
                        "#{" + name + "}", Object.class);
                return expression.getValue(elcontext);
            }
            else
            {
                throw new IllegalStateException("unknown scope defined: " + scope);
            }
        }
        catch (ELException e)
        {
            throw new EvaluationException(e.getMessage(), e);
        }
    }

    protected Scope getScope(FacesContext context)
    {
        return (Scope) context.getExternalContext().getRequestMap().get(Scope.class.getName());
    }
}