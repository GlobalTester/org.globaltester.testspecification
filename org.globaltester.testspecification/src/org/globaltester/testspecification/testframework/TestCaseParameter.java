package org.globaltester.testspecification.testframework;

import java.util.HashMap;

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
