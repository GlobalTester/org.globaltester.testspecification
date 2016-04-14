package org.globaltester.testspecification.testframework;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.globaltester.base.xml.XMLHelper;
import org.globaltester.logging.logger.TestLogger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * This class provides compatibility to the older TestCase format and allows
 * executing these tests in TestCampaigns. Using it for new TestScripts is
 * not encouraged.
 * 
 * @author jkoch
 */
public class TestCaseLegacy extends FileTestExecutable implements ILegacyConstants{

	private String testCaseId;
	private String testCaseTitle;
	private String testCaseVersion;
	private String testCasePurpose;
	private LinkedList<PreCondition> preConditions;
	private LinkedList<TestStep> testSteps;
	private LinkedList<PostCondition> postConditions;

	public TestCaseLegacy(IFile iFile) throws CoreException {
		super(iFile);
	}

	@Override
	protected void initFromIFile() {
		Assert.isNotNull(iFile);
		Document doc = XMLHelper.readDocument(iFile);
		
		Element root = doc.getRootElement();
		Namespace ns = root.getNamespace();
		Element testcase;
	
		// check that root element has correct name
		Assert.isTrue(root.getName().equals(rootLegacy),"Root element is not "+rootLegacy);
			
		testcase = root.getChild(testcaseLegacy, ns);
		testCaseId = testcase.getChild(testCaseIdLegacy, ns).getTextTrim();
		name = testCaseId;
		testCaseTitle = testcase.getChild(testCaseTitleLegacy, ns).getTextTrim();
		testCaseVersion = root.getChild(testCaseVersionLegacy, ns).getTextTrim();
		testCasePurpose = root.getChild(testCasePurposeLegacy, ns).getTextTrim();
		
		
		// extract Preconditions
		preConditions = new LinkedList<PreCondition>();
		Element preConElement = testcase.getChild(preconditionsLegacy, ns);
		PreCondition preCon = new PreCondition(preConElement, testCaseId+" - Preconditions", preconditionsLegacy);
		preConditions.add(preCon);
		
		// extract TestSteps from "testscript" node
		testSteps = new LinkedList<TestStep>();
		Element testElem = testcase.getChild(teststepsLegacy, ns);
		TestStep testStep = new TestStep(testElem, testCaseId+" - TestStep ", teststepsLegacy);
		testSteps.add(testStep);
		
		// extract Postconditions
		postConditions = new LinkedList<PostCondition>();
		Element postConElem = testcase.getChild(postconditionsLegacy, ns);
		PostCondition postCon = new PostCondition(postConElem, testCaseId+" - Postconditions ", postconditionsLegacy);
		postConditions.add(postCon);

	}

	/**
	 * checks whether the given file represents an TestCase object
	 * 
	 * @param iFile
	 * @return
	 */
	public static boolean isFileRepresentation(IFile iFile) {
		Document doc = XMLHelper.readDocument(iFile);
		if (doc != null) { 
			Element rootElem = doc.getRootElement();

			// check that root element has correct name
			if (!rootElem.getName().equals("testsuite")) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	public List<PreCondition> getPreConditions() {
		return preConditions;
	}

	public List<TestStep> getTestSteps() {
		return testSteps;
	}

	public List<PostCondition> getPostConditions() {
		return postConditions;
	}

	public void dumpTestCaseInfos() {
		String format = "Testcase %-10s %-46s -"; //format the output to be 100 chars wide (including log4j start of line)
		TestLogger.info(String.format(format, "Title:", testCaseTitle));
		TestLogger.info(String.format(format, "ID:", testCaseId));
		TestLogger.info(String.format(format, "version:", testCaseVersion));
		TestLogger.info("------------------------------------------------------------------ -");
	}

	public String getTestCaseID() {
		return testCaseId;
	}

	public String getTestCasePurpose() {
		return testCasePurpose;
	}

	@Override
	public List<ITestExecutable> getChildren() {
		LinkedList<ITestExecutable> children = new LinkedList<ITestExecutable>();
		
		//add test steps to list of children
		children.addAll(preConditions);
		children.addAll(testSteps);
		children.addAll(postConditions);
		
		return children;
	}

	@Override
	public boolean hasChildren() {
		if ((preConditions != null) && (!preConditions.isEmpty())) return true;
		if ((testSteps != null) && (!testSteps.isEmpty())) return true;
		if ((postConditions != null) && (!postConditions.isEmpty())) return true;
		
		return false;
	}
	
	@Override
	public FileTestExecutable copyTo(IFile targetSpecIFile) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
		
		//copy testcase
		iFile.copy(targetSpecIFile.getFullPath(), false, null);
		
		//copy dtd file
		IPath pathToTestCase = iFile.getFullPath();
		IPath pathTogrammar = pathToTestCase.removeLastSegments(3);
		IPath pathTogrammarDestination = targetSpecIFile.getFullPath().removeLastSegments(3);
		
		IPath dtdFilePath = pathTogrammar.append("testcase.dtd");
		IFile dtdFile = workspace.getRoot().getFile(dtdFilePath);

		
//		dtdFile.copy(pathTogrammarDestination, true, null);

//		IFile wSDLFile = (IFile) workspaceRoot.findMember(locationString);
//		String strPathToDtd="";
//		for(int i=0; i<tempPath.length-2; i++){
//			strPathToDtd+=tempPath[i]+File.separator;
//		}
//		IPath pathToDtd = new Path(strPathToDtd);
//		iFile.copy(targetSpecIFile.getFullPath(), false, null);
		return TestExecutableFactory.getInstance(targetSpecIFile);
	}
	
}
