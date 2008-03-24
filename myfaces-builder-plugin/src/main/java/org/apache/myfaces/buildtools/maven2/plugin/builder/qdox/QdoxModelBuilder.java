package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ModelItem;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorModel;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;

public class QdoxModelBuilder implements ModelBuilder
{

    private static final String DOC_CONVERTER = "mfp.converter";
    private static final String DOC_VALIDATOR = "mfp.validator";
    private static final String DOC_COMPONENT = "mfp.component";
    private static final String DOC_RENDERER = "mfp.renderer";
    private static final String DOC_RENDERKIT = "mfp.renderkit";

    private static final String DOC_PROPERTY = "mfp.property";

    /**
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, MavenProject project)
            throws MojoExecutionException
    {
        buildModel(model, project.getCompileSourceRoots());
    }

    /**
     * Scan the source tree for doc-annotations, and build Model objects
     * containing info extracted from the doc-annotation attributes and
     * introspected info about the item the annotation is attached to.
     */
    public void buildModel(Model model, List sourceDirs)
            throws MojoExecutionException
    {
        JavaDocBuilder builder = new JavaDocBuilder();

        // need a File object representing the original source tree
        System.out.println("CompileSourceRoots begin..");
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            String srcDir = (String) i.next();
            System.out.println("==" + srcDir);
            builder.addSourceTree(new File(srcDir));
        }
        System.out.println("CompileSourceRoots end..");

        JavaSource[] sources = builder.getSources();
        JavaClass[] classes = builder.getClasses();
        Set processedClasses = new HashSet();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass clazz = classes[i];
            processClass(processedClasses, clazz, model);
        }
    }

    /**
     * Set the parentClassName and interfaceClassNames properties of the
     * provided modelItem object.
     */
    private void processClass(Set processedClasses, JavaClass clazz, Model model)
            throws MojoExecutionException
    {
        if (processedClasses.contains(clazz))
        {
            return;
        }

        // first, check that the parent type and all interfaces have been
        // processed.
        JavaClass parentClazz = clazz.getSuperJavaClass();
        if (parentClazz != null)
        {
            processClass(processedClasses, parentClazz, model);
        }

        JavaClass[] classes = clazz.getImplementedInterfaces();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass iclazz = classes[i];
            processClass(processedClasses, iclazz, model);
        }

        // ok, now we can mark this class as processed.
        processedClasses.add(clazz);

        System.out.println("class:" + clazz.getName());

        DocletTag tag;

        tag = clazz.getTagByName(DOC_CONVERTER, false);
        if (tag != null)
        {
            processConverter(tag, clazz, model);
        }

        tag = clazz.getTagByName(DOC_VALIDATOR, false);
        if (tag != null)
        {
            processValidator(tag, clazz, model);
        }

        tag = clazz.getTagByName(DOC_COMPONENT, false);
        if (tag != null)
        {
            processComponent(tag, clazz, model);
        }
    }

    /**
     * Remove all leading whitespace and a quotemark if it exists.
     * <p>
     * Qdox comments like <code>@foo val= "bar"</code> return a value with leading whitespace and
     *      quotes, so remove them.
     */
    private String clean(String src)
    {
        if (src == null)
        {
            return null;
        }

        int start = 0;
        int end = src.length();
        while (start <= end)
        {
            char c = src.charAt(start);
            if (!Character.isWhitespace(c) && (c != '"'))
            {
                break;
            }
            ++start;
        }
        while (end >= start)
        {
            char c = src.charAt(end - 1);
            if (!Character.isWhitespace(c) && (c != '"'))
            {
                break;
            }
            --end;
        }
        return src.substring(start, end);
    }

    /**
     * Get the named attribute from a doc-annotation.
     * 
     * @param clazz
     *            is the class the annotation is attached to; only used when
     *            reporting errors.
     */
    private String getString(JavaClass clazz, String key, DocletTag tag,
            String dflt)
    {
        String val = clean(tag.getNamedParameter(key));
        if (val != null)
        {
            return val;
        }
        else
        {
            return dflt;
        }
    }

    /**
     * Get the named attribute from a doc-annotation and convert to a boolean.
     * 
     * @param clazz
     *            is the class the annotation is attached to; only used when
     *            reporting errors.
     */
    private Boolean getBoolean(JavaClass clazz, String key, DocletTag tag,
            Boolean dflt)
    {
        String val = clean(tag.getNamedParameter(key));
        if (val == null)
        {
            return dflt;
        }
        // TODO: report problem if the value does not look like "true" or
        // "false",
        // rather than silently converting it to false.
        return Boolean.valueOf(val);
    }

    /**
     * Set the parentClassName and interfaceClassNames properties of the
     * provided modelItem object.
     */
    private void initAncestry(Model model, JavaClass clazz, ModelItem modelItem)
    {
        // self
        modelItem.setClassName(clazz.getName());

        // parent
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            ComponentModel parentComponent = model
                    .findComponentByClassName(parentClazz.getName());
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }

        // interfaces
        JavaClass[] classes = clazz.getImplementedInterfaces();
        List ifaceNames = new ArrayList();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass iclazz = classes[i];

            ComponentModel ifaceComponent = model
                    .findComponentByClassName(iclazz.getName());
            if (ifaceComponent != null)
            {
                ifaceNames.add(ifaceComponent.getClassName());
            }
        }
        modelItem.setInterfaceClassNames(ifaceNames);
    }

    private void processConverter(DocletTag tag, JavaClass clazz, Model model)
    {
        String descDflt = "no description";
        // TODO: here, set the default description to the first sentence of the
        // class javadoc.

        String converterId = getString(clazz, "id", tag, null);

        ConverterModel converter = new ConverterModel();
        converter.setClassName(clazz.getName());
        converter.setConverterId(converterId);
        model.addConverter(converter);
    }

    private void processValidator(DocletTag tag, JavaClass clazz, Model model)
    {
        String descDflt = "no description";
        // TODO: here, set the default description to the first sentence of the
        // class javadoc.

        String validatorId = getString(clazz, "id", tag, null);

        ValidatorModel validator = new ValidatorModel();
        validator.setClassName(clazz.getName());
        validator.setValidatorId(validatorId);
        model.addValidator(validator);
    }

    private void processComponent(DocletTag tag, JavaClass clazz, Model model)
            throws MojoExecutionException
    {
        String componentTypeDflt = null;
        JavaField fieldComponentType = clazz.getFieldByName("COMPONENT_TYPE");
        if (fieldComponentType != null)
        {
            componentTypeDflt = clean(fieldComponentType
                    .getInitializationExpression());
        }

        String componentTypeFamily = null;
        JavaField fieldComponentFamily = clazz
                .getFieldByName("COMPONENT_FAMILY");
        if (fieldComponentFamily != null)
        {
            componentTypeFamily = clean(fieldComponentFamily
                    .getInitializationExpression());
        }

        String rendererTypeDflt = null;
        JavaField fieldRendererType = clazz
                .getFieldByName("DEFAULT_RENDERER_TYPE");
        if (fieldRendererType != null)
        {
            rendererTypeDflt = clean(fieldRendererType
                    .getInitializationExpression());
        }

        String descDflt = "no description";
        // TODO: here, set the default description to the first sentence of the
        // class javadoc.

        String componentName = getString(clazz, "name", tag, null);
        String componentClass = getString(clazz, "class", tag, clazz.getName());

        String shortDescription = getString(clazz, "desc", tag, descDflt);
        String longDescription = clazz.getComment();

        String componentFamily = getString(clazz, "family", tag,
                componentTypeFamily);
        String componentType = getString(clazz, "type", tag, componentTypeDflt);
        String rendererType = getString(clazz, "defaultRendererType", tag,
                rendererTypeDflt);
        Boolean canHaveChildren = getBoolean(clazz, "canHaveChildren", tag,
                Boolean.TRUE);

        String tagClass = getString(clazz, "tagClass", tag, null);
        String tagSuperclass = getString(clazz, "tagSuperclass", tag, null);
        String tagHandler = getString(clazz, "tagHandler", tag, null);

        ComponentModel component = new ComponentModel();
        initAncestry(model, clazz, component);
        component.setName(componentName);
        component.setClassName(componentClass);
        component.setDescription(shortDescription);
        component.setLongDescription(longDescription);
        component.setType(componentType);
        component.setFamily(componentFamily);
        component.setRendererType(rendererType);
        component.setChildren(canHaveChildren.booleanValue());

        boolean namingContainer = false;
        JavaClass[] interfaces = clazz.getImplementedInterfaces();
        for (int i = 0; i < interfaces.length; ++i)
        {
            JavaClass iface = interfaces[i];
            if (iface.getName().equals("javax.faces.component.NamingContainer"))
            {
                namingContainer = true;
                break;
            }
        }
        component.setNamingContainer(namingContainer);

        component.setTagClass(tagClass);
        component.setTagSuperclass(tagSuperclass);
        component.setTagHandler(tagHandler);

        // Now here walk the component looking for property annotations.
        processClassProperties(clazz, component);

        validateComponent(component);
        model.addComponent(component);
    }

    private void processClassProperties(JavaClass clazz,
            ComponentModel component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];
            DocletTag tag = method.getTagByName(DOC_PROPERTY);

            if (tag != null)
            {
                Boolean required = getBoolean(clazz, "required", tag,
                        Boolean.FALSE);
                Boolean transientProp = getBoolean(clazz, "transient", tag,
                        Boolean.FALSE);
                Boolean literalOnly = getBoolean(clazz, "literalOnly", tag,
                        Boolean.FALSE);

                AbstractJavaEntity ctx = tag.getContext();

                PropertyModel p = new PropertyModel();
                p.setName(methodToPropName(method));
                p.setClassName("java.lang.String"); // TODO
                p.setRequired(required.booleanValue());
                p.setTransient(transientProp.booleanValue());
                p.setLiteralOnly(literalOnly.booleanValue());
                p.setDescription(ctx.getComment());

                component.addProperty(p);
            }
        }
    }

    /**
     * Convert a method name to a property name.
     * <p>
     * TODO: this method is not quite correctly implemented. In particular there
     * are special rules handling things like getURL(); the propname is not
     * "uRL"!
     */
    private String methodToPropName(JavaMethod method)
    {
        String methName = method.getName();
        StringBuffer name = new StringBuffer();
        if (methName.startsWith("get") || methName.startsWith("set"))
        {
            name.append(methName.substring(3));
        }
        else if (methName.startsWith("is"))
        {
            name.append(methName.substring(2));
        }
        else
        {
            throw new IllegalArgumentException("Invalid annotated method name "
                    + methName);
        }

        char c = name.charAt(0);
        name.setCharAt(0, Character.toLowerCase(c));
        return name.toString();
    }

    private void validateComponent(ComponentModel component)
            throws MojoExecutionException
    {
        // when name is set, this is a real component, so must have
        // desc, type, family, rendererType,
        // tagClass, tagSuperclass

        if (component.getName() == null)
        {
            return;
        }

        List badprops = new ArrayList();
        if (component.getDescription() == null)
        {
            badprops.add("description");
        }
        if (component.getType() == null)
        {
            badprops.add("type");
        }
        if (component.getFamily() == null)
        {
            badprops.add("family");
        }
        if (component.getRendererType() == null)
        {
            badprops.add("rendererType");
        }

        if (badprops.size() > 0)
        {
            StringBuffer buf = new StringBuffer();
            for (Iterator i = badprops.iterator(); i.hasNext();)
            {
                if (buf.length() > 0)
                {
                    buf.append(",");
                }
                buf.append((String) i.next());
            }
            throw new MojoExecutionException(
                    "Missing properties on component class "
                            + component.getClassName() + ":" + buf.toString());

        }
    }
}