package org.globaltester.testspecification.testframework;

import org.jdom.Element;

public class PostCondition extends ActionStep{

	public PostCondition(Element elem, String id) {
		super(elem, id);
	}
	
	public PostCondition(Element elem, String id, String checkElement) {
		super(elem, id, checkElement);
	}

	@Override
	protected String getElementName() {
		return "Postcondition";
	}

}
