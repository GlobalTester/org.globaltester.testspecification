package org.globaltester.testspecification.testframework;

/**
 * This exception is raised whenever the ParameterGenerator generation for
 * ParameterGenerator fails.
 * 
 * @author fpamuk
 *
 */
public class CrossParameterGeneratorFailedException extends Exception {
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 6533626274262109475L;
	
	public static final String DEFAULT_MSG = "generation of ParamaterGenerators failed";

	public CrossParameterGeneratorFailedException(Throwable cause) {
		super(DEFAULT_MSG, cause);
	}
}
