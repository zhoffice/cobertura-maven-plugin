/*
 * #%L
 * Mojo's Maven plugin for Cobertura
 * %%
 * Copyright (C) 2005 - 2013 Codehaus
 * %%
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
 * #L%
 */
package org.codehaus.mojo.cobertura;

import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.mojo.cobertura.configuration.ConfigCheck;
import org.codehaus.mojo.cobertura.tasks.CheckTask;

/**
 * Check the coverage percentages for unit tests from the last instrumentation,
 * and optionally fail the build if the targets are not met. To fail the build
 * you need to set
 * <code>configuration/check/haltOnFailure=true</code> in the plugin's
 * configuration.
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 * @goal check
 * @execute phase="test" lifecycle="cobertura"
 * @phase verify
 */
public class CoberturaCheckMojo
    extends AbstractCoberturaMojo
{
    /**
     * The <a href="usage.html#Check">Check Configuration</a>.
     *
     * @parameter
     * @required
     */
    private ConfigCheck check;

    /**
     * {@inheritDoc}
     */
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( skipMojo() )
        {
            return;
        }

        ArtifactHandler artifactHandler = getProject().getArtifact().getArtifactHandler();
        if ( !"java".equals( artifactHandler.getLanguage() ) )
        {
            getLog().info(
                "Not executing cobertura:instrument as the project is not a Java classpath-capable package" );
        }
        else
        {
            if ( !getDataFile().exists() )
            {
                getLog().info( "Cannot perform check, instrumentation not performed - skipping." );
            }
            else
            {
                CheckTask task = new CheckTask();
                setTaskDefaults( task );
                task.setConfig( check );
                task.setDataFile( getDataFile().getAbsolutePath() );

                task.execute();
            }
        }
    }
}
