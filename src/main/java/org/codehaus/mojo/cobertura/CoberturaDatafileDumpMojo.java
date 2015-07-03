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

import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.PackageData;
import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.util.Header;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Output the contents of Cobertura's data file to the command line.
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 * @version $Id: CoberturaDatafileDumpMojo.java 20180 2014-11-06 13:41:36Z dennisl $
 * @goal dump-datafile
 */
public class CoberturaDatafileDumpMojo
    extends AbstractCoberturaMojo
{
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

        if ( !getDataFile().exists() )
        {
            throw new MojoExecutionException( "Unable to dump nonexistent dataFile [" + getDataFile() + "]" );
        }

        ProjectData projectData = CoverageDataFileHandler.loadCoverageData( getDataFile() );
        NumberFormat percentage = NumberFormat.getPercentInstance();
        NumberFormat integer = NumberFormat.getIntegerInstance();

        println( "<?xml version=\"1.0\"?>" );

        printProject( projectData, percentage, integer );

        Iterator it = projectData.getPackages().iterator();
        while ( it.hasNext() )
        {
            PackageData packageData = (PackageData) it.next();
            printPackage( percentage, integer, packageData );
        }

        println( "</coverage>" );
    }

    /**
     * print project info to the log.
     *
     * @param projectData
     * @param percentage
     * @param integer
     */
    private void printProject( ProjectData projectData, NumberFormat percentage, NumberFormat integer )
    {
        println( "<coverage line-rate=\"" + percentage.format( projectData.getLineCoverageRate() ) + "\" branch-rate=\""
                     + percentage.format( projectData.getBranchCoverageRate() ) + "\" lines-covered=\""
                     + integer.format( projectData.getNumberOfCoveredLines() ) + "\" lines-valid=\"" + integer.format(
            projectData.getNumberOfValidLines() ) + "\" branches-covered=\"" + integer.format(
            projectData.getNumberOfCoveredBranches() ) + "\" branches-valid=\"" + integer.format(
            projectData.getNumberOfValidBranches() ) + "\" version=\"" + Header.version() + "\" timestamp=\""
                     + new Date().getTime() + "\">" );
    }

    /**
     * print package info to the log.
     *
     * @param percentage
     * @param integer
     * @param packageData
     */
    private void printPackage( NumberFormat percentage, NumberFormat integer, PackageData packageData )
    {
        println( "<package name=\"" + packageData.getName() + "\" line-rate=\"" + percentage.format(
            packageData.getLineCoverageRate() ) + "\" branch-rate=\"" + percentage.format(
            packageData.getBranchCoverageRate() ) + "\" lines-covered=\"" + integer.format(
            packageData.getNumberOfCoveredLines() ) + "\" lines-valid=\"" + integer.format(
            packageData.getNumberOfValidLines() ) + "\" branches-covered=\"" + integer.format(
            packageData.getNumberOfCoveredBranches() ) + "\" branches-valid=\"" + integer.format(
            packageData.getNumberOfValidBranches() ) + "\" />" );
    }

    /**
     * Write the message to the logger
     *
     * @param msg
     */
    private void println( String msg )
    {
        getLog().info( msg );
    }
}
