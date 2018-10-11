package org.globaltester.testspecification.testframework;

import java.io.IOException;
import java.util.List;

import org.globaltester.sampleconfiguration.SampleConfig;

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
	 * @param sampleConfig provide access to SampleConfig
	 * 
	 * @return
	 * @throws IOException 
	 */
	public List<TestCaseParameter> generateParameters(SampleConfig sampleConfig) throws ParameterGenerationFailedException;

}
