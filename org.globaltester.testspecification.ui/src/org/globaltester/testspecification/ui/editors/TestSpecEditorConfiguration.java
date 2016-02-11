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
import org.globaltester.core.ui.editors.GtScanner;
import org.globaltester.core.ui.editors.GtScanner.TokenType;
import org.globaltester.core.ui.editors.JSScanner;
import org.globaltester.core.ui.editors.XMLScanner;

public class TestSpecEditorConfiguration extends SourceViewerConfiguration {
	private ITextDoubleClickStrategy doubleClickStrategy;
	private GtScanner formatScanner;
	private TestSpecEditor editor;

	public TestSpecEditorConfiguration(TestSpecEditor editor) {
		this.editor = editor;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return getScanner().getLegalContentTypes();
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new DefaultTextDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected GtScanner getScanner() {
		if (formatScanner == null) {
			formatScanner = new GtScanner(TokenType.TEXT_ATTRIBUTES);

			XMLScanner.addAllPredicateRules(formatScanner,
					TokenType.TEXT_ATTRIBUTES);
			JSScanner.addAllPredicateRules(formatScanner,
					TokenType.TEXT_ATTRIBUTES);
			
		}
		return formatScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		// create DamagerRepairer
		GtScanner scanner = getScanner();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		// register damager/repairer for all content types supported by scanner
		String[] contentTypes = scanner.getLegalContentTypes();
		for (int i = 0; i < contentTypes.length; i++) {
			if (contentTypes[i] == null)
				continue;
			reconciler.setDamager(dr, contentTypes[i]);
			reconciler.setRepairer(dr, contentTypes[i]);
		}

		return reconciler;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		TestSpecReconcilingStrategy strategy = new TestSpecReconcilingStrategy();
		strategy.setEditor(editor);

		MonoReconciler reconciler = new MonoReconciler(strategy, false);

		return reconciler;
	}

}