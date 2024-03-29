package org.globaltester.testspecification.testframework;

import java.util.ArrayList;

import org.globaltester.sampleconfiguration.SampleConfig;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * Generator for TestCaseParameters from values statically defined within test case definition
 * @author amay
 *
 */
public class ParameterGeneratorStatic implements ParameterGenerator {
	
	ArrayList<TestCaseParameter> generatedParameters = new ArrayList<>();

	public ParameterGeneratorStatic(Element parametersElement) {
		Namespace ns = parametersElement.getNamespace();
		for (Object curChildObject : parametersElement.getChildren("Param", ns)) {
			if (curChildObject instanceof Element) {
				Element curChild = (Element) curChildObject;
				String idSuffix = curChild.getAttributeValue("idSuffix");
				TestCaseParameter curParam = createParam(curChild, idSuffix);
				if (curParam != null){
					generatedParameters.add(curParam);
				}
			}
		}
		
	}

	protected TestCaseParameter createParam(Element curChild, String idSuffix) {
		TestCaseParameter curParam = new TestCaseParameter(idSuffix);
		
		for (Object curAttribObj : curChild.getAttributes()) {
			if (curAttribObj instanceof Attribute) {
				Attribute curAttrib = (Attribute) curAttribObj;
				curParam.put(curAttrib.getName(), curAttrib.getValue());
			}
			
		}
		return curParam;
	}

	@Override
	public ArrayList<TestCaseParameter> generateParameters(SampleConfig sampleConfig) {
		return generatedParameters;
	}

}
