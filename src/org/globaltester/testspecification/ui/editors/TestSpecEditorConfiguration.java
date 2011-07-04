package org.globaltester.testspecification.ui.editors;

import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.globaltester.core.ui.editors.ContentTypeAppearanceManager;
import org.globaltester.core.ui.editors.XMLScanner;

public class TestSpecEditorConfiguration extends SourceViewerConfiguration {
	private ITextDoubleClickStrategy doubleClickStrategy;
	private XMLScanner scanner;
	private ContentTypeAppearanceManager contentTypeAppearanceManager;
	private TestSpecEditor editor;

	public TestSpecEditorConfiguration(
			ContentTypeAppearanceManager colorManager, TestSpecEditor editor) {
		this.contentTypeAppearanceManager = colorManager;
		this.editor = editor;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				ContentTypeAppearanceManager.CONTENT_TYPE_XML_COMMENT,
				ContentTypeAppearanceManager.CONTENT_TYPE_XML_TAG,
				ContentTypeAppearanceManager.CONTENT_TYPE_JS_COMMENT,
				ContentTypeAppearanceManager.CONTENT_TYPE_JS_KEYWORD };
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new DefaultTextDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected XMLScanner getXMLScanner() {
		if (scanner == null) {
			scanner = new XMLScanner(XMLScanner.TokenType.TEXT_ATTRIBUTES);
			scanner.setDefaultReturnToken(contentTypeAppearanceManager
					.getContentTypeAppearance(ContentTypeAppearanceManager.CONTENT_TYPE_DEFAULT+"asd"));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		// add DamagerRepairer for XML content
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setDamager(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_COMMENT);
		reconciler.setRepairer(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_COMMENT);
		reconciler.setDamager(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_PROC_INSTR);
		reconciler.setRepairer(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_PROC_INSTR);
		reconciler.setDamager(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_TAG);
		reconciler.setRepairer(dr, ContentTypeAppearanceManager.CONTENT_TYPE_XML_TAG);

		return reconciler;
	}

	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		ReconcilingStrategy strategy = new ReconcilingStrategy();
		strategy.setEditor(editor);

		MonoReconciler reconciler = new MonoReconciler(strategy, false);

		return reconciler;
	}

}