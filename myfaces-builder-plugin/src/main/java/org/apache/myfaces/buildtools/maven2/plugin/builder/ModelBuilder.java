package org.apache.myfaces.buildtools.maven2.plugin.builder;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;

public interface ModelBuilder
{
	public void buildModel(Model model, MavenProject project) throws MojoExecutionException;
	
}