package org.globaltester.testspecification.testframework;

import org.jdom.Element;

public class PostCondition extends ActionStep{

	public PostCondition(Element elem, String id) {
		super(elem, id);
	}
	
	public PostCondition(String techCommand, String id) {
		super(techCommand, id);
	}

	@Override
	protected String getElementName() {
		return "Postcondition";
	}

}
