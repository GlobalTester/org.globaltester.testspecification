package org.globaltester.testspecification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.globaltester.testspecification.testframework.TestCase;


public final class GtTestSpecProject {
	
	private GtTestSpecProject() {
		//Not intendet to be instantiated
	}
	
	/**
	 * Create a GlobalTester TestSpecification Project. This includes creation
	 * of the Eclipse project, adding the according nature and creating the
	 * initial folder structure.
	 * 
	 * @param projectName
	 *            name of the project to be created
	 * @param location
	 *            location where the project shall be created. If empty the
	 *            default workspace location will be used.
	 * @return the created project
	 */
	public static IProject createProjectWithInitialStructure(String projectName, URI location) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = createProject(projectName, location);
		try {
			String[] paths = { "TestData/Certificates", "TestData/Subroutines", "TestCases" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			GtResourceHelper.addToProjectStructure(project, paths);
			createDefaultFile(defaultTestSpec, project.getFile("testSpecification.gtspec"));
			TestCase.createDefaultTestCase(project.getFile("TestCases" + File.separator + "testCase.gt"));
			createDefaultFile(defaultTestLayer, project.getFile("TestCases" + File.separator + "testLayer.gtspec"));
			createDefaultFile(defaultTestUnit, project.getFile("TestCases" + File.separator + "testUnit.gtspec"));
		} catch (CoreException e) {
			BasicLogger.logException("Unable to create initial structure in to project "+projectName, e, LogLevel.DEBUG);
		}

		// refresh the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot()
						.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) { //NOSONAR
			// refresh of workspace failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually 
		}

		return project;
	}
	
	/**
	 * Create a GlobalTester TestSpecification Project. This includes creation
	 * of the Eclipse project and adding the according nature.
	 * 
	 * @param projectName
	 *            name of the project to be created
	 * @param location
	 *            location where the project shall be created. If empty the
	 *            default workspace location will be used.
	 * @return the created project
	 */
	public static IProject createProject(String projectName, URI location) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = GtResourceHelper.createEmptyProject(projectName, location);
		try {
			GtResourceHelper.addNature(project, GtTestSpecNature.NATURE_ID);
		} catch (CoreException e) {
			BasicLogger.logException("Could not add GtTestSpecNature to project "+projectName, e, LogLevel.DEBUG);
		}

		// refresh the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot()
						.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) { //NOSONAR
			// refresh of workspace failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually 
		}

		return project;
	}
	
	private static void createDefaultFile(String content, IFile iFile) {
		File file = new File(iFile.getLocationURI());
		
		try (FileWriter fstream = new FileWriter(file); BufferedWriter out = new BufferedWriter(fstream)) {
			out.write(content);
		} catch (IOException e) {
			BasicLogger.logException("Unable to create default file " + iFile.getFullPath(), e, LogLevel.DEBUG);
		}
	}
	private static final String defaultTestUnit = ""+
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	"<TestUnit id=\"Unit_1\"\n" +
	"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
	"	xmlns=\"http://globaltester.org/testspecification\">\n" +
	"  <TestCase>testCase.gt</TestCase>\n" +
	"</TestUnit>";
	


	private static final String defaultTestLayer = ""+
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	"<TestLayer id=\"Layer_1\"\n" +
	"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
	"	xmlns=\"http://globaltester.org/testspecification\">\n" +
	"  <TestUnit>testUnit.gtspec</TestUnit>\n" +
	"</TestLayer>";
	


	private static final String defaultTestSpec = ""+
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	"<TestSpecification\n" +
	"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
	"	xmlns=\"http://globaltester.org/testspecification\">\n" +
	"  <VersionHistory>\n" +
	"    <Version>\n" +
	"      <VersionID>0.10</VersionID>\n" +
	"      <Date>Some Date</Date>\n" +
	"      <Editor>Some Author</Editor>\n" +
	"      <Description>Working Draft</Description>\n" +
	"    </Version>\n" +
	"  </VersionHistory>\n" +

	"  <Text>\n" +
	"  	<Heading level=\"1\">Introduction</Heading>\n" +
	"		<Paragraph>This file can be used as a starting point.</Paragraph>\n" +
	"  </Text>\n" +
	"  <TestLayer>TestCases/testLayer.gtspec</TestLayer>\n" +
	"</TestSpecification>";
	
}
