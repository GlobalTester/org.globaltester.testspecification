package org.globaltester.testspecification.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.globaltester.testspecification.ui.messages"; //$NON-NLS-1$
	public static String NewTestSpecWizard_create_new_GT_TestSpec;
	public static String NewTestSpecWizard_GT_TestSpec_Project;
	public static String NewTestSpecWizard_PAGE_NAME;
	public static String NewTestSpecWizard_WIZARD_NAME;
	public static String ImportTestSpecWizard_import_new_GT_TestSpec;
	public static String ImportTestSpecWizard_GT_TestSpec_Project;
	public static String ImportTestSpecWizard_PAGE_NAME;
	public static String ImportTestSpecWizard_WIZARD_NAME;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
