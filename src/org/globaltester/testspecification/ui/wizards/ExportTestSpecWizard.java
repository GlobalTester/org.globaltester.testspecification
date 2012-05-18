package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.globaltester.core.Activator;
import org.globaltester.document.export.Exporter;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.ui.Messages;
import org.globaltester.testspecification.ui.UiImages;

public class ExportTestSpecWizard extends Wizard implements IExportWizard {


	private ExportTestSpecWizardPage _pageOne;

	String projectName;
	File target;
	InputStream stylesheetStream;
	InputStream sourceZipStream;
	
	public ExportTestSpecWizard() {
		setWindowTitle(Messages.ExportTestSpecWizard_WIZARD_NAME);
		setNeedsProgressMonitor(true);
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
			
			getContainer().run(true, false, new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {

						IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

						monitor.beginTask("Export to OpenDocument format.", 3);
						// get stylesheet and OpenDocument sources
						monitor.subTask("Preparation");
						IFile testSpecIFile = project.getFile("testSpecification.xml");
						String pathToProject = project.getProject().getLocationURI().getPath();
						File testSpecification = new File(pathToProject + File.separator + testSpecIFile.getProjectRelativePath().toString());
						monitor.worked(1);
						monitor.subTask("Export");
						Exporter.export(target, testSpecification, stylesheetStream, sourceZipStream);
						monitor.worked(2);
						
					} catch (IOException e) {
						GtErrorLogger.log(Activator.PLUGIN_ID, e);
						ErrorDialog.openError(null, null, null, new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Input/Output error"));
					} catch (CoreException e) {
						ErrorDialog.openError(null, null, null, new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Conversion error."));
					} finally {
						monitor.done();
					}
				}
			});
			
			stylesheetStream.close();
			sourceZipStream.close();
			ErrorDialog.openError(null, null, null, new Status(IStatus.OK, Activator.PLUGIN_ID, "Export successfull."));
		} catch (IOException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
			ErrorDialog.openError(null, null, null, new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Input/Output Error"));
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

		_pageOne = new ExportTestSpecWizardPage();
		addPage(_pageOne);
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
}
