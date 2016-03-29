package org.globaltester.testspecification.ui.editors;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.globaltester.base.ui.editors.FoldingEditor;
import org.globaltester.logging.logger.GtErrorLogger;
import org.globaltester.testspecification.ui.Activator;

/**
 * This class is the main class of GlobalTester TestSpecEditor
 * 
 * @author Alexander May
 * 
 */
public class TestSpecEditor extends FoldingEditor{

	public TestSpecEditor() {
		super();
		setDocumentProvider(new TestSpecEditorDocumentProvider());
	}
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new TestSpecEditorConfiguration(this));
	}

	public void connect() {
		ITextFileBufferManager mgr = FileBuffers.getTextFileBufferManager();
		try {
			mgr.connect(((FileEditorInput) getEditorInput()).getFile()
					.getFullPath(), LocationKind.IFILE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		}
	}

	public void disconnect() {
		ITextFileBufferManager mgr = FileBuffers.getTextFileBufferManager();
		try {
			mgr.disconnect(((FileEditorInput) getEditorInput()).getFile()
					.getFullPath(), LocationKind.IFILE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
		}
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		connect();
	}

	@Override
	public void dispose() {
		disconnect();
		super.dispose();
		
	}
}
