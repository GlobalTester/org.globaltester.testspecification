package org.globaltester.testspecification.ui.wizards;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.globaltester.core.resources.GtResourceHelper;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.GtTestSpecProject;
import org.globaltester.testspecification.ui.Activator;
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
		try {
			GtResourceHelper
					.copyPluginContent2WorkspaceProject(pluginName, project);
		} catch (IOException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
			Status st = new Status(IStatus.WARNING, Activator.PLUGIN_ID, e
					.getLocalizedMessage(), e);
			ErrorDialog.openError(getShell(), "Unable to copy Project to workspace", "The selected Project could not be imported into the workspace.", st);
		}

		// refresh the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot()
					.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh workspace failed
			// log CoreException to eclipse log
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
			
			// users most probably will ignore this behavior and refresh manually, so do not open annoying dialog
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
