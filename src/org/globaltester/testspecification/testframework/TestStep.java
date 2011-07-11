package org.globaltester.testspecification.testframework;

import org.eclipse.core.runtime.Assert;
import org.jdom.Element;
import org.jdom.Namespace;

public class TestStep {

	private Element command;
	private String techCommand;

	public TestStep(Element elem) {
		Assert.isTrue(elem.getName().equals("TestStep"),
				"Element does not describe TestStep");
		Namespace ns = elem.getNamespace();

		command = elem.getChild("Command", ns);
		techCommand = elem.getChild("TechnicalCommand", ns).getTextTrim();
	}

	public String getTechnicalCommand() {
		return techCommand;
	}

}
