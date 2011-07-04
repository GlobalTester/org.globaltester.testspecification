package org.globaltester.testspecification.ui.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;

public class GTRuleBasedPartitionScanner extends RuleBasedPartitionScanner {
	private ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();

	public String[] getLegalContentTypes() {
		IPredicateRule[] ruleArray = rules.toArray(new IPredicateRule[]{});
		String[] contentTypes = new String[ruleArray.length];
		
		for (int i = 0; i < ruleArray.length; i++) {
			Object o = ruleArray[i].getSuccessToken().getData();
			if (o instanceof String) {
				contentTypes[i] = (String) o;	
			} else {
				contentTypes[i] = null;
			}
			
		}
		
		return contentTypes;
	}

}
