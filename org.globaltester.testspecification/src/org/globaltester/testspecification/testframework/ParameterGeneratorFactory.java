package org.globaltester.testspecification.testframework;

import java.lang.reflect.Constructor;

import org.eclipse.core.runtime.Platform;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.jdom.Attribute;
import org.jdom.Element;
import org.osgi.framework.Bundle;

public class ParameterGeneratorFactory {

	private static final String BUNDLE = "bundle";
	private static final String CLASS = "class";
	
	private static final String GENERATOR_STATIC = "static";
	private static final String GENERATOR_CLASS = CLASS;
	private static final String GENERATOR_CARTESIAN_PRODUCT = "cartesianProduct";
	
	public static ParameterGenerator createParameterGenerator(Element parametersElement) {
		if (parametersElement == null) return null;
		
		Attribute profileParseTypeAttribute = parametersElement.getAttribute("profileParseType");
		Attribute generatorAttribute = parametersElement.getAttribute("generator");
		if (generatorAttribute == null) return null;
		
		
		switch (generatorAttribute.getValue()) {
		case GENERATOR_STATIC:
			return new ParameterGeneratorStatic(parametersElement);
		case GENERATOR_CLASS:
			return createParameterGeneratorClass(parametersElement);
		case GENERATOR_CARTESIAN_PRODUCT:
			return new ParameterGeneratorCartesianProduct(parametersElement, profileParseTypeAttribute);

		default:
			BasicLogger.log("No ParameterGenerator found for value: " + generatorAttribute.getValue(), LogLevel.WARN);
			return null;
		}
	}

	private static ParameterGenerator createParameterGeneratorClass(Element parametersElement) {
		Attribute bundleAtt = parametersElement.getAttribute(BUNDLE);
		Attribute classAtt = parametersElement.getAttribute(CLASS);
		if ((bundleAtt == null) || (classAtt == null)) {
			BasicLogger.log("Attribute missing. Attributes \"bundle\" and \"class\" are required for ParameterGenerator of type \"class\"", LogLevel.WARN);
			return null;
		}
		
		Bundle generatorBundle = Platform.getBundle(bundleAtt.getValue());
		try {
			Class<?> generatorClazz = generatorBundle.loadClass(classAtt.getValue());
			Constructor<?> constructor = generatorClazz.getConstructor(Element.class);
			return (ParameterGenerator) constructor.newInstance(parametersElement);
		} catch (SecurityException | ReflectiveOperationException | IllegalArgumentException e) {
			BasicLogger.logException("ParameterGenerator can not be instantiated", e, LogLevel.ERROR);
		}

		
		return null;
	}
	
}
