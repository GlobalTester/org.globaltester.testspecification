package org.globaltester.testspecification.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewTestSpecWizard extends Wizard implements INewWizard {
	
	private WizardNewProjectCreationPage _pageOne;

	public NewTestSpecWizard() {
		setWindowTitle("GlobalTester TestSpecification Wizard");
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

	    _pageOne = new WizardNewProjectCreationPage("GlobalTester TestSpecification Project Wizard");
	    _pageOne.setTitle("GlobalTester TestSpecification Project");
	    _pageOne.setDescription("Create a new GlobalTester TestSpecification.");

	    addPage(_pageOne);
	}


}
