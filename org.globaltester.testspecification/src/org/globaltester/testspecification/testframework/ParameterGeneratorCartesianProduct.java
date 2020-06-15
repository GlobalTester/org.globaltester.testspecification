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
	Attribute profileParseType;
	private static ArrayList<ArrayList<TestCaseParameter>> paramList; //ArrayList containg List of Param Element(XML) Objects
	private static ArrayList<ArrayList<TestCaseParameter>> resList;
	private static ArrayList<ParameterGenerator> generators;
	

	public ParameterGeneratorCartesianProduct (Element parametersElement, Attribute profileParseType) {
		this.profileParseType = profileParseType;
		paramList = new ArrayList<>();
		resList = new ArrayList<>();
		generators = new ArrayList<>();
		Namespace ns = parametersElement.getNamespace();
		// iterate through the root Parameters Element and create paramGenerators
		for (Object curParametersObject: parametersElement.getChildren("Parameters", ns)) {
			generators.add(ParameterGeneratorFactory.createParameterGenerator((Element) curParametersObject));
		}
	}
	
	/*
	 * create Cartesian Product given ArrayList @paramList containing ArrayList of Elements
	 * 
	 * e.g. (p1,p2), (p1',p2') --> (p1,p1'), (p1,p2'), (p2,p1'), (p2,p2')
	 * */
	private static ArrayList<ArrayList<TestCaseParameter>> cartesianProduct(ArrayList<ArrayList<TestCaseParameter>> lists) {
	    if (lists.size() < 2)
	        throw new IllegalArgumentException(
	                "Can't have a product of fewer than two sets (got " +
	                lists.size() + ")");

	    return _cartesianProduct(0, lists);
	}
	
	private static ArrayList<ArrayList<TestCaseParameter>> _cartesianProduct(int index, ArrayList<ArrayList<TestCaseParameter>> lists) {
		// create result Array
		ArrayList<ArrayList<TestCaseParameter>> res = new ArrayList<ArrayList<TestCaseParameter>>();
		if (index== lists.size()) {
			res.add(new ArrayList<TestCaseParameter>());
		} else {
			for (TestCaseParameter s: lists.get(index)) {
				for (ArrayList<TestCaseParameter> list: _cartesianProduct(index+1, lists)) {
					list.add(s);
					res.add(list);
				}
			}
		}
		return res;
	}


	@Override
	public ArrayList<TestCaseParameter> generateParameters(SampleConfig sampleConfig) throws ParameterGenerationFailedException {
		// add TestCaseParameters to paramList
		for (ParameterGenerator curGenerator: generators) {
			if (curGenerator.generateParameters(sampleConfig).size()>0)
				paramList.add((ArrayList<TestCaseParameter>) curGenerator.generateParameters(sampleConfig));	
		}
		// get cartesianProduct of TestCaseParameters (List containing List of cartesianTestCaseParameters)
		if (paramList.size()==1) {
			return generatedParameters;							
		} else if (paramList.size()>1) {
			resList = cartesianProduct(paramList);							
		} else {
			resList = paramList;
		}
		// merge TestCaseParameters if there is something to merge
		if(resList!=paramList) {
			for (ArrayList<TestCaseParameter> curList: resList) {
				TestCaseParameter tcp = curList.get(0).clone();
				for (TestCaseParameter currTcp: curList.subList(1, curList.size())) {
					tcp = tcp.merge(currTcp, profileParseType);
				}
				generatedParameters.add(tcp);
			}
		} else {
			for (ArrayList<TestCaseParameter> curList: resList) {
				for (TestCaseParameter currTcp: curList) {
					generatedParameters.add(currTcp);
				}
			}
		}
		return generatedParameters;
	}
	
	
}
