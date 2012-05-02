package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import org.globaltester.document.export.Exporter;
import org.globaltester.testspecification.GtTestSpecNature;

public class ExportTestSpecWizardPage extends WizardPage {
	private List lstTestSpecSelection;
	private List lstExportLayoutSelection;
	private Text txtDestination;
	private Text txtLayoutDescription;
	
	private IConfigurationElement[] configElements;
	private Text txtSourcesZip;
	private Text txtStylesheet;
	private Composite customExport;
	private boolean isDefaultDestination;
	
	protected ExportTestSpecWizardPage() {
		super("");
		setTitle("TestSpecification Export Wizard");
		setMessage("");
	}

	@Override
	public void createControl(Composite parent) {
		// create main composite for this page
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		// Create the TestSpec project selection list
		Label lblSpecs = new Label(container, SWT.NONE);
		lblSpecs.setText("Select TestSpecification project to export:");
		lblSpecs.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false,
				2, 1));
		lstTestSpecSelection = new List(container, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		lstTestSpecSelection.setLayoutData(new GridData(SWT.FILL, SWT.TOP,
				false, false, 2, 1));
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

		// Create the TestSpec export layout selection list
		Label lblExportLayout = new Label(container, SWT.NONE);
		lblExportLayout.setText("Select TestSpecification export layout:");
		lblExportLayout.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));
		lstExportLayoutSelection = new List(container, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		lstExportLayoutSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
		lstExportLayoutSelection.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				validateAndUpdate();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Create the Custom export
		customExport = new Composite(container, SWT.NONE);
		customExport.setLayout(new GridLayout(2, false));
		customExport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2,1));
		customExport.setVisible(false);
		
		Label lblStylesheet = new Label(customExport, SWT.NONE);
		lblStylesheet.setText("Stylesheet:");
		lblStylesheet.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2,1));
		
		txtStylesheet = new Text(customExport, SWT.BORDER | SWT.FILL);
		txtStylesheet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtStylesheet.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validateAndUpdate();
			}
		});

		Button btnBrowseStylesheet = new Button(customExport, SWT.NONE);
		btnBrowseStylesheet.setText("Browse");
		btnBrowseStylesheet.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				txtStylesheet.setText(showFileDialog(new String[] { "XSL Stylesheet",
				"All Files (*.*)" },new String[] { "*.xsl", "*" },SWT.OPEN));
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}
		});	
		Label lblSourcesZip = new Label(customExport, SWT.NONE);
		lblSourcesZip.setText("Open Office Template:");
		lblSourcesZip.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2,1));
		
		txtSourcesZip = new Text(customExport, SWT.BORDER | SWT.FILL);
		txtSourcesZip.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtSourcesZip.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validateAndUpdate();
			}
		});
		Button btnBrowseSourcesZip = new Button(customExport, SWT.NONE);
		btnBrowseSourcesZip.setText("Browse");
		btnBrowseSourcesZip.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				txtSourcesZip.setText(showFileDialog(new String[] { "Open Office template zip file",
				"All Files (*.*)" },new String[] { "*.zip", "*" }, SWT.OPEN));
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}
		});
		
		// Create the TestSpec export layout description text
		Label lblLayoutDescr = new Label(container, SWT.NONE);
		lblLayoutDescr.setText("Description export layout:");
		lblLayoutDescr.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));

		txtLayoutDescription = new Text(container, SWT.BORDER);
		txtLayoutDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		txtLayoutDescription.setEditable(false);
		txtLayoutDescription.setText("");

		
		// Export destination
		Label lblDest = new Label(container, SWT.NONE);
		lblDest.setText("Export destination:");
		lblDest.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1));

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
		
		Button btnBrowseDestination = new Button(container, SWT.NONE);
		btnBrowseDestination.setText("Browse");
		btnBrowseDestination.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent event) {
				txtDestination.setText(showFileDialog(new String[] { "OpenDocumentText",
				"All Files (*.*)" },new String[] { "*.odt", "*" }, getProjectName() + ".odt", SWT.SAVE));
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				txtDestination.setText("");
			}
		});

		// Add all GT TestSpec projects to the list
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

		
		// Add all export layouts to the list
		configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(Exporter.EXTENSIONPOINT_ID);
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement curElem = configElements[i];
			String name = curElem.getAttribute("name");
			lstExportLayoutSelection.add(name);
		}
		lstExportLayoutSelection.add("Custom");

		// fill with default values
		if (lstExportLayoutSelection.getItemCount() > 0) {
			//defaultName = true;
			// select the first element
			lstExportLayoutSelection.select(0);
			validateAndUpdate();
		} else {
			// no valid test specification export layouts are present -> show error
			// message
			setErrorMessage("No TestSpecification Export Layout is installed.");
		}

		
		
	}

	private String showFileDialog(String [] filterNames, String [] filterExtensions, int swtStyle){
		return showFileDialog(filterNames, filterExtensions, null, swtStyle);
		
	}
	
	private String showFileDialog(String [] filterNames, String [] filterExtensions, String defaultFileName, int swtStyle){
		FileDialog dialog = new FileDialog(txtDestination.getShell(),
				swtStyle);
		dialog.setFilterNames(filterNames);
		dialog.setFilterExtensions(filterExtensions);
		if (defaultFileName != null){
			dialog.setFileName(defaultFileName);	
		}
		String selectedFileName = dialog.open();
		if (selectedFileName == null) {
			selectedFileName = "";
		}
		return selectedFileName;
	}
	
	private void setDefaultDestination() {
		txtDestination.setText(System.getProperty("user.dir")+System.getProperty("file.separator")+getProjectName() + ".odt");
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
		
		if (lstExportLayoutSelection.getSelectionIndex() == lstExportLayoutSelection.getItemCount() - 1){
			if (!(new File(txtStylesheet.getText())).canRead()){
				setErrorMessage("Please select a XSL stylesheet");
				return false;
			}
			if (!(new File(txtSourcesZip.getText())).canRead()){
				setErrorMessage("Please select a Open Office template zip");
				return false;
			}
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
		// update destination
		if (isDefaultDestination) {
			setDefaultDestination();
		}
		
		// update the description
		int selectionIndex = lstExportLayoutSelection.getSelectionIndex();
		// last item is selected, means custom
		if (selectionIndex == lstExportLayoutSelection.getItemCount() - 1){
			customExport.setVisible(true);
			txtLayoutDescription.setVisible(false);
		} else {
			customExport.setVisible(false);
			txtLayoutDescription.setVisible(true);
		}

		if (configElements != null && configElements.length > selectionIndex){
			txtLayoutDescription.setText(configElements[selectionIndex].getAttribute("description"));
		}
		
		// update the dialog appearance
		getContainer().updateButtons();
		getContainer().updateMessage();
	}
	
	/**
	 * Return the stylesheet from the exporter plugin or the custom file
	 * @return the selected stylesheet
	 * @throws IOException
	 */
	public InputStream getStylesheet() throws IOException{
		IConfigurationElement exporter = getExporter();
		if (exporter != null){
			String stylesheet = getExporter().getAttribute("stylesheet");
			String plugin = exporter.getDeclaringExtension().getUniqueIdentifier();
			return FileLocator.openStream(Platform.getBundle(plugin), new Path("/" + stylesheet), false);
		} else {
			return new FileInputStream(txtStylesheet.getText());
		}
	}
	
	/**
	 * Return the Open Office template zip from the exporter plugin or the custom file
	 * @return the selected stylesheet
	 * @throws IOException
	 */	
	public InputStream getSourcesZip() throws IOException{
		IConfigurationElement exporter = getExporter();
		if (exporter != null){
			String sourcesZip = exporter.getAttribute("sourceZip");
			String plugin = exporter.getDeclaringExtension().getUniqueIdentifier();
			return FileLocator.openStream(Platform.getBundle(plugin), new Path("/" + sourcesZip), false);
		} else {
			return new FileInputStream(txtSourcesZip.getText());
		}
	}
	
	private IConfigurationElement getExporter() {
		int selectionIndex = lstExportLayoutSelection.getSelectionIndex();
		if (configElements.length > selectionIndex){
			return configElements[selectionIndex];
		}
		return null;
	}

}
