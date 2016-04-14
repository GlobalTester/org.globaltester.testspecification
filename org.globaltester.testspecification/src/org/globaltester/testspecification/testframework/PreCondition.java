package org.globaltester.testspecification.testframework;

import org.jdom.Element;

public class PreCondition extends ActionStep{

	public PreCondition(Element elem, String id) {
		super(elem, id);
	}
	
	public PreCondition(Element elem, String id, String checkElement) {
		super(elem, id, checkElement);
	}

	@Override
	protected String getElementName() {
		return "Precondition";
	}
	
	

}
