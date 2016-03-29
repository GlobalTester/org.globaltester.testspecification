package org.globaltester.testspecification.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

/**
 * This class defines the DocumentProvider of TestSpecEditor
 * 
 * @author Alexander May
 * 
 */


public class TestSpecEditorDocumentProvider extends TextFileDocumentProvider {

	public static final String CT_JS = "__CT_JS";
	
	@Override
	public IDocument getDocument(Object element) {
		IDocument document = super.getDocument(element);
		if (document != null) {
			RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
			scanner.setPredicateRules(new IPredicateRule[]{new MultiLineRule("<![CDATA[", "]]>",  new Token(CT_JS))}); 
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					scanner, new String []{CT_JS});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}
