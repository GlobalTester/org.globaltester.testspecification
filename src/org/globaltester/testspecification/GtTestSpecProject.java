package org.globaltester.testspecification;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.core.resources.GtResourceHelper;


public class GtTestSpecProject {
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
	public static IProject createProject(String projectName, URI location) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = GtResourceHelper.createEmptyProject(projectName, location);
		try {
			GtResourceHelper.addNature(project, GtTestSpecNature.NATURE_ID);

			String[] paths = { "TestData/Certificates", "TestData/Subroutines",
					"TestCases" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			GtResourceHelper.addToProjectStructure(project, paths);
		} catch (CoreException e) {
			e.printStackTrace();
			project = null;
		}

		// refresh the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot()
						.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh of workspace failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually 
		}

		return project;
	}

}
