package org.globaltester.testspecification.testframework;

import java.util.ArrayList;

/**
 * Parameter generator can be referenced in TestCase XMl and allows the testcase
 * to be executed multiple times with different parameters. <br/>
 * During execution the {@link ParameterGenerator#generateParameters()} will be
 * called to generate the {@link TestCaseParameter}s for the concrete execution
 * instances.
 * 
 * @author amay
 *
 */
public interface ParameterGenerator {

	/**
	 * Generate the actual parameters used for specific parametrized executions
	 * 
	 * @return
	 */
	public ArrayList<TestCaseParameter> generateParameters();

}
