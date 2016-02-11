package org.globaltester.testspecification.export.hjp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.globaltester.document.export.IExportValidator;

/**
 * @author Martin Boonk
 *
 * Validates xsl parameters for the GlobalTester export layout.
 *
 */
public class Validator implements IExportValidator {

	private Pattern phoneNumber = Pattern.compile("[\\s\\+\\-\\d]*");
	private Pattern webAddress = Pattern.compile("(http://|www\\.).*\\..+");
	private Pattern emailAddress = Pattern.compile("^[_a-z0-9!#$%&\\\\'*+-\\/=?^_`.{|}~]+(\\.[_a-z0-9!#$%&\'*+-\\\\/=?^_`.{|}~]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,6})");
	private String message;


	@Override
	public boolean validate(String key, Object value,
			HashMap<String, Object> params) {
		message = null;
		return check(key,value);
	}
	
	private boolean check(String key, Object value) {
		if (value instanceof String) {
			if (key.equals("company.tel")) {
				Matcher m = phoneNumber.matcher((String) value);
				if (!m.matches()) {
					message = "The telephone number is not valid.";
					return false;
				}
			} else if (key.equals("company.www")) {
				Matcher m = webAddress.matcher((String) value);
				if (!m.matches()) {
					message = "The internet address is not valid";
					return false;
				}
			} else if (key.equals("company.mail")) {
				Matcher m = emailAddress.matcher((String) value);
				if (!m.matches()) {
					message = "The eMail address is not valid";
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Object generate(String key) {
		if (key.equals("year")){
			return Calendar.getInstance().get(Calendar.YEAR);
		}
		return null;
	}
}
