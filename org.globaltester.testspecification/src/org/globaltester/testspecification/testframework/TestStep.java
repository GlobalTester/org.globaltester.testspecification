package org.globaltester.testspecification.testframework;

import org.jdom.Element;

public class TestStep extends ActionStep{

	public TestStep(Element elem, String id) {
		super(elem, id);
	}
	
	public TestStep(Element elem, String id, String checkElement) {
		super(elem, id, checkElement);
	}

	@Override
	protected String getElementName() {
		return "TestStep";
	}

}
