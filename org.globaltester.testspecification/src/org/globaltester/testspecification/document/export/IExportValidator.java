package org.globaltester.testspecification.document.export;

import java.util.HashMap;

public interface IExportValidator {
	/**
	 * This method validates the given export xsl parameter. The given HashMap can be used
	 * to check dependencies between different parameters.
	 * 
	 * @param key
	 * @param value
	 * @param params
	 * @return false if value is not valid, true otherwise
	 */
	public boolean validate(String key, Object value, HashMap<String, Object> params);
	
	/**
	 * This method should return a message, which will be displayed in the export wizard.
	 * 
	 * @return the message explaining the last not valid value
	 */
	public String getMessage();
	
	/**
	 * This method generates a valid value for the given key.
	 * 
	 * @param key
	 * @return the generated value.
	 */
	public Object generate(String key);
}
