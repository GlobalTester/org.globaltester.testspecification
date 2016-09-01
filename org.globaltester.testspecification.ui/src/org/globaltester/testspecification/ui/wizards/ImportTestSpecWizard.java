package org.globaltester.testspecification.ui.wizards;

import static org.globaltester.testspecification.GtTestSpecConstants.BUNDLE_NAME;
import static org.globaltester.testspecification.GtTestSpecConstants.BUNDLE_SYMBOLIC_NAME;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.logging.legacy.logger.GtErrorLogger;
import org.globaltester.testspecification.GtTestSpecProject;
import org.globaltester.testspecification.ui.Activator;
import org.globaltester.testspecification.ui.Messages;
import org.globaltester.testspecification.ui.UiImages;
import org.osgi.framework.Bundle;

public class ImportTestSpecWizard extends Wizard implements IImportWizard {

	private ImportTestSpecWizardPage _pageOne;
	
	private List<IConfigurationElement> selectedBundles;
	
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
		selectedBundles = _pageOne.getSelectedElements();
		
		if(selectedBundles.size() > 0) {
			IRunnableWithProgress importRunnable = new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Importing testscript projects.", selectedBundles.size()*10);
					
					boolean errors = false;
					
					for(IConfigurationElement currentlySelectedBundle : selectedBundles) {
						String currentBundleName = currentlySelectedBundle.getAttribute(BUNDLE_NAME);
						String currentBundleSymbolicName = currentlySelectedBundle.getAttribute(BUNDLE_SYMBOLIC_NAME);
						
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IProject conflictingProject = workspace.getRoot().getProject(currentBundleName);
						
						if(conflictingProject.exists()) {
							
							try {
								conflictingProject.delete(true, true, null);
								workspace.getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
							} catch (CoreException e) {
								e.printStackTrace();
							}
							
						}
						
						monitor.subTask("Importing project " + currentBundleName + ".");
						
						monitor.subTask("Creating project " + currentBundleName + ".");
						IProject project = GtTestSpecProject.createProject(currentBundleName, null);
						monitor.worked(1);
						
						monitor.subTask("Copying content of bundle " + currentBundleSymbolicName + ".");
						
						Bundle curBundle = Platform.getBundle(currentBundleSymbolicName);
						Enumeration<URL> allFiles = curBundle.findEntries("/", "*", false);
						ArrayList<String> files = new ArrayList<>();
						while(allFiles.hasMoreElements()) {
							files.add(allFiles.nextElement().getFile());
						}
						String[] filesStrings = files.toArray(new String[1]);
						
						try {
							GtResourceHelper.copyPluginFilesToWorkspaceProject(currentBundleSymbolicName, project, "/", filesStrings);
						} catch (IOException e) {
							errors = true;
							GtErrorLogger.log(Activator.PLUGIN_ID, e);
							Status st = new Status(IStatus.WARNING, Activator.PLUGIN_ID, e.getLocalizedMessage(), e);
							ErrorDialog.openError(getShell(), "Unable to copy project " + currentBundleName + " to workspace.", "The selected project " + currentBundleName + " could not be imported into the workspace.", st);
						} finally {
							monitor.worked(8);
						}
						
						monitor.subTask("Refreshing workspace.");
						// refresh the workspace
						try {
							ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
						} catch (CoreException e) {
							// refresh workspace failed
							// log CoreException to eclipse log
							GtErrorLogger.log(Activator.PLUGIN_ID, e);
							
							// users most probably will ignore this behavior and refresh manually, so do not open annoying dialog
						} finally{
							monitor.worked(1);
						}
						
					}
					
					if (!errors){
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						public void run() {
						MessageDialog.openInformation(getShell(), "Import", "Testspecification(s) imported successfully.");
						}}); 
					}
					
					monitor.done();
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
		} else {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openWarning(null, "Warning",
							"No TestSpecification project was selected. Please select at least one project or click Cancel to leave the wizard page.");
				}
			});
			return false;
		}
		
		
	}

	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new ImportTestSpecWizardPage();
		addPage(_pageOne);
	}
	
	@Override
	public boolean canFinish() {
		return _pageOne.canFinish();
	}

}
