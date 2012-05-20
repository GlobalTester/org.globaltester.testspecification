package org.globaltester.testspecification.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.globaltester.core.ui.editors.GtScanner;
import org.globaltester.core.ui.editors.GtScanner.TokenType;
import org.globaltester.core.ui.editors.JSScanner;
import org.globaltester.core.ui.editors.XMLScanner;

/**
 * This class defines the DocumentProvider of TestSpecEditor
 * 
 * @author Alexander May
 * 
 */


public class TestSpecEditorDocumentProvider extends TextFileDocumentProvider {

	
	private GtScanner partitionScanner;

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.getDocument(element);
		if (document != null) {
			GtScanner scanner = getPartitionScanner();
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					scanner, scanner.getLegalContentTypes());
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}

	protected GtScanner getPartitionScanner() {
		if (partitionScanner == null) {
			partitionScanner = new GtScanner(TokenType.CONTENT_TYPE);

			JSScanner.addAllPartitionRules(partitionScanner, TokenType.CONTENT_TYPE);
			XMLScanner.addAllPartitionRules(partitionScanner, TokenType.CONTENT_TYPE);
			
			
		}
		return partitionScanner;
	}
}