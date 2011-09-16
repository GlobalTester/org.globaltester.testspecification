package org.globaltester.testspecification.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.globaltester.core.resources.GtResourceHelper;
import org.globaltester.testspecification.GtTestSpecProject;
import org.globaltester.testspecification.ui.Messages;

public class ImportTestSpecWizard extends Wizard implements IImportWizard {

	private ImportTestSpecWizardPage _pageOne;

	public ImportTestSpecWizard() {
		setWindowTitle(Messages.ImportTestSpecWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// no initialization needed
	}

	@Override
	public boolean performFinish() {
		String name = _pageOne.getProjectName();
		String pluginName = _pageOne.getSelectedPlugin();

		IProject project = GtTestSpecProject.createProject(name, null);
		GtResourceHelper
				.copyPluginContent2WorkspaceProject(pluginName, project);

		// refresh the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot()
					.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh of workspace failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually
		}

		return true;
	}

	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new ImportTestSpecWizardPage();
		addPage(_pageOne);
	}

}
