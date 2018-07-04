package org.globaltester.testspecification.testframework;

import java.util.List;

import org.eclipse.core.resources.IFile;
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
