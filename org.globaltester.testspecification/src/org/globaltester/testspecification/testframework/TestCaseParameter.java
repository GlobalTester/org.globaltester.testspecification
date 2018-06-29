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
	
	public Object get(String key) {
		return values.get(key);
	}
	
	public void put(String key, Object value) {
		values.put(key, value);
	}
	

}
