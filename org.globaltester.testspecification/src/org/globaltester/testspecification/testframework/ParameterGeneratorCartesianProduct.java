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
	
	ArrayList<ArrayList<Element>> paramList = new ArrayList<>(); //ArrayList containg List of Param XML Objects
	ArrayList<TestCaseParameter> generatedParameters = new ArrayList<>();

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
	 * create Cartesian Product of given ArrayList containing ArrayList of Elements
	 * */
	private ArrayList<ArrayList<Element>> doCartesianProduct (ArrayList<ArrayList<Element>> givenList, ArrayList<ArrayList<Element>> resList, int k) {
		if (k == givenList.size()){ //reached end of list
			return resList;
		}
		ArrayList<Element> tmp = new ArrayList<>();
		for (Element e : givenList.get(k)) {
			tmp.add(e);
		}
		return doCartesianProduct(givenList, resList, k+1);
	
	}


	@Override
	public ArrayList<TestCaseParameter> generateParameters(SampleConfig sampleConfig) throws ParameterGenerationFailedException {
		return null;
	}
	
	
}
