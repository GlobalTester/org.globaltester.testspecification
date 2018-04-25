package org.globaltester.testspecification.testframework;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.jdom.Element;

/**
 * A set of TestExecutables that can be executed from a TestSession or a
 * TestCampaign.
 * 
 * @author amay
 *
 */
public class TestSet implements ITestExecutable {

	public static final String XML_ELEMENT = "TestSet";
	public static final String XML_ELEM_CHILD = "TestSetChild";
	
	List<FileTestExecutable> children = new ArrayList<>();

	/**
	 * Construct  an empty TestSet
	 */
	public TestSet() {
	}
	
	/**
	 * Construct a TestSet containing TestCases from the given IFiles
	 * @param files
	 * @throws CoreException 
	 */
	public TestSet(List<IFile> files) throws CoreException {
		for (IFile currentFile : files) {
			add(TestExecutableFactory.getInstance(currentFile));
		}
	}

	/**
	 * Construct a {@link TestSet} based on the given XML element
	 * @param testSetElem
	 */
	public TestSet(Element xmlElem) throws CoreException {
		initFromXmlElement(xmlElem);
	}

	private void initFromXmlElement(Element xmlElem) throws CoreException {
		@SuppressWarnings("unchecked")
		List<Element> childElements = xmlElem.getChildren(XML_ELEM_CHILD);
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		for (Element curChildElem : childElements) {
			IPath fileName = new Path(curChildElem.getTextTrim());
			IFile iFile = workspaceRoot.getFileForLocation(fileName);

			FileTestExecutable curChild = TestExecutableFactory.getInstance(iFile);
			if (curChild != null) {
				children.add(curChild);
			}
		}

	}

	public Element getXmlRepresentation() {
		Element xmlElem = new Element(XML_ELEMENT);

		// create XML elements for children and add them to xmlElem
		for (FileTestExecutable curChild : children) {		
			Element childElem = new Element(XML_ELEM_CHILD);
			childElem.addContent(curChild.getIFile().getProjectRelativePath().toString());
			xmlElem.addContent(childElem);
		}

		return xmlElem;
	}

	@Override
	public List<ITestExecutable> getChildren() {
		ArrayList<ITestExecutable> retVal = new ArrayList<>();
		retVal.addAll(children);
		
		return retVal;
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public String getName() {
		return "TestSet";
	}

	public void add(FileTestExecutable newChild) {
		children.add(newChild);
	}

	public void addAll(List<FileTestExecutable> newChildren) {
		children.addAll(newChildren);
	}

	public boolean isEmpty() {
		return children.isEmpty();
	}

}
