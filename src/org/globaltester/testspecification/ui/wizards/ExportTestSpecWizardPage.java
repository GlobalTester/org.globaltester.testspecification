package org.globaltester.testspecification.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.globaltester.testspecification.GtTestSpecNature;

public class ExportTestSpecWizardPage extends WizardPage {
	private List lstTestSpecSelection;
	private Text txtDestination;
	private boolean isDefaultDestination;

	protected ExportTestSpecWizardPage() {
		super("");
		setTitle("");
		setMessage("");
	}

	@Override
	public void createControl(Composite parent) {
		// create main composite for this page
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(3, false));
		new Label(container, SWT.NONE);

		// Create the TestSpec project selection list
		Label lblSpecs = new Label(container, SWT.NONE);
		lblSpecs.setText("Select TestSpecification project to export:");
		lblSpecs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		new Label(container, SWT.NONE);
		lstTestSpecSelection = new List(container, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		lstTestSpecSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
		new Label(container, SWT.NONE);
		lstTestSpecSelection.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				validateAndUpdate();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		Label lblDest = new Label(container, SWT.NONE);
		lblDest.setText("Export destination:");
		lblDest.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		new Label(container, SWT.NONE);

		txtDestination = new Text(container, SWT.BORDER);
		txtDestination.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtDestination.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isDefaultDestination = false;
				validateAndUpdate();
			}
		});
		
		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.setText("Browse");
		btnBrowse.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(txtDestination.getShell(),
						SWT.SAVE);
				dialog.setFilterNames(new String[] { "OpenDocumentText",
						"All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.odt", "*" });
				dialog.setFileName(getProjectName() + ".odt");
				String selectedFileName = dialog.open();
				if (selectedFileName == null) {
					selectedFileName = "";
				}
				txtDestination.setText(selectedFileName);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				txtDestination.setText("");
			}
		});

		// Add all GT TestSPec projects to the list
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject curProject : projects) {

			try {
				if (curProject.hasNature(GtTestSpecNature.NATURE_ID))
					lstTestSpecSelection.add(curProject.getName());
			} catch (CoreException e1) {
				// ignore core exception, just do not display the problematic
				// project in the list
			}
		}

		// fill with default values
		if (lstTestSpecSelection.getItemCount() > 0) {
			// select the first element
			lstTestSpecSelection.select(0);
			setDefaultDestination();
			validateAndUpdate();
		} else {
			// no valid test specification plugins are present -> show error
			// message
			setErrorMessage("Currently no GT TestSpecification projects are available in the workspace.");
		}

	}

	private void setDefaultDestination() {
		txtDestination.setText(System.getProperty("user.dir")+getProjectName() + ".odt");
		isDefaultDestination = true;
	}

	@Override
	public boolean isPageComplete() {
		String fileName = txtDestination.getText();
		if (fileName.length() == 0) {
			setErrorMessage("No export destination is given.");
			return false;
		}

		if (lstTestSpecSelection.getSelectionCount() != 1) {
			setErrorMessage("Please select a single TestSpec to export");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	public String getDestination() {
		return txtDestination.getText();
	}

	public String getProjectName() {
		return lstTestSpecSelection.getSelection()[0];
	}

	private void validateAndUpdate() {
		if (isDefaultDestination) {
			setDefaultDestination();
		}
		
		// update the dialog appearance
		getContainer().updateButtons();
		getContainer().updateMessage();
	}

}
