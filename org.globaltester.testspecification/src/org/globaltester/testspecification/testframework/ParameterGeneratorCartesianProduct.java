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
	private static ArrayList<ArrayList<Element>> paramList = new ArrayList<>(); //ArrayList containg List of Param Element(XML) Objects
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
		
		//create cartesianProduct of given Generator Elements
		resList = cartesianProduct(paramList);
	}
	
	
	/*
	 * extract idSuffix & Attributes from given Param Element
	 * In case of equal Attribute Ids: Take the first occuring one!
	 * 
	 * Returns:
	 * 		TestCaseParameter
	 * */
	private TestCaseParameter extractFromParamList (ArrayList<Element> paramList) {
//		String idSuffix = paramElement.getAttributeValue("idSuffix");
		TestCaseParameter tcp = new TestCaseParameter("");
		ArrayList<Attribute> attributes = new ArrayList<>();
		// get all attributes
		for (Element param: paramList) {
			attributes.addAll(param.getAttributes());
		}
		// set suffix
		for (Attribute attr : attributes) {
			if (attr.getValue().equals("idSuffix") || attr.getValue().equals("suffix")) {
				tcp.appendIdSuffix(attr.getValue());
			}
		}
		for (Attribute attr : attributes) {
			if (tcp.contains(attr.getValue())) continue;
			tcp.put(attr.getName(), attr.getValue());
		}
		return tcp;
	}
	
	/*
	 * create Cartesian Product given ArrayList @paramList containing ArrayList of Elements
	 * 
	 * e.g. (p1,p2), (p1',p2') --> (p1,p1'), (p1,p2'), (p2,p1'), (p2,p2')
	 * */
	private static ArrayList<ArrayList<Element>> cartesianProduct(ArrayList<ArrayList<Element>> lists) {
	    if (lists.size() < 2)
	        throw new IllegalArgumentException(
	                "Can't have a product of fewer than two sets (got " +
	                lists.size() + ")");

	    return _cartesianProduct(0, lists);
	}
	
	private static ArrayList<ArrayList<Element>> _cartesianProduct(int index, ArrayList<ArrayList<Element>> lists) {
		// create result Array
		ArrayList<ArrayList<Element>> res = new ArrayList<ArrayList<Element>>();
		if (index== lists.size()) {
			res.add(new ArrayList<Element>());
		} else {
			for (Element s: lists.get(index)) {
				for (ArrayList<Element> list: _cartesianProduct(index+1, lists)) {
					list.add(s);
					res.add(list);
				}
			}
		}
		return res;
	}


	@Override
	public ArrayList<TestCaseParameter> generateParameters(SampleConfig sampleConfig) throws ParameterGenerationFailedException {
		// variable containing ArrayList of TestCaseParameters
		ArrayList<TestCaseParameter> generatedParameters = new ArrayList<>();
		// fill List
		for (ArrayList<Element> parentList : resList) {
			TestCaseParameter tcp = extractFromParamList(parentList);
			generatedParameters.add(tcp);
		}
		return generatedParameters;
	}
	
	
}
