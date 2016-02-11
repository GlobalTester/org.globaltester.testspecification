package org.globaltester.testspecification.testframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.jdom.Element;
import org.jdom.Namespace;

public class ExpectedResult implements ITestExecutable{

	private String resultId;
	private String techResult;
	private List<String> descriptions;
	

	public ExpectedResult(Element elem, String id) {
		Assert.isTrue(elem.getName().equals("ExpectedResult"),
				"Element does not describe ExpectedResult");
		Namespace ns = elem.getNamespace();
		
		resultId = id;
		
		//extract TechincalResult if present
		Element techResElem = elem.getChild("TechnicalResult", ns);
		if (techResElem != null) {
			techResult = techResElem.getText();
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
		
	}

	public String getTechnicalResult() {
		return techResult;
	}

	public String getId() {
		return resultId;
	}

	public List<String> getDescriptions() {
		return descriptions;
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
