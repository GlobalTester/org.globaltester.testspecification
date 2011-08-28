package org.globaltester.testspecification.ui.wizards;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.globaltester.testspecification.ui.Activator;
import org.osgi.framework.Bundle;

public class ImportTestSpecWizardPage extends WizardPage {

	private Text txtProjectName;
	private List lstBundleSelection;

	protected ImportTestSpecWizardPage() {
		super("Some wizard Page");
		setTitle("WizardPage Title");
		setMessage("WizardPage Message");
	}

	@Override
	public void createControl(Composite parent) {
		// create main composite for this page
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		// add further controls to the container
		Label lblName = new Label(container, SWT.NONE);
		lblName.setText("Projectname:");
		txtProjectName = new Text(container, SWT.BORDER);
		txtProjectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtProjectName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateAndUpdate();
			}
		});

		// Create the bundle-selection list
		Label lblSpecs = new Label(container, SWT.NONE);
		lblSpecs.setText("Select TestSpecification project to import:");
		lblSpecs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		lstBundleSelection = new List(container, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		GridData gd_lstBundleSelection = new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1);
		lstBundleSelection.setLayoutData(gd_lstBundleSelection);
		lstBundleSelection.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO following line for debugging only, remove it
				txtProjectName.setText(lstBundleSelection.getItem(lstBundleSelection.getSelectionIndex()));
				
				validateAndUpdate();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Add the bundles to the list
		Bundle[] bundles = Activator.getDefault().getBundle()
				.getBundleContext().getBundles();
		for (int i = 0, n = bundles.length; i < n; i++) {
			String bundleName = bundles[i].getSymbolicName();
			// TODO check if bundle includes TestSpecProject
			if (bundleName.startsWith("org.globaltester.")) {
				lstBundleSelection.add(bundleName);
			}
		}

		// fill with default values
		if (lstBundleSelection.getItemCount() > 0) {
			// select the first element
			lstBundleSelection.select(0);
			txtProjectName.setText(lstBundleSelection.getItem(0));
		} else {
			// no valid test specification plugins are present -> show error
			// message
			setErrorMessage("No Plugin defining a GT TestSpecification is installed.");
		}

	}

	@Override
	public boolean isPageComplete() {
		// check whether project name is given
		String projectName = txtProjectName.getText();
		if ((projectName.length() == 0)) {
			setErrorMessage("No Projectname is given.");
			return false;
		}

		// check if the project name is unique
		if (ResourcesPlugin.getWorkspace().getRoot().getProject(projectName)
				.exists()) {
			setErrorMessage("Project with given name already exists.");
			return false;
		}

		// check whether project name is given
		if ((lstBundleSelection.getSelectionCount() != 1)) {
			setErrorMessage("No source Specification is selected.");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	public String getProjectName() {
		return txtProjectName.getText();
	}

	public String getSelectedPlugin() {
		String[] selection = lstBundleSelection.getSelection(); 
		if ((selection != null)&&(selection.length > 0)) {
			return selection[0];
		} else {
		 return null;
		}
	}

	private void validateAndUpdate() {
		// TODO check user input and generate according messages here

		// update the dialog appearance
		getContainer().updateButtons();
		getContainer().updateMessage();
	}
}
