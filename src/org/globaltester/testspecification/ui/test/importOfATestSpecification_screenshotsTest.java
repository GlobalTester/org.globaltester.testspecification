package org.globaltester.testspecification.ui.test;

import org.eclipse.core.runtime.CoreException;
import org.globaltester.swtbot.uihelper.GlobalTesterUiHelper;
import org.globaltester.swtbot.uihelper.ImportWizardUiHelper;
import org.globaltester.swtbot.uihelper.NavigatorViewUiHelper;
import org.junit.Test;

public class importOfATestSpecification_screenshotsTest {

	
	@Test
	public void testImportOfATestSpecification_screenshots() throws CoreException{
		
		GlobalTesterUiHelper.init();
		NavigatorViewUiHelper navigator = GlobalTesterUiHelper.focusNavigatorView();
		navigator.captureScreenshot("importTestSpec01.png");
		ImportWizardUiHelper importWizard = navigator.openImportWizardByNavigatorContextMenu();
		importWizard.captureScreenshot("importTestSpec02.png");

	}
	
}
