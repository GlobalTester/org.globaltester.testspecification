package org.globaltester.testspecification.testframework;

/**
 * provides central constants needed to parse GLobalTester tests which use the
 * legacy format
 * 
 * @author jkoch
 *
 */
public interface ILegacyConstants {
	public static final String rootLegacy = "testsuite";
	public static final String testcaseLegacy = "testcase";
	public static final String preconditionsLegacy = "preconditions";
	public static final String teststepsLegacy = "testscript";
	public static final String postconditionsLegacy = "postconditions";
	public static final String testCaseIdLegacy = "testcaseid"; 
	public static final String testCaseTitleLegacy = "shortdescription"; 
	public static final String testCaseVersionLegacy = "version"; 
	public static final String testCasePurposeLegacy = "description"; 
	public static final String testCaseProfile = "profile";
	
	//For testsuites
	public static final String testcasesLegacy = "testcases"; 
	
}
