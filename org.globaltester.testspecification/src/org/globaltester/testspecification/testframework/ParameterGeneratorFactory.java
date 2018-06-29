package org.globaltester.testspecification.testframework;

import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.jdom.Attribute;
import org.jdom.Element;

//FIXME MBK move ParameterGeneratorFactory and implementations to another bundle/package? 
public class ParameterGeneratorFactory {

	private static final String GENERATOR_STATIC = "static";

	public static ParameterGenerator createParameterGenerator(Element parametersElement) {
		if (parametersElement == null) return null;
		
		Attribute generatorAttribute = parametersElement.getAttribute("generator");
		if (generatorAttribute == null) return null;
		
		switch (generatorAttribute.getValue()) {
		case GENERATOR_STATIC:
			return new ParameterGeneratorStatic(parametersElement);

		default:
			BasicLogger.log("No ParameterGenerator found for value: " + generatorAttribute.getValue(), LogLevel.WARN);
			return null;
		}

		

	}
}
