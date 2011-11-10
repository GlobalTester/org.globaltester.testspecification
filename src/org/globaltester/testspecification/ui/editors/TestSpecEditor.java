package org.globaltester.testspecification.ui.editors;

import org.globaltester.core.ui.editors.FoldingEditor;

/**
 * This class is the main class of GlobalTester TestSpecEditor
 * 
 * @author Alexander May
 * 
 */
public class TestSpecEditor extends FoldingEditor {

	public TestSpecEditor() {
		super();
		setDocumentProvider(new TestSpecEditorDocumentProvider());
		setSourceViewerConfiguration(new TestSpecEditorConfiguration(this));
	}
}
