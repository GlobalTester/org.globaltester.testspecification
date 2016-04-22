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

public class TestLayer implements ITestCaseConstants {

	public static boolean isFileRepresentation(IFile iFile){
		Document doc = XMLHelper.readDocument(iFile);
		if (doc != null) { 
			String checkElem = doc.getRootElement().getName();

			if (checkElem==testLayer){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}
	
	public static LinkedList<IFile> extractTests(IFile sourceFile) {
		Assert.isNotNull(sourceFile);
		LinkedList<IFile> foundTests=new LinkedList<IFile>();
		Document doc = XMLHelper.readDocument(sourceFile);
		
		Element root = doc.getRootElement();
		Namespace ns = root.getNamespace();
	
		// check that root element has correct name
		Assert.isTrue(root.getName().equals(testLayer),"Root element is not "+testLayer);
		
		@SuppressWarnings("unchecked")
		List<Element> testUnits = root.getChildren(testUnit, ns);
		
		for (int i = 0; i < testUnits.size(); i++) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath pathToTc = sourceFile.getFullPath().removeLastSegments(1).append(testUnits.get(i).getText());
			IFile tu = workspace.getRoot().getFile(pathToTc);
			foundTests.addAll(TestUnit.extractTests(tu));
		}

		
		return foundTests;
	}
}
