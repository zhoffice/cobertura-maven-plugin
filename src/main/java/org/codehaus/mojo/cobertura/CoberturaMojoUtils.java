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

import org.apache.maven.plugin.MojoExecution;

import java.io.File;

/**
 * Utility class which can be used by both build-plugins and reports
 *
 * @author Robert Scholte
 * @since 2.6
 */
public class CoberturaMojoUtils
{

    private CoberturaMojoUtils()
    {
    }

    /**
     * @param buildDirectory the buildDirectory
     * @param execution      the mojo-execution
     * @return the data file
     */
    public static File getDataFile( File buildDirectory, MojoExecution execution )
    {
        return new File( buildDirectory, "cobertura/cobertura.ser" );
    }
}