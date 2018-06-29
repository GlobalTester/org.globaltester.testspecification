package org.globaltester.testspecification.testframework;

import java.util.ArrayList;

public interface ParameterGenerator {

	/**
	 * Generate the actual parameters used
	 * @return
	 */
	public ArrayList<TestCaseParameter> generateParameters();

}
