package org.globaltester.testspecification.ui.wizards;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
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

public class ImportTestSpecWizardPage extends WizardPage {

	private Text txtProjectName;
	private List lstBundleSelection;
	private Text txtDescription;
	private boolean defaultName;
	
	private IConfigurationElement[] configElements;

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
				//if name if modified do not update it with the default name when new source is selected
				defaultName = false;
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
		lstBundleSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
		lstBundleSelection.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				validateAndUpdate();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		Label lblDescr = new Label(container, SWT.NONE);
		lblDescr.setText("Description:");
		lblDescr.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		txtDescription = new Text(container, SWT.WRAP | SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setText("asdf");
		txtDescription.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		

		// Add the bundles to the list
		String importablesExtensionId = "org.globaltester.testspecification.importables";
		configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(importablesExtensionId);
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement curElem = configElements[i];

			String name = curElem.getAttribute("name");
			String bundle = curElem.getAttribute("bundle");

			String listEntry = "";
			if ((bundle != null) && (bundle.trim().length() > 0)) {
				listEntry = name + " (" + bundle + ")";
			} else {
				listEntry = bundle;
			}

			lstBundleSelection.add(listEntry);
		}

		// fill with default values
		if (lstBundleSelection.getItemCount() > 0) {
			defaultName = true;
			// select the first element
			lstBundleSelection.select(0);
			validateAndUpdate();
		} else {
			// no valid test specification plugins are present -> show error
			// message
			setErrorMessage("No Plugin defining an importable GT TestSpecification is installed.");
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

	public String getSelectedBundle() {
		int selectedEntry = lstBundleSelection.getSelectionIndex();
		return configElements[selectedEntry].getAttribute("bundle");		
	}

	private void validateAndUpdate() {
		int selectedEntry = lstBundleSelection.getSelectionIndex();
		//update the description
		txtDescription.setText(configElements[selectedEntry].getAttribute("descr"));
		
		if (defaultName) {
			//update the default name of the new project
			txtProjectName.setText(configElements[selectedEntry].getAttribute("name"));
			defaultName = true;
		}

		// update the dialog appearance
		getContainer().updateButtons();
		getContainer().updateMessage();
	}
}
