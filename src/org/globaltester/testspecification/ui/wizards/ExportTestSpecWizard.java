package org.globaltester.testspecification.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.globaltester.testspecification.ui.Messages;

public class ExportTestSpecWizard extends Wizard implements IExportWizard {

	private ExportTestSpecWizardPage _pageOne;

	public ExportTestSpecWizard() {
		setWindowTitle(Messages.ExportTestSpecWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// no initialization needed
	}

	@Override
	public boolean performFinish() {
		String projectName = _pageOne.getProjectName();
		String targetFile = _pageOne.getDestination();

		//FIXME add the code to export the selected TestSpec here
		
		
		return true;
	}

	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new ExportTestSpecWizardPage();
		addPage(_pageOne);
	}

}
