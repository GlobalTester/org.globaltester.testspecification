package org.globaltester.testspecification.ui.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.globaltester.base.ui.editors.GtScanner;
import org.globaltester.base.ui.editors.GtScanner.TokenType;
import org.globaltester.base.ui.editors.JSScanner;
import org.globaltester.base.ui.editors.ReconcilingStrategy;
import org.globaltester.base.ui.editors.XMLScanner;

public class TestSpecEditorConfiguration extends SourceViewerConfiguration {
	private ITextDoubleClickStrategy doubleClickStrategy;
	private GtScanner xmlFormatScanner;
	private GtScanner jsFormatScanner;
	private TestSpecEditor editor;

	public TestSpecEditorConfiguration(TestSpecEditor editor) {
		this.editor = editor;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		List<String> types = new ArrayList<String>();
		types.addAll(Arrays.asList(super.getConfiguredContentTypes(sourceViewer)));
		types.addAll(Arrays.asList(getXmlScanner().getLegalContentTypes()));
		types.addAll(Arrays.asList(getJsScanner().getLegalContentTypes()));
		return types.toArray(new String [types.size()]);
	}
	
	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new DefaultTextDoubleClickStrategy();
		return doubleClickStrategy;
	}

	private GtScanner getXmlScanner() {
		if (xmlFormatScanner != null){
			return xmlFormatScanner;
		}
		xmlFormatScanner = new GtScanner(TokenType.TEXT_ATTRIBUTES);

		XMLScanner.addAllPredicateRules(xmlFormatScanner,
				TokenType.TEXT_ATTRIBUTES);
		return xmlFormatScanner;
	}

	private GtScanner getJsScanner() {
		if (jsFormatScanner != null){
			return jsFormatScanner;
		}
		jsFormatScanner = new GtScanner(TokenType.TEXT_ATTRIBUTES);

		JSScanner.addAllPredicateRules(jsFormatScanner,
				TokenType.TEXT_ATTRIBUTES);
		return jsFormatScanner;
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer xmldr = new DefaultDamagerRepairer(getXmlScanner());
		reconciler.setDamager(xmldr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(xmldr, IDocument.DEFAULT_CONTENT_TYPE);
		
		DefaultDamagerRepairer jsdr = new DefaultDamagerRepairer(getJsScanner());
		reconciler.setDamager(jsdr, TestSpecEditorDocumentProvider.CT_JS);
		reconciler.setRepairer(jsdr, TestSpecEditorDocumentProvider.CT_JS);
		
		return reconciler;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		ReconcilingStrategy strategy = new TestSpecReconcilingStrategy();
		strategy.setEditor(editor);

		MonoReconciler reconciler = new MonoReconciler(strategy, false);
		
		return reconciler;
	}
}
