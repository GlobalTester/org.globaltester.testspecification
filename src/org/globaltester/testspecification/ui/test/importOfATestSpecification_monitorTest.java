package org.globaltester.testspecification.ui.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

public class importOfATestSpecification_monitorTest {

	public static String gtMainWindowTitle = "GlobalTester";
	private SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	@Test
	public void testImportOfATestSpecification_monitor(){
		
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		
	
		SWTBotView activeView = bot.activeView();
		
		//close the welcome page, if it is shown
		if ("Welcome".equals(activeView.getTitle())) {
			bot.viewByTitle("Welcome").close();
		}
		
		//get the GlobalTester View widget
		SWTBotView globalTesterNavigatorView = bot.viewByTitle("GlobalTester Navigator");
		bot.captureScreenshot("screenshots"+File.separator+"importTestSpec01.png");

		//get the tree view widget of the GT View
		SWTBotTree navigatorTree = globalTesterNavigatorView.bot().tree();
		
		//Select Import
		SWTBotMenu contextMenu = navigatorTree.contextMenu("Import...");
		contextMenu.click();

		
		SWTBotShell importDialogBot = bot.shell("Import");
		SWTBotTree importViewTree = importDialogBot.bot().tree();
		
		for (SWTBotTreeItem item : importViewTree.getAllItems()){
			if (item.getText().equals("GlobalTester")){
				item.expand();
				item.getItems()[0].select();
			}
		}

		bot.button("Next >").click();
		
		
		bot.button("Finish").click();

		bot.waitUntil(Conditions.shellIsActive("Import"));
		SWTBotShell progressMonitor = bot.shell("Progress Information");
		bot.shell("Import").bot().button("OK").click();
		assertNotNull(progressMonitor);
	}
	
}
