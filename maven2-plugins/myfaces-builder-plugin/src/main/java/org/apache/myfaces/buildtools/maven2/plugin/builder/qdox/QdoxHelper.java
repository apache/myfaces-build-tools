/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.IOUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.BehaviorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;
import org.codehaus.plexus.components.io.fileselectors.FileInfo;
import org.codehaus.plexus.components.io.fileselectors.FileSelector;
import org.codehaus.plexus.components.io.fileselectors.IncludeExcludeFileSelector;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * Helper class used by QdoxModelBuilder and provide utility methods. 
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class QdoxHelper
{

    /**
     * Convert a method name to a property name.
     */
    public static String methodToPropName(String methodName)
    {
        StringBuffer name = new StringBuffer();
        if (methodName.startsWith("get") || methodName.startsWith("set"))
        {
            name.append(methodName.substring(3));
        }
        else if (methodName.startsWith("is"))
        {
            name.append(methodName.substring(2));
        }
        else
        {
            throw new IllegalArgumentException("Invalid annotated method name "
                    + methodName);
        }
    
        // Handle following styles of property name
        // getfooBar --> fooBar
        // getFooBar --> fooBar
        // getURL --> url
        // getURLLocation --> urlLocation
        for (int i = 0; i < name.length(); ++i)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                name.setCharAt(i, Character.toLowerCase(c));
            }
            else
            {
                if (i > 1)
                {
                    // reset the previous char to uppercase
                    c = name.charAt(i - 1);
                    name.setCharAt(i - 1, Character.toUpperCase(c));
                }
                break;
            }
        }
        return name.toString();
    }

    /**
     * Given the full javadoc for a component, extract just the "first
     * sentence".
     * <p>
     * Initially, just find the first dot, and strip out any linefeeds. Later,
     * try to handle "e.g." and similar (see javadoc algorithm for sentence
     * detection).
     */
    public static String getFirstSentence(String doc)
    {
        if (doc == null)
        {
            return null;
        }
    
        int index = doc.indexOf('.');
        if (index == -1)
        {
            return doc;
        }
        // abc.
        return doc.substring(0, index);
    }
    
    
    public static String getFullyQualifiedClassName(JavaClass clazz, String fqn)
    {
        //QDox 1.9 bug. getFullyQualifiedName does not resolve 
        //correctly classes like jakarta.servlet.jsp.tagext.TagSupport as parent
        //of a class with @JSFJspTag. The temporal solution is scan
        //the imports, looking for this type and if it is found replace it.
        //Fixed on 1.9.1, but better let the code as is 
        /*
        if (fqn.indexOf('.') == -1)
        {
            String [] imports = clazz.getSource().getImports();
            for (int i = 0; i < imports.length; i++)
            {
                if (imports[i].endsWith(fqn))
                {
                    fqn = imports[i];
                }
            }
        }*/
        return fqn;
    }
    
    /**
     * For each component, try to find its "logical" parent component,
     * ie the nearest superclass that is also annotated as a component
     * and therefore has an entry in the model.
     * <p>
     * In most cases this could be done at the time the component is
     * processed. The processClass() method does try to process the
     * classes that qdox discovers in ancestor->descendant order.
     * <p>
     * However there is one case where this just doesn't work. Therefore
     * a two-pass approach is used: first create a ComponentMeta for
     * each component, and then on a second pass find the matching
     * parent for each one.
     * <p>
     * The problem case is where an annotated java class extends a
     * generated one. In this case when walking up the ancestry tree of
     * the hand-written class we find an entry for which there is no
     * ComponentMeta entry. We do not know whether this is because the
     * parent exists but is not annotated, or whether a ComponentMeta
     * for that parent will be generated once we have processed some
     * other class that happens to have the matching annotation.
     */
    public static void initComponentAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);
            
            ComponentMeta parentComponent = model
                    .findComponentByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }

    /**
     * Same as initComponentAncestry but for validators.
     */
    public static void initValidatorAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);

            ValidatorMeta parentComponent = model
                    .findValidatorByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    /**
     * Same as initComponentAncestry but for converters
     */
    public static void initConverterAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);
            
            ConverterMeta parentComponent = model
                    .findConverterByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }

    public static Annotation getAnnotation(AbstractJavaEntity entity, String annoName)
    {
        Annotation[] annos = entity.getAnnotations();
        if (annos == null)
        {
            return null;
        }
        // String wanted = ANNOTATION_BASE + "." + annoName;
        for (int i = 0; i < annos.length; ++i)
        {
            Annotation thisAnno = annos[i];
            // Ideally, here we would check whether the fully-qualified name of
            // the annotation
            // class matches ANNOTATION_BASE + "." + annoName. However it
            // appears that qdox 1.6.3
            // does not correctly expand @Foo using the class import statements;
            // method
            // Annotation.getType.getJavaClass.getFullyQualifiedName still just
            // returns the short
            // class name. So for now, just check for the short name.
            String thisAnnoName = thisAnno.getType().getJavaClass().getName();
            
            //Make short name for recognizing, if returns long
            int containsPoint = thisAnnoName.lastIndexOf('.');
            if (containsPoint != -1)
            {
                thisAnnoName = thisAnnoName.substring(containsPoint+1);
            }
            if (thisAnnoName.equals(annoName))
            {
                return thisAnno;
            }
        }
        return null;
    }
    
    /**
     * Same as initComponentAncestry but for converters
     */
    public static void initBehaviorAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);
            
            BehaviorMeta parentComponent = model
                    .findBehaviorByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    public static void initFaceletTagHandlerAncestry(Map javaClassByName, Model model, ClassMeta modelItem)
    {
        JavaClass clazz = (JavaClass) javaClassByName.get(modelItem.getSourceClassName());
        JavaClass parentClazz = clazz.getSuperJavaClass();
        while (parentClazz != null)
        {
            String parentClazzName = parentClazz.getFullyQualifiedName();
            
            parentClazzName = getFullyQualifiedClassName(clazz,parentClazzName);

            FaceletTagMeta parentComponent = model
                    .findFaceletTagByClassName(parentClazzName);
            if (parentComponent != null)
            {
                modelItem.setParentClassName(parentComponent.getClassName());
                break;
            }
            parentClazz = parentClazz.getSuperJavaClass();
        }
    }
    
    public static String evaluateParameterInitializationExpression(String value)
    {
        if (value.charAt(0) == '"' && value.charAt(value.length()-1) == '"' && value.indexOf('+') == -1)
        {
            return value.substring(1,value.length()-1);
        }            
        return value;
    }
    
    @Deprecated
    public static void addFileToJavaDocBuilder(JavaDocBuilder builder,
            FileSelector selector, File path)
    {
        addFileToJavaDocBuilder(builder,selector, path, path.getPath());
    }
    
    @Deprecated
    public static void addFileToJavaDocBuilder(JavaDocBuilder builder,
            FileSelector selector, File path, String basePath)
    {
        if (path.isDirectory())
        {
            File[] files = path.listFiles();
            
            //Scan all files in directory
            for (int i = 0; i < files.length; i++)
            {
                addFileToJavaDocBuilder(builder, selector, files[i], basePath);
            }
        }
        else
        {
            File file = path;

            try
            {
                String name = file.getPath();
                while (name.startsWith("/"))
                {
                    name = name.substring(1);
                }
                while (name.startsWith("\\"))
                {
                    name = name.substring(1);
                }
                SourceFileInfo fileInfo = new SourceFileInfo(file,name);
                if (selector.isSelected(fileInfo))
                {
                    //System.out.println("file:"+name);
                    builder.addSource(file);
                }
            }
            catch (FileNotFoundException e)
            {
                Log log = LogFactory.getLog(QdoxHelper.class);
                log.error("Error reading file: "+file.getName()+" "+e.getMessage());
            }
            catch (IOException e)
            {
                Log log = LogFactory.getLog(QdoxHelper.class);
                log.error("Error reading file: "+file.getName()+" "+e.getMessage());                
            }
        }
    }
    
    /**
     * Remove all leading whitespace and a quotemark if it exists.
     * <p>
     * Qdox comments like <code>foo val= "bar"</code> return a value with
     * leading whitespace and quotes, so remove them.
     */
    public static String clean(Object srcObj)
    {
        if (srcObj == null)
        {
            return null;
        }

        String src = srcObj.toString();
        int start = 0;
        int end = src.length();
        
        if (end == 0)
        {
            return src;
        }
        
        if (src.equals("\"\""))
        {
            return "\"\"";
        }

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
     * Param clazz is the class the annotation is attached to; only used when
     * reporting errors.
     */
    public static String getString(JavaClass clazz, String key, Map map, String dflt)
    {
        String val = clean(map.get(key));
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
     * Param clazz is the class the annotation is attached to; only used when
     * reporting errors.
     */
    public static Boolean getBoolean(JavaClass clazz, String key, Map map,
            Boolean dflt)
    {
        String val = clean(map.get(key));
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
     * Returns true if the tagClassName is not null, but the corresponding
     * source file cannot be found in the specified source dirs.
     */
    public static boolean isTagClassMissing(String tagClassName, List sourceDirs)
    {
        if (tagClassName == null)
        {
            return false;
        }
        String tagClassFile = StringUtils.replace(tagClassName,".","/")+".java";
        return !IOUtils.existsSourceFile(tagClassFile, sourceDirs);
    }
    
    private static class SourceFileInfo implements FileInfo
    {
        private File file;
        
        private String name;

        /**
         * Creates a new instance.
         */
        public SourceFileInfo( File file )
        {
            this( file, file.getPath().replace( '\\', '/' ) );
        }

        /**
         * Creates a new instance.
         */
        public SourceFileInfo( File file, String name )
        {
            this.file = file;
            this.name = name;
        }
        
        /**
         * Sets the resources file.
         */
        public void setFile( File file )
        {
            this.file = file;
        }

        /**
         * Returns the resources file.
         */
        public File getFile()
        {
            return file;
        }

        /**
         * Sets the resources name.
         */
        public void setName( String name )
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }        
        
        public InputStream getContents() throws IOException
        {
            return new FileInputStream( getFile() );
        }

        public boolean isDirectory()
        {
            return file.isDirectory();
        }

        public boolean isFile()
        {
            return file.isFile();
        }        
    }
    
    /**
     * 
     * @deprecated use IOUtils.visitSources(ModelParams, JavaSourceVisitor)
     * @param sourceDirs
     * @param includes
     * @param excludes
     * @return
     */
    @Deprecated
    public static JavaClass[] getSourceClasses(List sourceDirs, String includes, String excludes)
    {
        if (StringUtils.isNotEmpty(includes) || 
                StringUtils.isNotEmpty(excludes))
        {
            return getInnerSourceClasses(sourceDirs, includes, excludes);
        }
        else
        {
            return getInnerSourceClasses(sourceDirs);
        }
    }
    
    @Deprecated
    private static JavaClass[] getInnerSourceClasses(List sourceDirs, String includes, String excludes)
    {
        JavaDocBuilder builder = new JavaDocBuilder();
        IncludeExcludeFileSelector selector = 
            new IncludeExcludeFileSelector(); 
        if (StringUtils.isNotEmpty(excludes))
        {
            selector.setExcludes(excludes.split(","));
        }
        if (StringUtils.isNotEmpty(includes))
        {
            selector.setIncludes(includes.split(","));            
        }
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            Object dir = i.next();
            File srcDir = null;
            if (dir instanceof File)
            {
                srcDir = (File) dir;
            }
            else         
            {
                new File((String) i.next());
            }
            //Scan all files on directory and add to builder
            QdoxHelper.addFileToJavaDocBuilder(builder, selector, srcDir);
        }        
        
        return builder.getClasses();
    }

    @Deprecated
    private static JavaClass[] getInnerSourceClasses(List sourceDirs)
    {
        JavaDocBuilder builder = new JavaDocBuilder();
        for (Iterator i = sourceDirs.iterator(); i.hasNext();)
        {
            String srcDir = (String) i.next();
            builder.addSourceTree(new File(srcDir));
        }
        return builder.getClasses();
    }
    
    public static class JavaClassComparator implements Comparator
    {
        public int compare(Object arg0, Object arg1)
        {
            JavaClass c0 = (JavaClass) arg0;
            JavaClass c1 = (JavaClass) arg1;
            return (c0.getFullyQualifiedName().compareTo(c1.getFullyQualifiedName()));
        }
    }



}
