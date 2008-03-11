package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelBuilder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentModel;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyModel;

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
	 * Scan the source tree for annotations. Sets
	 */
	public void buildModel(Model model, MavenProject project) throws MojoExecutionException
	{
        JavaDocBuilder builder = new JavaDocBuilder();
        
        // need a File object representing the original source tree
        List roots = project.getCompileSourceRoots();
        System.out.println("CompileSourceRoots begin..");
        for(Iterator i = roots.iterator(); i.hasNext(); ) {
        	String srcDir = (String) i.next();
        	System.out.println("==" + srcDir);
            builder.addSourceTree(new File(srcDir));
        }
        System.out.println("CompileSourceRoots end..");
        
        JavaSource[] sources = builder.getSources();
        JavaClass[] classes = builder.getClasses();
        for(int i=0; i<classes.length; ++i)
        {
        	JavaClass clazz = classes[i];
        	processClass(clazz, model);
        }
	}
	

	// remove all leading whitespace and a quotemark if it exists.
	//
	// Qdox comments like <code>@foo val= "bar"</code> return a value
	// with leading whitespace and quotes, so remove them.
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
			char c = src.charAt(end-1);
			if (!Character.isWhitespace(c) && (c != '"'))
			{
				break;
			}
			--end;
		}
		return src.substring(start, end);
	}

	private String getString(JavaClass clazz, DocletTag tag, String key)
	{
		String val = clean(tag.getNamedParameter(key));
		if (val == null)
		{
			throw new IllegalStateException(
					"missing key " + key + " in tag " + tag.getName()
					+ " on line " + tag.getLineNumber()
					+ " of class " + clazz.getName());
		}
		return val;
	}

	private String getString(JavaClass clazz, DocletTag tag, String key, String dflt)
	{
		String val = clean(tag.getNamedParameter(key));
		if (val == null)
		{
			return dflt;
		}
		return val;
	}

	private Boolean getBoolean(JavaClass clazz, DocletTag tag, String key, Boolean dflt)
	{
		String val = clean(tag.getNamedParameter(key));
		if (val == null)
		{
			return dflt;
		}
		return Boolean.valueOf(val);
	}

	private void processClass(JavaClass clazz, Model model)
	{
    	System.out.println("class:" + clazz.getName());
    	
    	DocletTag tag;
    	
    	tag = clazz.getTagByName(DOC_CONVERTER, false);
    	if (tag != null)
    	{
    		processComponent(tag, clazz, model);
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

	private void processConverter(DocletTag tag, JavaClass clazz, Model model)
	{
	}

	private void processValidator(DocletTag tag, JavaClass clazz, Model model)
	{
	}

	private void processComponent(DocletTag tag, JavaClass clazz, Model model)
	{
		String componentTypeDflt = null;
		JavaField fieldComponentType = clazz.getFieldByName("COMPONENT_TYPE");
		if (fieldComponentType != null)
		{
			componentTypeDflt = clean(fieldComponentType.getInitializationExpression());
		}

		String rendererTypeDflt = null;
		JavaField fieldRendererType = clazz.getFieldByName("DEFAULT_RENDERER_TYPE");
		if (fieldRendererType != null)
		{
			rendererTypeDflt = clean(fieldRendererType.getInitializationExpression());
		}

		String descDflt = "no description";
		// TODO: here, set the default description to the first sentence of the
		// class javadoc.

		String componentFamily = getString(clazz, tag, "family");
	    String componentType = getString(clazz, tag, "type", componentTypeDflt);
		String rendererType = getString(clazz, tag, "defaultRendererType", rendererTypeDflt);
		String shortDescription = getString(clazz, tag, "desc", descDflt);
		Boolean canHaveChildren = getBoolean(clazz, tag, "canHaveChildren", Boolean.TRUE);
		String tagName = getString(clazz, tag, "tagName", null);
		String tagClass = getString(clazz, tag, "tagClass", null);
		String tagSuperclass = getString(clazz, tag, "tagSuperclass", null);
		String tagHandler = getString(clazz, tag, "tagHandler", null);

		String longDescription = clazz.getComment();

		ComponentModel component = new ComponentModel();
		component.setComponentClass(clazz.getName());
		component.setComponentType(componentType);
		component.setComponentFamily(componentFamily);
		component.setRendererType(rendererType);
		component.setDescription(shortDescription);
		component.setLongDescription(longDescription);
		component.setChildren(canHaveChildren.booleanValue());

		boolean namingContainer = false;
		JavaClass[] interfaces = clazz.getImplementedInterfaces();
		for(int i=0; i<interfaces.length; ++i)
		{
			JavaClass iface = interfaces[i];
			if (iface.getName().equals("javax.faces.component.NamingContainer"))
			{
				namingContainer = true;
				break;
			}
		}
		component.setNamingContainer(namingContainer);
		
		component.setTagName(tagName);
		component.setTagClass(tagClass);
		component.setTagSuperclass(tagSuperclass);
		component.setTagHandler(tagHandler);
		
		// Now here walk the component and all its ancestors
		// looking for property annotations.
		processClassProperties(clazz, component);

		model.addComponent(component);
	}

	private void processClassProperties(JavaClass clazz, ComponentModel component)
	{
		JavaMethod[] methods = clazz.getMethods();
		for(int i=0; i<methods.length; ++i)
		{
			JavaMethod method = methods[i];
			System.out.println("Inspecting method " + method.getName());
			DocletTag tag = method.getTagByName(DOC_PROPERTY, true);
			if (tag != null)
			{
				System.out.println("found method" + method.getName());
				Boolean required = getBoolean(clazz, tag, "required", Boolean.FALSE);
				Boolean transientProp = getBoolean(clazz, tag, "transient", Boolean.FALSE);
				Boolean literalOnly = getBoolean(clazz, tag, "literalOnly", Boolean.FALSE);
				
				AbstractJavaEntity ctx = tag.getContext();
				
				PropertyModel p = new PropertyModel();
				p.setPropertyName(ctx.getName());
				p.setPropertyClass("java.lang.String"); // TODO
				p.setRequired(required.booleanValue());
				p.setTransient(transientProp.booleanValue());
				p.setLiteralOnly(literalOnly.booleanValue());
				p.setDescription(ctx.getComment());
				
				component.addProperty(p);
			}
		}
	}	
}