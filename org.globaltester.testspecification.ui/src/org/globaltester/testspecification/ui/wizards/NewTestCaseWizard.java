package org.globaltester.testspecification.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.globaltester.core.ui.GtUiHelper;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.testframework.TestCase;
import org.globaltester.testspecification.ui.Activator;
import org.globaltester.testspecification.ui.Messages;
import org.globaltester.testspecification.ui.UiImages;

public class NewTestCaseWizard extends Wizard implements INewWizard {

	private WizardNewFileCreationPage _pageOne;
	private IStructuredSelection selection;
	
	public NewTestCaseWizard() {
		setWindowTitle(Messages.NewTestCaseWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
		setDefaultPageImageDescriptor(UiImages.TESTCASE_BANNER
				.getImageDescriptor());
	}

	@Override
	public boolean performFinish() {
	    // create File with default content
	    IFile iFile = _pageOne.createNewFile();
	    TestCase.createDefaultTestCase(iFile);
	    
	    // refresh the workspace
	 	try {
			ResourcesPlugin.getWorkspace().getRoot()
					.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh workspace failed
			// log CoreException to eclipse log
			GtErrorLogger.log(Activator.PLUGIN_ID, e);

			// users most probably will ignore this behavior and refresh
			// manually, so do not open annoying dialog
		}
	    
	    // open in Editor
	    GtUiHelper.openInEditor(iFile);
	    
	    return true;
	}

	
	@Override
	public void addPages() {
	    super.addPages();

		_pageOne = new WizardNewFileCreationPage(Messages.NewTestCaseWizard_PAGE_NAME, getSelection());
	    _pageOne.setTitle(Messages.NewTestCaseWizard_GT_TestCase_Project);
	    _pageOne.setDescription(Messages.NewTestCaseWizard_create_new_GT_TestCase);
	    _pageOne.setFileExtension("gt");
	    _pageOne.setFileName("TestCase.gt");

	    addPage(_pageOne);
	}

	private IStructuredSelection getSelection() {
		return selection;
	}

}
