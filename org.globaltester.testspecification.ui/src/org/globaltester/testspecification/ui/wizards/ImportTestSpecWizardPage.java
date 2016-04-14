package org.globaltester.testspecification.ui.wizards;

import java.util.ArrayList;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.globaltester.testspecification.ui.Messages;

public class ImportTestSpecWizardPage extends WizardPage {
	
	public static String BUNDLE_SYMBOLIC_NAME = "bundle";
	public static String BUNDLE_NAME          = "name";
	public static String BUNDLE_DESCRIPTION   = "descr";
	
	public static String WARNING = "CONFLICT ---> ";
	
	private Composite container;
	private List listOfSelectableBundles;
	private Text textBundleName;
	private Text textDescription;
	private Button checkbox;
	
	private IConfigurationElement[] configElements;
	
	protected ImportTestSpecWizardPage() {
		super(Messages.ImportTestSpecWizard_PAGE_NAME);
		setTitle(Messages.ImportTestSpecWizard_PAGE_TITLE);
		setMessage("");
	}
	
	@Override
	public void createControl(Composite parent) {
		
		// create main composite for this page
		container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));
		container.layout(true, true);
		
		// bundle-selection list
		Label lblSpecs = new Label(container, SWT.NONE);
		lblSpecs.setText("Select TestSpecification project to import:");
		lblSpecs.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		listOfSelectableBundles = new List(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		listOfSelectableBundles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		SelectionListener selectionListener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				validateAndUpdate();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
		};
		
		listOfSelectableBundles.addSelectionListener(selectionListener);
		
		// bundle symbolic name text
		Label lblBundle = new Label(container, SWT.NONE);
		lblBundle.setText("Name of last selected bundle:");
		lblBundle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		textBundleName = new Text(container, SWT.BORDER);
		textBundleName.setEditable(false);
		textBundleName.setText("");
		textBundleName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		// description text
		Label lblDescr = new Label(container, SWT.NONE);
		lblDescr.setText("Description:");
		lblDescr.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		textDescription = new Text(container, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		textDescription.setEditable(false);
		textDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		ModifyListener mod = new ModifyListener(){
			
			@Override
			public void modifyText(ModifyEvent e) {
				textDescription.update();
				textDescription.redraw();
			}
			
		};
		
		textDescription.addModifyListener(mod);
		textDescription.setText("");
		
		// checkbox
		checkbox = new Button(container, SWT.CHECK);
		checkbox.setText ("Delete conflicting projects in workspace prior to import");
		checkbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		checkbox.setSelection(false);
		checkbox.setEnabled(false);
		checkbox.setToolTipText("WARNING: The existing project will be deleted incl. all logs and reports!");
		checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getContainer().updateButtons();
			}
		});
		
		// Add the bundles to the list
		String importablesExtensionId = "org.globaltester.testspecification.importables";
		configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(importablesExtensionId);
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement curElem = configElements[i];
			
			String name = curElem.getAttribute(BUNDLE_NAME);
			
			listOfSelectableBundles.add(name);
		}
		
		// fill with default values
		if (listOfSelectableBundles.getItemCount() == 0) {
			// no valid test specification plugins are present -> show error
			// message
			setErrorMessage("No Plugin defining an importable GT TestSpecification is installed.");
		}
		
		container.layout(true, true);
		
		Shell shell = getShell();
		shell.setSize(500, 580);
		
		//update dialog size
		parent.pack();
		getControl().getParent().setSize(getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public boolean isPageComplete() {
		
		if(listOfSelectableBundles.getSelectionCount() == 0) {
			return true;
		}
		
		ArrayList<String> collidingBundleNames = new ArrayList<>();
		
		String currentlySelectedBundleName;
		for(int i=0; i<listOfSelectableBundles.getItemCount(); i++) {
			currentlySelectedBundleName = listOfSelectableBundles.getItem(i);
			if(currentlySelectedBundleName.startsWith(WARNING)) {
				listOfSelectableBundles.setItem(i, currentlySelectedBundleName.substring(WARNING.length()));
			}
		}
		
		int[] selectedEntryIndices = listOfSelectableBundles.getSelectionIndices();
		for(int i:selectedEntryIndices) {
			// check that the project name is unique
			currentlySelectedBundleName = configElements[i].getAttribute(BUNDLE_NAME);
			if (ResourcesPlugin.getWorkspace().getRoot().getProject(currentlySelectedBundleName).exists()) {
				collidingBundleNames.add(currentlySelectedBundleName);
				listOfSelectableBundles.setItem(i, WARNING + currentlySelectedBundleName);
			}
		}
		
		if(collidingBundleNames.size() > 0) {
			setErrorMessage("The current workspace already contains projects with the same name");
			checkbox.setEnabled(true);
		} else{
			checkbox.setSelection(false);
			checkbox.setEnabled(false);
			setErrorMessage(null);
		}
		
		return true;
	}

	public java.util.List<IConfigurationElement> getSelectedElements() {
		int[] selectedEntryIndices = listOfSelectableBundles.getSelectionIndices();
		ArrayList<IConfigurationElement> selectedEntryStrings = new ArrayList<>();
		for(int i:selectedEntryIndices) {
			selectedEntryStrings.add(configElements[i]);
		}
		return selectedEntryStrings;
	}

	private void validateAndUpdate() {
		int selectedEntry = listOfSelectableBundles.getSelectionIndex();
		
		textBundleName.setText(configElements[selectedEntry].getAttribute(BUNDLE_SYMBOLIC_NAME));
		textDescription.setText(configElements[selectedEntry].getAttribute(BUNDLE_DESCRIPTION));
		
		// update the dialog appearance
		getContainer().updateButtons();
	}
	
	public boolean canFinish() {
		isPageComplete();
		
		if(checkbox.isEnabled()) {
			return checkbox.getSelection();
		} else{
			return true;
		}
	}
	
}
