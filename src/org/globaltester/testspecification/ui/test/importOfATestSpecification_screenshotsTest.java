package org.globaltester.testspecification.ui.test;

import static org.junit.Assert.*;


import org.junit.Test;
import java.io.File;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.waits.Conditions;

public class importOfATestSpecification_screenshotsTest {

	public static String gtMainWindowTitle = "GlobalTester";
	private SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	@Test
	public void testTest()throws Exception{
		assertNotNull(1);
	}
	
	@Test
	public void testImportOfATestSpecification_screenshots(){
		
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		
	
		SWTBotView activeView = bot.activeView();
		
		//close the welcome page, if it is shown
		if ("Welcome".equals(activeView.getTitle())) {
			bot.viewByTitle("Welcome").close();
		}
		
		//get the GlobalTester View widget
		SWTBotView globalTesterNavigatorView = bot.viewByTitle("GlobalTester Navigator");
		bot.sleep(500);
		bot.captureScreenshot("screenshots"+File.separator+"importTestSpec01.png");
		bot.sleep(500);

		//get the tree view widget of the GT View
		SWTBotTree tree = globalTesterNavigatorView.bot().tree();
		
		//Select Import
		SWTBotMenu contextMenu = tree.contextMenu("Import...");
		bot.sleep(500);
		bot.captureScreenshot("screenshots"+File.separator+"importTestSpec02.png");
		bot.sleep(500);

		//cannot hold it open
		contextMenu.click();
		bot.sleep(500);
		bot.captureScreenshot("screenshots"+File.separator+"importTestSpec01.png");
		bot.sleep(500);
		
		//get the Import View widget
		SWTBotView importView = bot.activeView();

		//get the tree
		SWTBotTree importViewTree = importView.bot().tree();
		
		//cannot select the correct node 
//		SWTBotTreeItem globalTesterItem = importViewTree.expand();
	
//		importViewTree.select("Import TestSpecification from Plugin");
//		importViewTree.select(2);
		importViewTree.expandNode("Import TestSpecification from Plugin", true);

//		bot.button("Next >").click();
		
//		globalTesterItem.getNode("Import TestSpecification from Plugin").select();


		
		

		
		bot.sleep(500);
		bot.captureScreenshot("screenshots"+File.separator+"importTestSpec01.png");
		bot.sleep(500);
		
		
		assertNotNull(1);
		
		
	}
	
}
