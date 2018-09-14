package org.globaltester.testspecification.testframework;

import java.lang.reflect.Constructor;

import org.eclipse.core.runtime.Platform;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.jdom.Attribute;
import org.jdom.Element;
import org.osgi.framework.Bundle;

public class ParameterGeneratorFactory {

	private static final String GENERATOR_STATIC = "static";
	private static final String GENERATOR_CLASS = "class";

	public static ParameterGenerator createParameterGenerator(Element parametersElement) {
		if (parametersElement == null) return null;
		
		Attribute generatorAttribute = parametersElement.getAttribute("generator");
		if (generatorAttribute == null) return null;
		
		switch (generatorAttribute.getValue()) {
		case GENERATOR_STATIC:
			return new ParameterGeneratorStatic(parametersElement);
		case GENERATOR_CLASS:
			Attribute bundleAtt = parametersElement.getAttribute("bundle");
			Attribute classAtt = parametersElement.getAttribute("class");
			if ((bundleAtt == null) || (classAtt == null)) {
				BasicLogger.log("Attribute missing. Attributes \"bundle\" and \"class\" are required for ParameterGenerator of type \"class\"", LogLevel.WARN);
				return null;
			}
			
			return createParameterGeneratorFromBundle(bundleAtt.getValue(), classAtt.getValue(), parametersElement);

		default:
			BasicLogger.log("No ParameterGenerator found for value: " + generatorAttribute.getValue(), LogLevel.WARN);
			return null;
		}

		

	}

	private static ParameterGenerator createParameterGeneratorFromBundle(String bundle, String classname,
			Element parametersElement) {
		
		Bundle generatorBundle = Platform.getBundle(bundle);
		
		try {
			Class<?> generatorClazz = generatorBundle.loadClass(classname);
			Constructor<?> constructor = generatorClazz.getConstructor(Element.class);
			return (ParameterGenerator) constructor.newInstance(parametersElement);
		} catch (NullPointerException | SecurityException | ReflectiveOperationException | IllegalArgumentException e) {
			BasicLogger.logException("ParameterGenerator can not be instantiated", e, LogLevel.ERROR);
		}

		
		return null;
	}
}
