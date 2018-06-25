package org.globaltester.testspecification.testframework;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * This class is used as super class for test cases and test suites to abstract
 * all similarities
 * 
 * @author amay
 * 
 */
public abstract class FileTestExecutable implements ITestExecutable {
	IFile iFile;
	String name;

	public IFile getIFile() {
		return iFile;
	}

	/**
	 * Constructor referencing the workspace file which describes the test
	 * executable. All required data is extracted from the workspace file and
	 * its surrounding project.
	 * 
	 * @param iFile
	 *            IFile that contains the test case data and is located inside
	 *            an GTTestSpecProject
	 * @throws CoreException
	 */
	public FileTestExecutable(IFile iFile) throws CoreException {
		this.iFile = iFile;
		name = iFile.getName();
		initFromIFile();
	}

	/**
	 * Initialize all values required for this instance form the already set
	 * variable iFile
	 */
	protected abstract void initFromIFile();

	@Override
	public String getName() {
		return name;
	}

	public static IProject[] getDeps(IProject project) { //FIXME AAF check whethter this is still needed
		List<IProject> deps = new LinkedList<>();

		String dependencyString = getManifestValueForProject(project, "Require-Bundle");
		if (dependencyString == null) return new IProject[0];
		
		String[] dependencies = dependencyString.split(",");
		for (String current : dependencies) {
			if (current.contains(";")) {
				current = current.substring(0, current.indexOf(";"));
			}

			for (IProject currentProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				String projectFolder = currentProject.getLocation().lastSegment();
				String projectSymbolicName = getManifestValueForProject(currentProject, "Bundle-SymbolicName");

				if (projectSymbolicName != null && projectSymbolicName.contains(";")){
					projectSymbolicName = projectSymbolicName.substring(0, projectSymbolicName.indexOf(";"));
				}
				if (projectFolder.equals(current) || current.equals(projectSymbolicName)) {
					deps.add(currentProject);
					break;
				}
			}

		}

		return deps.toArray(new IProject[deps.size()]);
	}
	
	/**
	 * Return the value of the given key from Manifest. Or null if the key does
	 * not exist or can't be parsed for any reason.
	 * 
	 * @param project
	 * @param key
	 * @return
	 */
	private static String getManifestValueForProject(IProject project, String key){
		IFile manifestFile = project.getFolder("META-INF").getFile("MANIFEST.MF");

		Manifest manifest;
		try {
			manifest = new Manifest(manifestFile.getContents());
			return manifest.getMainAttributes().getValue(key);
		} catch (IOException | CoreException e) {
			return null;
		}
	}
	
	public abstract List<PreCondition> getPreConditions();
	public abstract List<TestStep> getTestSteps();
	public abstract List<PostCondition> getPostConditions();
	public abstract String getTestCasePurpose();
	public abstract String getTestCaseID();
	public abstract String getProfileString();
	
	/**
	 * Dumps general information of the test case to the log
	 */
	public abstract void dumpTestCaseInfos();

}
