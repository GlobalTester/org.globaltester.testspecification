package org.globaltester.testspecification.ui.editors;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;
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
public class TestSpecEditor extends FoldingEditor implements IPropertyListener {

	public TestSpecEditor() {
		super();
		setDocumentProvider(new TestSpecEditorDocumentProvider());
		setSourceViewerConfiguration(new TestSpecEditorConfiguration(this));
		addPropertyListener(this);
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
	public void propertyChanged(Object source, int propId) {
		if (this != source)
			return;
		if (propId == IWorkbenchPartConstants.PROP_DIRTY) {
			setDirty(this.isDirty());
		}

	}

	/**
	 * Set the dirtyState for the IFile associated with this EditorInput based
	 * on FileBuffers. This is required in order to prevent accidental deletion
	 * when unsaved changes exist in Editors.
	 * 
	 * @param isDirty
	 *            new dirty state
	 */
	public void setDirty(boolean isDirty) {
		ITextFileBuffer buffer = null;
		buffer = FileBuffers.getTextFileBufferManager().getTextFileBuffer(
				((FileEditorInput) getEditorInput()).getFile().getFullPath(),
				LocationKind.IFILE);
		if (buffer != null) {
			buffer.setDirty(isDirty);
		}
	}

	@Override
	public void dispose() {
		disconnect();
		super.dispose();
		
	}
}
