package org.globaltester.testspecification.testframework;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class TestCase extends TestExecutable {

	public TestCase(IFile iFile) throws CoreException {
		super(iFile);
	}

}
