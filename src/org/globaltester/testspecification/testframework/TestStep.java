package org.globaltester.testspecification.testframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.jdom.Element;
import org.jdom.Namespace;

public class TestStep implements ITestExecutable{

	private String stepId;
	private Element command;
	private String techCommand;
	private List<String> descriptions;
	private LinkedList<ExpectedResult> expResults;
	

	public TestStep(Element elem, String id) {
		Assert.isTrue(elem.getName().equals("TestStep"),
				"Element does not describe TestStep");
		Namespace ns = elem.getNamespace();
		
		stepId = id;
		command = elem.getChild("Command", ns);
		
		//extract TechincalResult if present
		Element techCommandElem = elem.getChild("TechnicalCommand", ns);
		if (techCommandElem != null) {
			techCommand = techCommandElem.getTextTrim();
		}
		
		//extract description elements
		@SuppressWarnings("rawtypes")
		Iterator descriptionElemsIter = elem.getChildren("Description", ns).iterator();
		descriptions = new ArrayList<String>();
		while (descriptionElemsIter.hasNext()) {
			Object curElem = descriptionElemsIter.next();
			if (curElem instanceof Element) {
				
				descriptions.add(((Element) curElem).getTextTrim());
			}
			
		}
		
		// extract ExpectedResults
		expResults = new LinkedList<ExpectedResult>();
		@SuppressWarnings("unchecked")
		List<Element> testStepElements = (List<Element>) elem.getChildren(
				"ExpectedResult", ns);
		int resultId = 1;
		for (Iterator<Element> iterator = testStepElements.iterator(); iterator
				.hasNext();) {
			Element curElem = iterator.next();
			ExpectedResult curExpResult = new ExpectedResult(curElem, stepId + " - "+resultId++);
			if (curExpResult != null) {
				expResults.add(curExpResult);
			}
		}
	}

	public Element getCommand() {
		return command;
	}
	
	public String getTechnicalCommand() {
		return techCommand;
	}

	public String getId() {
		return stepId;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public List<ExpectedResult> getExpectedResults() {
		return expResults;
	}

	@Override
	public List<ITestExecutable> getChildren() {
		// has no children
		return null;
	}

	@Override
	public boolean hasChildren() {
		// has no children
		return false;
	}

	@Override
	public String getName() {
		return getId();
	}

}
