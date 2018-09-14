package org.globaltester.testspecification.testframework;

/**
 * This exception is raised whenever the parameter generation for
 * TestCaseParameters fails. This must cause the TestCaseExecution to fail as well.
 * 
 * @author amay
 *
 */
public class ParameterGenerationFailedException extends Exception {

	private static final long serialVersionUID = 4694325766339304456L;
	
	public static final String DEFAULT_MSG = "generation of testcase parameters failed";

	public ParameterGenerationFailedException(Throwable cause) {
		super(DEFAULT_MSG, cause);
	}
	
	

}
