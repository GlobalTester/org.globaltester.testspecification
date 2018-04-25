package org.globaltester.testspecification.testframework;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class TestExecutableFactory {

	/**
	 * Return an instance representing the given IFile
	 * 
	 * @param iFile
	 * @return
	 * @throws CoreException
	 */
	public static FileTestExecutable getInstance(IFile iFile) throws CoreException {

		FileTestExecutable retVal = null;

		if (TestCase.isFileRepresentation(iFile)) {
			retVal = new TestCase(iFile);
		} else if (TestCaseLegacy.isFileRepresentation(iFile)) {
			retVal = new TestCaseLegacy(iFile);
		}

		return retVal;
	}

}
