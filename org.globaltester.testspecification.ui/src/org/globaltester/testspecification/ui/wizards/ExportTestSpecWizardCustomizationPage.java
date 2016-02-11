package org.globaltester.testspecification.ui.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.globaltester.core.Activator;
import org.globaltester.document.export.IExportValidator;
import org.globaltester.logging.logger.GtErrorLogger;

/**
 * @author Martin Boonk
 * 
 * Allow the Customization of the exported document by setting xsl parameters for the export
 * 
 */
public class ExportTestSpecWizardCustomizationPage extends WizardPage implements ModifyListener {
	
	public static String PREFERENCE_PREFIX = "gt.export.wizard";
	private HashMap<String, Object> params = new HashMap<String, Object>();
	Composite parent;
	Composite container;
	List<Text> textFields = new ArrayList<Text>();
	IExportValidator validator = null;
	IExtension lastExporter;
	
	protected ExportTestSpecWizardCustomizationPage() {
		super("");
		setTitle("TestSpecification Export Wizard");
		setMessage("");
	}

	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));	
		container.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		setControl(container);

	}
	
	/**
	 * This method updates the page and stored parameters according to the selected exporter.
	 * It has to run in the display thread because it modifies SWT widgets. 
	 */
	protected void update() {
		IExtension exporter = ((ExportTestSpecWizardPage) getPreviousPage()).getExporter();
		IConfigurationElement [] configElements = ((ExportTestSpecWizardPage) getPreviousPage()).getCustomData();
		if (exporter != lastExporter){
			lastExporter = exporter;
			setMessage(null);
			textFields.clear();
			// remove the old fields
			for (Control control : container.getChildren()){
				control.dispose();
			}
			
			if (configElements.length > 0){
				IConfigurationElement exportData = ((ExportTestSpecWizardPage) getPreviousPage()).getExportData();
				if (exportData.getAttribute("validator") != null){
					try {
						validator = (IExportValidator) exportData.createExecutableExtension("validator");
					} catch (CoreException e) {
						GtErrorLogger.log(Activator.PLUGIN_ID, e);
					}
				}

				// create label and text for every parameter 
				for (int i = 0; i < configElements.length; i++) {
					String key = configElements[i].getAttribute("key");
					String text = configElements[i].getAttribute("text");
					String defaultValue = null;
					if (PlatformUI.getPreferenceStore().contains(PREFERENCE_PREFIX + "." + key)){
						defaultValue = PlatformUI.getPreferenceStore().getString(PREFERENCE_PREFIX + "." + key);
					} else {
						defaultValue = configElements[i].getAttribute("defaultValue");
					}
					boolean enabled = configElements[i].getAttribute("enabled").equals("true") ? true : false;

					Label lbl = new Label(container, SWT.NONE);
					lbl.setText(text + ":");
					lbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,1, 1));

					Text txt = new Text(container, SWT.BORDER);
					textFields.add(txt);
					txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));

					txt.setData(key);
					
					if (!enabled){
						txt.setEditable(false);
						if (validator != null){
							Object generatedValue = validator.generate(key);
							txt.setText(generatedValue.toString());
							params.put(key, generatedValue);
						} else {
							params.put(key, defaultValue);
						}
					} else {
						params.put(key, defaultValue);
					}
					
					if (params.get(key) != null){
						txt.setText(params.get(key).toString());
					}
					txt.addModifyListener(this);
				}
			} else {
				Label lbl = new Label(container, SWT.NONE);
				lbl.setText("The selected exporter does not support any parameters");
				lbl.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			}
			container.layout();
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		String widgetKey = (String)e.widget.getData();
		Text text = (Text) e.widget;
		if (validator != null) {
			if (validator.validate(widgetKey, text.getText(), params)) {
				params.put(widgetKey, text.getText());
				setMessage(null);
				setPageComplete(true);
				if (!validateAllTextFields()) {
					setMessage(validator.getMessage(), DialogPage.WARNING);
					setPageComplete(false);
				}
			} else {
				setMessage(validator.getMessage(), DialogPage.WARNING);
				setPageComplete(false);
			}
		}
		
	}
		
	private boolean validateAllTextFields(){
		if (validator != null){
			for (Text text : textFields) {
				String key = (String) text.getData();
				if (!validator.validate(key, text.getText(), params)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public HashMap<String, Object> getXslParams() {
		return params;
	}

}
