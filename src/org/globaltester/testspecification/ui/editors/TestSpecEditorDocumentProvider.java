package org.globaltester.testspecification.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

/**
 * This class defines the DocumentProvider of TestSpecEditor
 * 
 * @author Alexander May
 * 
 */


public class TestSpecEditorDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			GTRuleBasedPartitionScanner scanner = new GTRuleBasedPartitionScanner();
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					scanner, scanner.getLegalContentTypes());
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}