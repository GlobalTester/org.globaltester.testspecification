package org.globaltester.testspecification.testframework;

import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class TestExecutableFactory {

	private static Hashtable<IFile, FileTestExecutable> instances = new Hashtable<IFile, FileTestExecutable>();

	/**
	 * Return the instance representing the given IFile
	 * 
	 * @param iFile
	 * @return
	 * @throws CoreException 
	 */
	public static FileTestExecutable getInstance(IFile iFile) throws CoreException {

		if (!instances.containsKey(iFile)) {
			FileTestExecutable newExecutionInstance = null;

			if (TestCase.isFileRepresentation(iFile)) {
				newExecutionInstance = new TestCase(iFile);
			}
			
			if (newExecutionInstance != null) {
				instances.put(iFile, newExecutionInstance);
			} else {
				return null;
			}
		}

		return instances.get(iFile);
	}

}
