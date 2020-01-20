package org.globaltester.testspecification.testframework;

import java.util.ArrayList;

import org.globaltester.sampleconfiguration.SampleConfig;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class ParameterGeneratorCross {
	
	ArrayList<ParameterGenerator> generatedGenerators = new ArrayList<>();

	public ParameterGeneratorCross (Element parametersElement) {
		Namespace ns = parametersElement.getNamespace();
		for (Object curParameters: parametersElement.getChildren("Parameters", ns)) {
			if (curParameters instanceof Element) {
				generatedGenerators.add(ParameterGeneratorFactory.createParameterGenerator((Element) curParameters));
			}
		}
	}
	
	
	public ArrayList<ParameterGenerator> generateGenerators(SampleConfig sampleConfig) {
		return generatedGenerators;
	}
	
	
}
