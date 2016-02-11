package org.globaltester.testspecification.testframework;

import org.jdom.Element;

public class TestStep extends ActionStep{

	public TestStep(Element elem, String id) {
		super(elem, id);
	}

	@Override
	protected String getElementName() {
		return "TestStep";
	}

}
