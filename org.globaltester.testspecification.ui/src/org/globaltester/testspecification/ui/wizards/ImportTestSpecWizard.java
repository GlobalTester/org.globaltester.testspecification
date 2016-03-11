package org.globaltester.testspecification.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.GtTestSpecProject;
import org.globaltester.testspecification.ui.Activator;
import org.globaltester.testspecification.ui.Messages;
import org.globaltester.testspecification.ui.UiImages;

public class ImportTestSpecWizard extends Wizard implements IImportWizard {

	private ImportTestSpecWizardPage _pageOne;

	private String name;
	private String pluginName;
	
	public ImportTestSpecWizard() {
		setWindowTitle(Messages.ImportTestSpecWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UiImages.TESTSPEC_BANNER
				.getImageDescriptor());
	}

	@Override
	public boolean performFinish() {
		name = _pageOne.getProjectName();
		pluginName = _pageOne.getSelectedBundle();

		IRunnableWithProgress importRunnable = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// TODO Auto-generated method stub
				monitor.beginTask("Copy plugin content.", 4);
				
				monitor.subTask("Create project");
				IProject project = GtTestSpecProject.createProject(name, null);
				monitor.worked(2);
				monitor.subTask("Copying");
				
				boolean errors = false;
				
				try {
					GtResourceHelper.copyPluginFilesToWorkspaceProject(pluginName, project, "/", "TestCases", "testSpecification.gtspec");
					monitor.worked(1);
				} catch (IOException e) {
					errors = true;
					GtErrorLogger.log(Activator.PLUGIN_ID, e);
					Status st = new Status(IStatus.WARNING, Activator.PLUGIN_ID, e
							.getLocalizedMessage(), e);
					ErrorDialog.openError(getShell(), "Unable to copy Project to workspace", "The selected Project could not be imported into the workspace.", st);
				}
				
				monitor.subTask("Workspace refresh");
				// refresh the workspace
				try {
					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
					monitor.worked(1);
				} catch (CoreException e) {
					// refresh workspace failed
					// log CoreException to eclipse log
					GtErrorLogger.log(Activator.PLUGIN_ID, e);
					
					// users most probably will ignore this behavior and refresh manually, so do not open annoying dialog
				}
				monitor.done();
				if (! errors){
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
					MessageDialog.openInformation(getShell(), "Import", "Testspecification imported successfully as  \"" + name + "\".");
					}}); 
				}
				
			}
		
		};

		ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
		pmd.setOpenOnRun(true);
		pmd.setBlockOnOpen(false);
		
		try {
			pmd.run(true, true, importRunnable);
		} catch (InvocationTargetException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		} catch (InterruptedException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
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
