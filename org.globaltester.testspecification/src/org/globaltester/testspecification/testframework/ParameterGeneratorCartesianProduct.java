package org.globaltester.testspecification.testframework;

import java.util.ArrayList;

import org.globaltester.sampleconfiguration.SampleConfig;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;


/**
 * Generator for TestCaseParameters derived from Cartesian Product of multiple Parameters Elements
 * 
 * @author fpamuk
 *
 */
public class ParameterGeneratorCartesianProduct implements ParameterGenerator {
	
	ArrayList<TestCaseParameter> generatedParameters = new ArrayList<>();
	private static ArrayList<ArrayList<Element>> paramList = new ArrayList<>(); //ArrayList containg List of Param XML Objects
	private static ArrayList<ArrayList<Element>> resList = new ArrayList<>();

	public ParameterGeneratorCartesianProduct (Element parametersElement) {
		Namespace ns = parametersElement.getNamespace();
		// iterate through the root Parameters Element
		for (Object curParameters: parametersElement.getChildren("Parameters", ns)) {
			if (curParameters instanceof Element) {
				// iterate through child Parameter Elements
				ArrayList<Element> tmpList = new ArrayList<>();
				for (Object curParam: ((Element) curParameters).getChildren()) {
					if (curParam instanceof Element) {
						tmpList.add((Element) curParam);
					}
				}
				// add tmpList of params to rootList containing list of params for respective parent
				paramList.add(tmpList);
			}
		}
		
		resList.add(paramList.get(0));
		//make a crossproduct TestCaseParameter
		
		
	}
	
	
	/*
	 * extract idSuffix & Attributes from given Param Element
	 * 
	 * Returns:
	 * 		TestCaseParameter
	 * */
	private TestCaseParameter extractFromParam (Element paramElement) {
		String idSuffix = paramElement.getAttributeValue("idSuffix");

		TestCaseParameter curParam = new TestCaseParameter(idSuffix);
		
		for (Object curAttribObj : paramElement.getAttributes()) {
			if (curAttribObj instanceof Attribute) {
				Attribute curAttrib = (Attribute) curAttribObj;
				curParam.put(curAttrib.getName(), curAttrib.getValue());
			}
			
		}
		
		return curParam;
	}
	
	/*
	 * create Cartesian Product given ArrayList @paramList containing ArrayList of Elements
	 * */
	private static void doCartesianProduct (int k) {
		if (k == paramList.size()){ //reached end of list
			return;
		}
		ArrayList<Element> tmp = new ArrayList<>();
		for (Element e : paramList.get(k-1)) {
			tmp.add(e);
		}
		doCartesianProduct(k+1);
	
	}


	@Override
	public ArrayList<TestCaseParameter> generateParameters(SampleConfig sampleConfig) throws ParameterGenerationFailedException {
		return null;
	}
	
	
}
