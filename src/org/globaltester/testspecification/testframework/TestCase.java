package org.globaltester.testspecification.testframework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private LinkedList<PreCondition> preConditions;
	private LinkedList<TestStep> testSteps;
	private LinkedList<PostCondition> postConditions;

	public TestCase(IFile iFile) throws CoreException {
		super(iFile);
	}

	@Override
	protected void initFromIFile() {
		Assert.isNotNull(iFile);
		Document doc = XMLHelper.readDocument(iFile);
		if (doc != null) {
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
	
			// extract Preconditions
			preConditions = new LinkedList<PreCondition>();
			@SuppressWarnings("unchecked")
			List<Element> preConElements = (List<Element>) root.getChildren(
					"Precondition", ns);
			int preConId = 1;
			for (Iterator<Element> iterator = preConElements.iterator(); iterator
					.hasNext();) {
				Element curElem = iterator.next();
				PreCondition curPreCon = new PreCondition(curElem, testCaseId+" - Precondition "+preConId++);
				if (curPreCon != null) {
					preConditions.add(curPreCon);
				}
			}
			
			// extract TestSteps
			testSteps = new LinkedList<TestStep>();
			@SuppressWarnings("unchecked")
			List<Element> testStepElements = (List<Element>) root.getChildren(
					"TestStep", ns);
			int stepId = 1;
			for (Iterator<Element> iterator = testStepElements.iterator(); iterator
					.hasNext();) {
				Element curElem = iterator.next();
				TestStep curTestStep = new TestStep(curElem, testCaseId+" - TestStep "+stepId++);
				if (curTestStep != null) {
					testSteps.add(curTestStep);
				}
			}
	
			// extract Postconditions
			postConditions = new LinkedList<PostCondition>();
			@SuppressWarnings("unchecked")
			List<Element> postConElements = (List<Element>) root.getChildren(
					"Postcondition", ns);
			int postConId = 1;
			for (Iterator<Element> iterator = postConElements.iterator(); iterator
					.hasNext();) {
				Element curElem = iterator.next();
				PostCondition curPostCon = new PostCondition(curElem, testCaseId+" - Postcondition "+postConId++);
				if (curPostCon != null) {
					postConditions.add(curPostCon);
				}
			}
		} else {
			//ToDo Error Handling
			
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
		if (doc != null) { 
			Element rootElem = doc.getRootElement();

			// check that root element has correct name
			if (!rootElem.getName().equals("TestCase")) {
				return false;
			}
		}
		
		return true;
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

	public static void createDefaultTestCase(IFile iFile) {
		BufferedWriter out = null;
		try {
			File file = new File(iFile.getLocationURI());
			// Create file
			FileWriter fstream = new FileWriter(file);
			out = new BufferedWriter(fstream);
			out.write(defaultTestCase);
			
			
			} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static final String defaultTestCase = ""+
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	"<TestCase id=\"TestCase\" xmlns=\"http://globaltester.org/testspecification\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://globaltester.org/schema/testspecification.xsd\">\n" +
	"	<Title>Sample TestCase</Title>\n" +
	"	<Version>1.00</Version>\n" +
	"	<Purpose>This test is used as simple base testcase for your own modifications</Purpose>\n" +
	"	<Profile></Profile>\n" +
	"	<Reference></Reference>\n" +
	"	<Precondition>\n" +
	"		<Command>\n" +
	"			<Text>Reset card</Text>\n" +
	"		</Command>\n" +
	"		<TechnicalCommand>\n" +
	"            card.gt_reset();\n" +
	"		</TechnicalCommand>\n" +
	"	</Precondition>\n" +
	"	<TestStep>\n"+
	"		<Command xsi:type=\"APDUCommand\">\n"+
	"			<Text>Select the MF application</Text>\n"+
	"			<APDU>00 A4 3F 00 00</APDU>\n"+
	"		</Command>\n"+
	"		<Description>\n"+
	"			Select the MF\n"+
	"		</Description>\n"+
	"		<ExpectedResult xsi:type=\"APDUResult\">\n"+
	"			<Text>First byte MUST be '61'</Text>\n"+
	"			<TechnicalResult>\n"+
	"				assertStatusWord(new Array(\"9000\"), card.SW.toString(HEX), FAILURE);\n"+
	"				assertMatchValue(\"61\", dg1.bytes(0,1), FAILURE);\n"+
	"			</TechnicalResult>\n"+
	"		</ExpectedResult>\n"+
	"	</TestStep>\n"+
	"	<Postcondition>\n"+
	"	</Postcondition>\n"+
	"</TestCase>";
	
}
