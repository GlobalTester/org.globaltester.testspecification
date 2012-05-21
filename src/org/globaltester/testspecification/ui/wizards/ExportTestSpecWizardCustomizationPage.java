package org.globaltester.testspecification.ui.wizards;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.globaltester.document.export.XslParameter;

/**
 * @author mboonk
 * Allow the Customization of the exported document
 */
public class ExportTestSpecWizardCustomizationPage extends WizardPage {

	private Text txtDocTitle;
	private Text txtDocSubTitle;
	private Text txtCompanyName;
	private Text txtCompanyAddress1;
	private Text txtCompanyAddress2;
	private Text txtCompanyTel;
	private Text txtCompanyMail;
	private Text txtCompanyWWW;
	
	protected ExportTestSpecWizardCustomizationPage() {
		super("");
		setTitle("TestSpecification Export Wizard");
		setMessage("");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label lblDocTitle = new Label(container, SWT.NONE);
		lblDocTitle.setText("Title:");
		lblDocTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtDocTitle = new Text(container, SWT.BORDER);
		txtDocTitle.setText("GlobalTester");
		txtDocTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));

		Label lblDocSubTitle = new Label(container, SWT.NONE);
		lblDocSubTitle.setText("Subtitle:");
		lblDocSubTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtDocSubTitle = new Text(container, SWT.BORDER);
		txtDocSubTitle.setText("Test Specification");
		txtDocSubTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyName = new Label(container, SWT.NONE);
		lblCompanyName.setText("Company:");
		lblCompanyName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyName = new Text(container, SWT.BORDER);
		txtCompanyName.setText("HJP Consulting GmbH");
		txtCompanyName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyAddress1 = new Label(container, SWT.NONE);
		lblCompanyAddress1.setText("Address:");
		lblCompanyAddress1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyAddress1 = new Text(container, SWT.BORDER);
		txtCompanyAddress1.setText("Hauptstraﬂe 35");
		txtCompanyAddress1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyAddress2 = new Label(container, SWT.NONE);
		lblCompanyAddress2.setText("Address:");
		lblCompanyAddress2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyAddress2 = new Text(container, SWT.BORDER);
		txtCompanyAddress2.setText("33178 Borchen, Germany");
		txtCompanyAddress2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyTel = new Label(container, SWT.NONE);
		lblCompanyTel.setText("Telephone:");
		lblCompanyTel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyTel = new Text(container, SWT.BORDER);
		txtCompanyTel.setText("+49 5251 41776- 0");
		txtCompanyTel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyMail = new Label(container, SWT.NONE);
		lblCompanyMail.setText("eMail:");
		lblCompanyMail.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyMail = new Text(container, SWT.BORDER);
		txtCompanyMail.setText("globaltester@hjp-consulting.com");
		txtCompanyMail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
		
		Label lblCompanyWWW = new Label(container, SWT.NONE);
		lblCompanyWWW.setText("Internet:");
		lblCompanyWWW.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,	false, 1, 1));
		
		txtCompanyWWW = new Text(container, SWT.BORDER);
		txtCompanyWWW.setText("http://www.globaltester.org");
		txtCompanyWWW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,	false, 1, 1));
	}

	public XslParameter [] getXslParams() {
		List<XslParameter> params = new ArrayList<XslParameter>();
		params.add(new XslParameter("document.title", txtDocTitle.getText()));
		params.add(new XslParameter("document.subtitle", txtDocSubTitle.getText()));
		params.add(new XslParameter("company.name", txtCompanyName.getText()));
		params.add(new XslParameter("company.address1", txtCompanyAddress1.getText()));
		params.add(new XslParameter("company.address2", txtCompanyAddress2.getText()));
		params.add(new XslParameter("company.tel", txtCompanyTel.getText()));
		params.add(new XslParameter("company.mail", txtCompanyMail.getText()));
		params.add(new XslParameter("company.www", txtCompanyWWW.getText()));
		params.add(new XslParameter("year", Calendar.getInstance().get(Calendar.YEAR)));
		return params.toArray(new XslParameter [params.size()]);
	}

}
