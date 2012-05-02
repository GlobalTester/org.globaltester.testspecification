package org.globaltester.testspecification.ui.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class ExportWizardTest {
	public static String gtMainWindowTitle = "GlobalTester";

	private SWTWorkbenchBot bot = new SWTWorkbenchBot();
	@Test
	public void testExportWizard() {
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		bot.menu("File").menu("Export...").click();

		String wizardTitle = "Export";
		bot.waitUntil(Conditions.shellIsActive(wizardTitle));

		SWTBotShell exportWizardShell = bot.shell(wizardTitle);
		exportWizardShell.activate();
		exportWizardShell.setFocus();
		assertNotNull(exportWizardShell);
		bot.waitUntil(Conditions.shellIsActive(wizardTitle), 5000, 500);
		exportWizardShell.bot().tree().getTreeItem("GlobalTester").expand()
				.select("Export TestSpecification to OpenDocumentFormat");
		bot.button("Next >").click();
		bot.sleep(500);

		SWTBotList projectList = bot.list(0);
		assertEquals(
				"while testing there should be no test specification projects",
				projectList.itemCount(), 0);

		SWTBotList exporterList = bot.list(1);
		assertTrue("at least one entry should be in the exporters list",
				exporterList.itemCount() >= 1);
		String[] items = exporterList.getItems();
		assertEquals("Custom", items[items.length - 1]);

		// select custom to show custom input fields
		exporterList.select("Custom");

		SWTBotLabel stylesheetLabel = bot.label("Stylesheet:");
		
		// check change of visibility
		assertTrue(
				"by selecting custom export, additional fields should become visible", stylesheetLabel.isVisible());

		bot.button("Cancel").click();
	}
}
