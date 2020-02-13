package org.globaltester.testspecification.testframework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Attribute;

public class TestCaseParameter {

	public static final TestCaseParameter UNPARAMETERIZED = new TestCaseParameter("Unparameterized");

	private String id;

	private HashMap<String, Object> values = new HashMap<>();
	
	public TestCaseParameter(String id) {
		this.id = id;
	}

	public String getIdSuffix() {
		return id;
	}

	public void appendIdSuffix(String additionalSuffix) {
		id = id + additionalSuffix;
	}
	
	public Object get(String key) {
		return values.get(key);
	}
	
	public boolean contains(String key) {
		return values.containsKey(key);
	}
	
	public void put(String key, Object value) {
		values.put(key, value);
	}
	
	public TestCaseParameter merge(TestCaseParameter param, Attribute profileParseType) {
		TestCaseParameter tcp = this.clone();
		boolean listFlag = (profileParseType.getValue().equals("list"));
		ArrayList<String> initList = new ArrayList<>();
		for (String key: param.values.keySet()) {
			// if key is profile, check if should treat as List
			System.out.println("Type of " + key + "..."+ key.getClass().toString() + ", listFlag="+ listFlag);
			if (key.equals("profile") && listFlag) {
				if (tcp.contains(key)) { 
					// is existing value a String? -> create new List with appended values
					if (tcp.get(key) instanceof String) {
						String tmpString = (String) tcp.get(key);
						initList.add(tmpString); initList.add((String) param.get(key));
						tcp.put(key, initList);
					} // is existing value of type List? -> append paramValue to list 
					else if (tcp.get(key) instanceof ArrayList<?>) {
						ArrayList<String> currList = (ArrayList<String>) tcp.get(key);
						currList.add((String) param.get(key));
					}
				} // no key "profile" contained -> set new entry 
				else {
					initList.add((String) param.get(key));
					tcp.put(key, initList);
				}
				continue;
			} else if (key.equals("idSuffix")) {
				tcp.appendIdSuffix("_"+param.getIdSuffix());
				tcp.put(key, tcp.getIdSuffix());
				continue;
			}
			// add key,value to map as usual
			tcp.put(key, param.get(key));
		}
		return tcp;
	}

	@Override
	public String toString() {
		return "TestCaseParameter [id=" + id + ", values=" + values + "]";
	}
	
	@Override
	public TestCaseParameter clone() {
		TestCaseParameter retVal = new TestCaseParameter(id);
		for (String curKey : values.keySet()) {
			retVal.put(curKey, get(curKey));
		}
		return retVal ;
	}

}
