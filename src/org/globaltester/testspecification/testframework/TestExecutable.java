package org.globaltester.testspecification.testframework;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * This class is used as super class for test cases and test suites to abstract
 * all similarities
 * 
 * @author amay
 * 
 */
public abstract class TestExecutable {
	//TODO handle results and logging

	IFile iFile;

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
	public TestExecutable(IFile iFile) throws CoreException {
		this.iFile = iFile;
		initFromIFile();
	}

	/**
	 * Initialize all values required for this instance form the already set
	 * variable iFile
	 */
	protected abstract void initFromIFile();

}
