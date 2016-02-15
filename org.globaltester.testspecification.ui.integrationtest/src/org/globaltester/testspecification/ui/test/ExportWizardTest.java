package org.globaltester.testspecification.ui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.globaltester.swtbot.uihelper.GlobalTesterUiHelper;
import org.globaltester.swtbot.uihelper.TestSpecificationExportWizardUiHelper;
import org.junit.Before;
import org.junit.Test;

public class ExportWizardTest {

	@Before
	public void prepare() throws IOException, CoreException{
		GlobalTesterUiHelper.init();
	}
	
	@Test
	public void testExportWizardPO() throws CoreException{
		TestSpecificationExportWizardUiHelper exportWizard = GlobalTesterUiHelper.openExportWizardByMenu().openTestSpecificationExportWizard();
		assertEquals("while testing there should be no test specification projects", exportWizard.getNumberOfTestSpecifications(), 0);
		assertTrue("at least one entry should be in the exporters list", exportWizard.getNumberOfExporters() >= 1);
		assertTrue("Custom exporter should be in the exporters list", exportWizard.hasExporter("Custom"));
		assertTrue("before selecting custom export, additional fields should not be visible", !exportWizard.visibilityOfCustomOptions());
		exportWizard.selectExportLayout("Custom");
		assertTrue("by selecting custom export, additional fields should become visible", exportWizard.visibilityOfCustomOptions());
	}
}
