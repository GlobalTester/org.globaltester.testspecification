package org.globaltester.testspecification.testframework;

import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.base.xml.XMLHelper;
import org.globaltester.sampleconfiguration.profiles.ProfileMapper;
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
public class TestCaseLegacy extends TestCase implements ILegacyConstants{

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
		testCasePurpose = testcase.getChild(testCasePurposeLegacy, ns).getTextTrim();
		profileString = testcase.getChild(testCaseProfile, ns).getTextTrim();
		profileExpression = ProfileMapper.parse(profileString, getPropertyFiles());
		
		// extract parameter generator
		Element parametersElement = testcase.getChild(testCaseParameter, ns);
		if (parametersElement!= null) {
			paramGenerator = ParameterGeneratorFactory.createParameterGenerator(parametersElement);
		}
		
		// extract Preconditions
		preConditions = new LinkedList<PreCondition>();
		Element preConElement = testcase.getChild(preconditionsLegacy, ns);
		PreCondition preCon = new PreCondition(preConElement.getText(), testCaseId+" - Preconditions");
		preConditions.add(preCon);
		
		// extract TestSteps from "testscript" node
		testSteps = new LinkedList<TestStep>();
		Element testElem = testcase.getChild(teststepsLegacy, ns);
		TestStep testStep = new TestStep(testElem.getText(), testCaseId+" - Teststep ");
		testSteps.add(testStep);
		
		// extract Postconditions
		postConditions = new LinkedList<PostCondition>();
		Element postConElem = testcase.getChild(postconditionsLegacy, ns);
		PostCondition postCon = new PostCondition(postConElem.getText(), testCaseId+" - Postconditions ");
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
	
}
