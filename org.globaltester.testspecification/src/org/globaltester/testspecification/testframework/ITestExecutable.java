package org.globaltester.testspecification.testframework;

import java.util.List;

public interface ITestExecutable {

	public String getName();

	public List<ITestExecutable> getChildren();

	public boolean hasChildren();
	
}
