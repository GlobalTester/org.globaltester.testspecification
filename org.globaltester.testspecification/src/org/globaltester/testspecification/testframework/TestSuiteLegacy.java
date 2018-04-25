package org.globaltester.testspecification.testframework;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.globaltester.base.xml.XMLHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class TestSuiteLegacy  implements ILegacyConstants{
	
    public static final String FILE_ENDING_GT_TEST_SUITE = "gtsuite";

	public static LinkedList<IFile> extractTests(IFile sourceFile) {
		Assert.isNotNull(sourceFile);
		LinkedList<IFile> foundTests=new LinkedList<IFile>();
		Document doc = XMLHelper.readDocument(sourceFile);
		
		Element root = doc.getRootElement();
		Namespace ns = root.getNamespace();
	
		// check that root element has correct name
		Assert.isTrue(root.getName().equals(rootLegacy),"Root element is not "+rootLegacy);
		
		Element testCasesElement = root.getChild(testcasesLegacy, ns);
		
		@SuppressWarnings("unchecked")
		List<Element> testCases = testCasesElement.getChildren(testcaseLegacy, ns);
		
		for (int i = 0; i < testCases.size(); i++) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath pathToTc = sourceFile.getFullPath().removeLastSegments(1).append(testCases.get(i).getText());
			IFile tc = workspace.getRoot().getFile(pathToTc);
			foundTests.add(tc);
		}
		
		return foundTests;
	}
	
	public static boolean isFileRepresentation(IFile iFile){
		Document doc = XMLHelper.readDocument(iFile);
		if (doc != null) { 
			Element checkElem = doc.getRootElement().getChild(ILegacyConstants.testcasesLegacy);

			if (checkElem==null) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
}
