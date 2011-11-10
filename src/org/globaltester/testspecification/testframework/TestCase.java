package org.globaltester.testspecification.testframework;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.core.xml.XMLHelper;
import org.globaltester.logging.logger.TestLogger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class TestCase extends FileTestExecutable {

	private String testCaseId;
	private String testCaseTitle;
	private String testCaseVersion;
	private String testCasePurpose;
	private LinkedList<TestStep> testSteps;

	public TestCase(IFile iFile) throws CoreException {
		super(iFile);
	}

	@Override
	protected void initFromIFile() {
		Assert.isNotNull(iFile);
		Document doc = XMLHelper.readDocument(iFile);
		Element root = doc.getRootElement();
		Namespace ns = root.getNamespace();

		// check that root element has correct name
		Assert.isTrue(root.getName().equals("TestCase"),
				"Root element is not TestCase");

		// extract metadata
		testCaseId = root.getAttribute("id").getValue().trim();
		name = testCaseId;
		testCaseTitle = root.getChild("Title", ns).getTextTrim();
		testCaseVersion = root.getChild("Version", ns).getTextTrim();
		testCasePurpose = root.getChild("Purpose", ns).getTextTrim();

		// extract TestSteps
		testSteps = new LinkedList<TestStep>();
		@SuppressWarnings("unchecked")
		List<Element> testStepElements = (List<Element>) root.getChildren(
				"TestStep", ns);
		int stepId = 1;
		for (Iterator<Element> iterator = testStepElements.iterator(); iterator
				.hasNext();) {
			Element curElem = iterator.next();
			TestStep curTestStep = new TestStep(curElem, testCaseId+" - "+stepId++);
			if (curTestStep != null) {
				testSteps.add(curTestStep);
			}
		}

	}

	/**
	 * checks whether the given file represents an TestCase object
	 * 
	 * @param iFile
	 * @return
	 */
	public static boolean isFileRepresentation(IFile iFile) {
		Document doc = XMLHelper.readDocument(iFile);
		Element rootElem = doc.getRootElement();

		// check that root element has correct name
		if (!rootElem.getName().equals("TestCase")) {
			return false;
		}

		return true;
	}

	public List<TestStep> getTestSteps() {
		return testSteps;
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
		children.addAll(testSteps);
		
		return children;
	}

	@Override
	public boolean hasChildren() {
		return !testSteps.isEmpty();
	}

}
