package org.globaltester.testspecification.ui.test;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.globaltester.swtbot.Strings;
import org.globaltester.swtbot.SwtBotHelper;
import org.globaltester.swtbot.uihelper.GlobalTesterUiHelper;
import org.globaltester.swtbot.uihelper.ImportWizardUiHelper;
import org.globaltester.swtbot.uihelper.NavigatorViewUiHelper;
import org.globaltester.swtbot.uihelper.TestSpecificationImportWizardUiHelper;
import org.junit.Test;

public class importOfATestSpecification_screenshotsTest {

	
	@Test
	public void testImportOfATestSpecification_screenshots() throws CoreException{
		
		GlobalTesterUiHelper.init();
		GlobalTesterUiHelper.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "importTestSpec00.png");
		NavigatorViewUiHelper navigator = GlobalTesterUiHelper.focusNavigatorView();
		navigator.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "importTestSpec01.png");
		ImportWizardUiHelper importWizard = navigator.openImportWizardByNavigatorContextMenu();
		SwtBotHelper.selectInTree(importWizard.getBot().tree(), Strings.WIZARD_CATEGORY_GLOBALTESTER, Strings.WIZARD_ITEM_IMPORT);
		importWizard.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "importTestSpec02.png");
		TestSpecificationImportWizardUiHelper testSpecImportWizard = importWizard.openTestSpecificationImportWizard();
		testSpecImportWizard.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "importTestSpec03.png");
		testSpecImportWizard.selectProject(Strings.SAMPLE_TESTSPEC);
		testSpecImportWizard.finish();
		navigator = GlobalTesterUiHelper.focusNavigatorView();
		navigator.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "importTestSpec04.png");
	}
	
}
