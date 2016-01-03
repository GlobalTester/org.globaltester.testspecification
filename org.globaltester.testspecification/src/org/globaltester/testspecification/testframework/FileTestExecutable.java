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

	/**
	 * Returns the name of this instance
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Copy the specification of this TestExecutable to the new location and returns
	 * a new instance of TestExecutable relating to the new file.
	 * 
	 * This needs to be overwritten if the underlying TestExecutable needs to
	 * preserve any file based relationships other than the IFile.
	 * 
	 * @param targetSpecIFile
	 * @return
	 * @throws CoreException
	 */
	public FileTestExecutable copyTo(IFile targetSpecIFile) throws CoreException {
		// copy the spec file
		iFile.copy(targetSpecIFile.getFullPath(), false, null);
		
		//create and return the new instance
		return TestExecutableFactory.getInstance(targetSpecIFile);
	}

}
