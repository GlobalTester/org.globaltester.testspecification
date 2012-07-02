package org.globaltester.testspecification.ui.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({importOfATestSpecification_screenshotsTest.class,ExportWizardTest.class,importOfATestSpecification_monitorTest.class})
public class FullTestSuite {

}
