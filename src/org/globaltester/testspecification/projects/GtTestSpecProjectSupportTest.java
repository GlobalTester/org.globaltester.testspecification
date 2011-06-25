package org.globaltester.testspecification.projects;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.AssertionFailedException;
import org.globaltester.testspecification.natures.GtTestSpecNature;
import org.junit.Assert;
import org.junit.Test;

public class GtTestSpecProjectSupportTest {

	@Test(expected = AssertionFailedException.class)
	public void testCreateProjectWithEmptyNameArg() {
		String projectName = " "; //$NON-NLS-1$
		GtTestSpecProjectSupport.createProject(projectName, null);
	}

	@Test(expected = AssertionFailedException.class)
	public void testCreateProjectWithNullNameArg() {
		String projectName = null;
		GtTestSpecProjectSupport.createProject(projectName, null);
	}

	@Test
	public void testCreateProjectWithGoodArgs() throws Exception {
		String projectName = "junitTestProject-deleteMe";

		IProject project = GtTestSpecProjectSupport.createProject(projectName,
				null);

		// check nature is added
		Assert.assertTrue("GtTestSpecNature is not correctly added",
				project.hasNature(GtTestSpecNature.NATURE_ID));

		// check directory structure is created correctly
		String[] paths = { "TestData/Certificates", "TestData/Subroutines",
				"TestLayer" };
		String projectPath = project.getLocation().toString();
		for (String path : paths) {
			File file = new File(projectPath + "/" + path);
			if (!file.exists()) {
				Assert.fail("Folder structure " + path + " does not exist.");
			}
		}

		// delete the project after the test
		project.delete(true, null);
	}

}
