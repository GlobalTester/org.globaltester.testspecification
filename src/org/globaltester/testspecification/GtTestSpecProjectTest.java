package org.globaltester.testspecification;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.AssertionFailedException;
import org.globaltester.testspecification.GtTestSpecNature;
import org.globaltester.testspecification.GtTestSpecProject;
import org.junit.Assert;
import org.junit.Test;

public class GtTestSpecProjectTest {

	@Test(expected = AssertionFailedException.class)
	public void testCreateProjectWithEmptyNameArg() {
		String projectName = " "; //$NON-NLS-1$
		GtTestSpecProject.createProject(projectName, null);
	}

	@Test(expected = AssertionFailedException.class)
	public void testCreateProjectWithNullNameArg() {
		String projectName = null;
		GtTestSpecProject.createProject(projectName, null);
	}

	@Test
	public void testCreateProjectWithGoodArgs() throws Exception {
		String projectName = "junitTestProject-deleteMe";

		IProject project = GtTestSpecProject.createProject(projectName,
				null);

		// check nature is added
		Assert.assertTrue("GtTestSpecNature is not correctly added",
				project.hasNature(GtTestSpecNature.NATURE_ID));

		// check directory structure is created correctly
		String[] paths = { "TestData/Certificates", "TestData/Subroutines",
				"TestCases" };
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
