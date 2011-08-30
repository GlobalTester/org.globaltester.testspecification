package org.globaltester.testspecification.testframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.jdom.Element;
import org.jdom.Namespace;

public class TestStep implements ITestExecutable{

	private String stepId;
	private Element command;
	private String techCommand;
	private List<String> descriptions;
	

	public TestStep(Element elem, String id) {
		Assert.isTrue(elem.getName().equals("TestStep"),
				"Element does not describe TestStep");
		Namespace ns = elem.getNamespace();
		
		stepId = id;
		command = elem.getChild("Command", ns);
		techCommand = elem.getChild("TechnicalCommand", ns).getTextTrim();
		
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

}
