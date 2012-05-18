package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
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
		String projectName = _pageOne.getProjectName();
		File target = _pageOne.getDestination();

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		try {
			// get stylesheet and Open Office sources
			InputStream stylesheetStream = _pageOne.getStylesheet();
			InputStream sourceZipStream = _pageOne.getSourcesZip();
			IFile testSpecIFile = project.getFile("testSpecification.xml");
			String pathToProject = project.getProject().getLocationURI().getPath();
			File testSpecification = new File(pathToProject + File.separator + testSpecIFile.getProjectRelativePath().toString());
			try {
				Exporter.export(target, testSpecification, stylesheetStream, sourceZipStream);
			} catch (CoreException e) {
				ErrorDialog.openError(getShell(), null, null, e.getStatus());
			}

			stylesheetStream.close();
			sourceZipStream.close();
		} catch (IOException e) {
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

}
