package org.apache.myfaces.buildtools.maven2.plugin.builder;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;

/**
 * An interface that is implemented by classes that are capable of collecting
 * metadata about JSF components, converters, validators, etc.
 * <p>
 * A ModelBuilder implementation might read xml files, or scan source code for
 * annotations, or read data from a database, or any number of possible
 * approaches. All that matters is that it makes a series of calls to a Model
 * object to add information.
 * <p>
 * ModelBuilder implementations may be run as a chain, ie one instance used to
 * populate the Model object with some objects, then a second instance used to
 * add more data or modify the data already in the model.
 */
public interface ModelBuilder
{
    /**
     * Given a model (which might already be partly populated with data, add
     * information about JSF artifacts.
     */
    public void buildModel(Model model, MavenProject project)
            throws MojoExecutionException;

}