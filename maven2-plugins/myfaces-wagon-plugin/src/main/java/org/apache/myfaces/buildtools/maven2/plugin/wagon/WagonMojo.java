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
package org.apache.myfaces.buildtools.maven2.plugin.wagon;

import java.io.File;

import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.UnsupportedProtocolException;
import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.observers.Debug;
import org.apache.maven.wagon.repository.Repository;


/**
 * Deploys the content of a directory using scp/file protocol.
 * For scp protocol, files are packaged into zip archive,
 * then archive is transfred to remote host, nextly it is un-archived.
 * This method of deployment should normally be much faster
 * then making file by file copy.  For file protocol, the files are copied
 * directly to the destination directory.
 *
 * @version 
 * @goal deploy
 */
public class WagonMojo extends AbstractMojo
{
    /**
     * Directory containing the files for wagon.
     *
     * @parameter
     * @required
     */
    private File inputDirectory;

    /**
      * Specifies the server id.
      *
      * @parameter
      * * @required
      */
    private String id;

    /**
     * The full URL of the server.
     *
     * @parameter
     * @required
     */
    private String url;
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @component
     */
    private WagonManager wagonManager;

    public void execute()
        throws MojoExecutionException
    {
        if ( !inputDirectory.exists() )
        {
            throw new MojoExecutionException( "The inputDirectory does not exist" );
        }

        Repository repository = new Repository( id, url );


        Wagon wagon = null;
        try
        {
            wagon = wagonManager.getWagon( repository.getProtocol() );
        }
        catch ( UnsupportedProtocolException e )
        {
            throw new MojoExecutionException( "Unsupported protocol: '" + repository.getProtocol() + "'", e );
        }

        if ( !wagon.supportsDirectoryCopy() )
        {
            throw new MojoExecutionException(
                "Wagon protocol '" + repository.getProtocol() + "' doesn't support directory copying" );
        }

        try
        {
            Debug debug = new Debug();

            wagon.addSessionListener( debug );

            wagon.addTransferListener( debug );

            wagon.connect( repository, wagonManager.getAuthenticationInfo( id ) );

            wagon.putDirectory( inputDirectory, "." );

        }
        catch ( ResourceDoesNotExistException e )
        {
            throw new MojoExecutionException( "Error uploading", e );
        }
        catch ( TransferFailedException e )
        {
            throw new MojoExecutionException( "Error uploading", e );
        }
        catch ( AuthorizationException e )
        {
            throw new MojoExecutionException( "Error uploading", e );
        }
        catch ( ConnectionException e )
        {
            throw new MojoExecutionException( "Error uploading", e );
        }
        catch ( AuthenticationException e )
        {
            throw new MojoExecutionException( "Error uploading", e );
        }
        finally
        {
            try
            {
                wagon.disconnect();
            }
            catch ( ConnectionException e )
            {
                getLog().error( "Error disconnecting wagon - ignored", e );
            }
        }
    }
}
