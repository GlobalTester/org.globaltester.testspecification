package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
		File source = new File(project.getLocationURI().getPath().toString() + File.separator + "testSpecification.xml");
		File target = new File(targetFile);
		try {
			OOExporter.export(target, source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new ExportTestSpecWizardPage();
		addPage(_pageOne);
	}

}
