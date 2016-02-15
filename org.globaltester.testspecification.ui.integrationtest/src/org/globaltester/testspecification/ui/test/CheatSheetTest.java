package org.globaltester.testspecification.ui.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.globaltester.swtbot.uihelper.GlobalTesterUiHelper;
import org.globaltester.swtbot.uihelper.TestCaseWizardUiHelper;
import org.globaltester.swtbot.uihelper.TestSpecificationExportWizardUiHelper;
import org.globaltester.swtbot.uihelper.TestSpecificationImportWizardUiHelper;
import org.globaltester.swtbot.uihelper.TestSpecificationWizardUiHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
/**
 * Test the workflows that are given as eclipse cheat cheet.
 * 
 * @author mboonk
 *
 */
public class CheatSheetTest {

	private String projectName = "ProjectForTesting";
	private String sampleProject = "GlobalTester Sample TestSpecification";
	
	@Before
	public void prepare() throws CoreException{
		GlobalTesterUiHelper.init();
	}
	
	@Test
	@Ignore
	public void createAndExportTestSpecification() throws IOException {
		TestSpecificationWizardUiHelper testSpecWizard = GlobalTesterUiHelper.openNewWizardByMenu().selectTestSpecification();
		testSpecWizard.setProjectName(projectName);
		testSpecWizard.finish();
		
		TestCaseWizardUiHelper testCaseWizard = GlobalTesterUiHelper.openNewWizardByMenu().selectTestCase();
		testCaseWizard.selectFolder(projectName);
		testCaseWizard.finish();
		
		TestSpecificationExportWizardUiHelper exportWizard = GlobalTesterUiHelper.openExportWizardByMenu().openTestSpecificationExportWizard();
		File tempFile = File.createTempFile("export", "pdf");
		exportWizard.selectTestSpecification(projectName);
		exportWizard.setExportDestination(tempFile);
		exportWizard.finish();
		// check temporary folder for result
		assertTrue("No testspecification file exported", tempFile.length() > 0);
		tempFile.delete();
	}
	
	@Test
	public void importTestSpecification(){
		TestSpecificationImportWizardUiHelper importWizard = GlobalTesterUiHelper.openImportWizardByMenu().openTestSpecificationImportWizard();
		importWizard.selectProject(sampleProject);
		importWizard.setProjectName(sampleProject);
		importWizard.finish();
	}
	
}
