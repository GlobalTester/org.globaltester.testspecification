package org.globaltester.testspecification.testframework;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.globaltester.base.resources.GtResourceHelper;
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
		testCasePurpose = root.getChild(testCasePurposeLegacy, ns).getTextTrim();
		profileExpression = ProfileMapper.parse(testcase.getChild(testCaseProfile, ns).getTextTrim(), getPropertyFiles());
		
		
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
	
	@Override
	public FileTestExecutable copyTo(final IFile targetSpecIFile) throws CoreException {		
		try {
			IPath targetFolder = targetSpecIFile.getFullPath().uptoSegment(3);
			File targetFile = ResourcesPlugin.getWorkspace().getRoot().getFolder(targetFolder).getLocation().toFile();
			
			IFile manifest = iFile.getProject().getFolder("META-INF").getFile("MANIFEST.MF");
			IProject [] dependencies = getDeps(manifest);
			
			for (IProject dep : dependencies){
				GtResourceHelper.copyFiles(dep.getLocation().toFile(), ResourcesPlugin.getWorkspace().getRoot().getFolder(targetFolder.removeLastSegments(1).append(dep.getName())).getLocation().toFile());
			}

			GtResourceHelper.copyFiles(iFile.getProject().getLocation().toFile(), targetFile);
			targetSpecIFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return TestExecutableFactory.getInstance(targetSpecIFile);
	}

	private IProject[] getDeps(IFile manifestFile) {
		List<IProject> deps = new LinkedList<>(); 
		try {
			Manifest manifest = new Manifest(manifestFile.getContents());
			String dependencyString = manifest.getMainAttributes().getValue("Require-Bundle");
			String [] dependencies = dependencyString.split(",");
			for (String current : dependencies){
				if (current.contains(";")){
					current = current.substring(0, current.indexOf(";"));
				}
				
				for (IProject currentProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()){
					if (currentProject.getLocation().lastSegment().equals(current)){
						deps.add(currentProject);
						break;
					}
				}
				
			}
		} catch (IOException | CoreException e) {
			return null;
		}
		
		return deps.toArray(new IProject [deps.size()]);
	}
	
}
