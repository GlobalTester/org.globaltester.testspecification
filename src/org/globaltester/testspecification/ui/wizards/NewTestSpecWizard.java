package org.globaltester.testspecification.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.globaltester.testspecification.ui.Messages;

public class NewTestSpecWizard extends Wizard implements INewWizard {
	
	private WizardNewProjectCreationPage _pageOne;

	public NewTestSpecWizard() {
		setWindowTitle(Messages.NewTestSpecWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	@Override
	public void addPages() {
	    super.addPages();

	    _pageOne = new WizardNewProjectCreationPage(Messages.NewTestSpecWizard_PAGE_NAME);
	    _pageOne.setTitle(Messages.NewTestSpecWizard_GT_TestSpec_Project);
	    _pageOne.setDescription(Messages.NewTestSpecWizard_create_new_GT_TestSpec);

	    addPage(_pageOne);
	}


}
