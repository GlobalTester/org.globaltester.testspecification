package org.globaltester.testspecification.testframework;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;

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
		} else {
			BasicLogger.log("Unable to create TestExecutable for "+iFile.toString(), LogLevel.WARN);
		}

		return retVal;
	}

}
