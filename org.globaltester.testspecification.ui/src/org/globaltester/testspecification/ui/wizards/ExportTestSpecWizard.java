package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.globaltester.core.Activator;
import org.globaltester.document.export.Exporter;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.ui.Messages;
import org.globaltester.testspecification.ui.UiImages;

public class ExportTestSpecWizard extends Wizard implements IExportWizard {


	private ExportTestSpecWizardPage _pageOne;
	private ExportTestSpecWizardCustomizationPage _pageTwo;

	String projectName;
	File target;
	InputStream stylesheetStream;
	InputStream sourceZipStream;
	HashMap<String, Object> xslParams;
	
	public ExportTestSpecWizard() {
		setWindowTitle(Messages.ExportTestSpecWizard_WIZARD_NAME);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UiImages.TESTSPEC_BANNER
				.getImageDescriptor());
	}

	@Override
	public boolean performFinish() {
		projectName = _pageOne.getProjectName();
		target = _pageOne.getDestination();
		try {
			stylesheetStream = _pageOne.getStylesheet();
			sourceZipStream = _pageOne.getSourcesZip();
			
			IRunnableWithProgress exportRunnable = new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {

						IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

						monitor.beginTask("Export to OpenDocument format.", 3);
						// get stylesheet and OpenDocument sources
						monitor.subTask("Preparation");
						IFile testSpecIFile = project.getFile("testSpecification.gtspec");
						String pathToProject = project.getProject().getLocationURI().getPath();
						File testSpecification = new File(pathToProject + File.separator + testSpecIFile.getProjectRelativePath().toString());
						monitor.worked(1);
						monitor.subTask("Export");
						
						PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								_pageTwo.update();
							}
						});
						
						HashMap<String,Object> params = _pageTwo.getXslParams();

						Exporter.export(target, testSpecification, stylesheetStream, sourceZipStream, params);

						// save modified String values as preferences
						for (Entry<String, Object> entry : params.entrySet()){
							if (entry.getValue() instanceof String){
								PlatformUI.getPreferenceStore().putValue(ExportTestSpecWizardCustomizationPage.PREFERENCE_PREFIX + "." + entry.getKey(), (String) entry.getValue());
							}
						}
						monitor.worked(2);
						showSuccess();
					} catch (IOException e) {
						showIoError();
						GtErrorLogger.log(Activator.PLUGIN_ID, e);
					} catch (CoreException e) {
						showConversionError();
						GtErrorLogger.log(Activator.PLUGIN_ID, e);
					} finally {
						monitor.done();
					}
				}


			};
			

			ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
			pmd.setOpenOnRun(true);
			pmd.setBlockOnOpen(false);
			pmd.run(true, true, exportRunnable);
			
			
			stylesheetStream.close();
			sourceZipStream.close();
		} catch (IOException e) {
			showIoError();
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		} catch (InvocationTargetException e) {
			showError();
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		} catch (InterruptedException e) {
			showError();
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		}	

		return true;
	}

	private void showConversionError() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
			MessageDialog.openInformation(getShell(), "Export", "Conversion error.");
			}}); 
	}
	
	private void showSuccess(){
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
			MessageDialog.openInformation(getShell(), "Export", "Testspecification \"" + projectName + "\" exported successfully.");
			}}); 
	}
	
	private void showIoError(){
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
			MessageDialog.openError(getShell(), "Export", "Input/Output error.");
			}}); 
	}
	
	private void showError(){
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
			MessageDialog.openError(getShell(), "Export", "Unexpected error.");
			}}); 
	}
	
	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new ExportTestSpecWizardPage();
		addPage(_pageOne);
		_pageTwo = new ExportTestSpecWizardCustomizationPage();
		addPage(_pageTwo);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == _pageOne){
			_pageTwo.update();
		}
		return super.getNextPage(page);
	}
	
	
	
}
