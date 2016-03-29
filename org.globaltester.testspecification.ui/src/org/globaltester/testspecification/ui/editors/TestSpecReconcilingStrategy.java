package org.globaltester.testspecification.ui.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.globaltester.base.ui.editors.ReconcilingStrategy;

public class TestSpecReconcilingStrategy extends ReconcilingStrategy {

	protected char cLastNLChar = ' ';

	enum TagTypes {
		START_TAG, LEAF_TAG, END_TAG, EOR_TAG, COMMENT_TAG, PI_TAG, CDATA_TAG
	}
	
	enum LineMode {
		ALL, ONE
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.
	 * eclipse.jface.text.IRegion)
	 */
	public void reconcile(IRegion partition) {
		calculatePositions(partition.getOffset(), partition.getOffset() + partition.getLength());

		this.updateFoldingStructure();
	}

	/**
	 * uses {@link #fDocument}, {@link #fOffset} and {@link #fRangeEnd} to
	 * calculate {@link #fPositions}. About syntax errors: this method is not a
	 * validator, it is useful.
	 * @param rangeEnd 
	 * @param offset 
	 */
	protected void calculatePositions(int offset, int rangeEnd) {
		fPositions.clear();

		try {
			recursiveTokens(0, offset, rangeEnd);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		// Collections.sort(fPositions, new RangeTokenComparator());

	}
	
	/**
	 * emits tokens to {@link #fPositions}.
	 * @return 
	 * 
	 * @throws BadLocationException
	 */
	protected int recursiveTokens(int depth, int initialOffset, int rangeEnd) throws BadLocationException {
	int offset = initialOffset;
	int lastStartingTagOffset = -1;
		while (offset < rangeEnd) {
			offset = consumeWhitespace(offset, rangeEnd, LineMode.ALL);
			if (offset >= rangeEnd){
				break;
			}
			char ch = fDocument.getChar(offset);
			switch (ch) {
			case '<':
				int startTagOffset = offset;
				int tagLength = getTagLength(startTagOffset, rangeEnd);
				TagTypes classification = classifyTag(startTagOffset, rangeEnd);

				switch (classification) {
				case START_TAG:
					lastStartingTagOffset = startTagOffset;
					offset = recursiveTokens(depth + 1, startTagOffset+tagLength, rangeEnd);
					break;
				case LEAF_TAG:
				case COMMENT_TAG:
					offset += tagLength;
					break;
				case END_TAG:
					if (lastStartingTagOffset >= 0){
						offset += tagLength;
						if (fDocument.getLineOfOffset(lastStartingTagOffset) < fDocument.getLineOfOffset(startTagOffset + tagLength)) {
							int lengthToCollapse = startTagOffset + tagLength - lastStartingTagOffset;
							lengthToCollapse = consumeWhitespace(lastStartingTagOffset + lengthToCollapse, rangeEnd, LineMode.ONE) - lastStartingTagOffset;
							emitPosition(lastStartingTagOffset, lengthToCollapse);
						}
						lastStartingTagOffset = -1;
					} else {
						return startTagOffset;
					}
					break;
				case PI_TAG:
				case EOR_TAG:
				case CDATA_TAG:
					offset = startTagOffset + tagLength;
				default:
					break;
				}
				break;
			default:
				offset++;
				break;
			}
		}
		return offset;
	}

	private int getTagLength(int initialOffset, int rangeEnd) throws BadLocationException {
		int offset = initialOffset;
		if (offset + 9 <= rangeEnd && fDocument.get(offset, 9).equals("<![CDATA[")){
			offset += 9;
			while(offset + 3 < rangeEnd && !(fDocument.get(offset++, 3).equals("]]>")));
			offset += 2;
		} else {
			while (fDocument.getChar(offset++) != '>');
		}
		
		return offset - initialOffset;
	}

	protected void emitPosition(int startOffset, int length) {
		fPositions.add(new Position(startOffset, length));
	}

	/**
	 * classsifies a tag: <br />
	 * &lt;?...?&gt;: {@link #PI_TAG} <br />
	 * &lt;!...--&gt;: {@link #COMMENT_TAG} <br />
	 * &lt;...&gt;: {@link #START_TAG} <br />
	 * &lt;.../&gt;: {@link #LEAF_TAG} <br />
	 * &lt;/...&gt;: {@link #END_TAG} <br />
	 * &lt;...: {@link #EOR_TAG} (end of range reached before closing &gt; is
	 * found). <br />
	 * when this method is called, {@link #cNextPos} must point to the character
	 * after &lt;, when it returns, it points to the character after &gt; or
	 * after the range. About syntax errors: this method is not a validator, it
	 * is useful. Side effect: writes number of found newLines to
	 * {@link #cNewLines}.
	 * 
	 * @return the tag classification
	 */
	protected TagTypes classifyTag(int initialOffset, int rangeEnd) {
		int startingPosition = initialOffset;
		try {
			char ch = fDocument.getChar(startingPosition++);
			
			if ('<' != ch){
				return TagTypes.EOR_TAG;
			}
			
			ch = fDocument.getChar(startingPosition++);
			
			// processing instruction?
			if ('?' == ch) {
				boolean piFlag = false;
				while (startingPosition < rangeEnd) {
					ch = fDocument.getChar(startingPosition++);
					if (('>' == ch) && piFlag)
						return TagTypes.PI_TAG;
					piFlag = ('?' == ch);
				}
				return TagTypes.EOR_TAG;
			}

			// comment?
			if ('!' == ch) { 
				ch = fDocument.getChar(startingPosition++);
				switch (ch){
				case '-':
					startingPosition++; // must be '-' but we don't care if not
					int commEnd = 0;
					while (startingPosition < rangeEnd) {
						ch = fDocument.getChar(startingPosition++);
						if (('>' == ch) && (commEnd >= 2))
							return TagTypes.COMMENT_TAG;
						if ('-' == ch) {
							commEnd++;
						} else {
							commEnd = 0;
						}
					}
					return TagTypes.EOR_TAG;
				case '[':
					String cdataCandidate = fDocument.get(startingPosition++, 6);
					if (cdataCandidate.equals("CDATA[")){
						return TagTypes.CDATA_TAG;
					}
					break;
				}
			}

			startingPosition = consumeWhitespace(startingPosition, rangeEnd, LineMode.ALL);

			// end tag?
			if ('/' == ch) {
				while (startingPosition < rangeEnd) {
					ch = fDocument.getChar(startingPosition++);
					if ('>' == ch) {
						return TagTypes.END_TAG;
					}
					if ('"' == ch) {
						ch = fDocument.getChar(startingPosition++);
						while ((startingPosition < rangeEnd) && ('"' != ch)) {
							ch = fDocument.getChar(startingPosition++);
						}
					} else if ('\'' == ch) {
						ch = fDocument.getChar(startingPosition++);
						while ((startingPosition < rangeEnd) && ('\'' != ch)) {
							ch = fDocument.getChar(startingPosition++);
						}
					}
				}
				return TagTypes.EOR_TAG;
			}

			// start tag or leaf tag?
			while (startingPosition < rangeEnd) {
				ch = fDocument.getChar(startingPosition++);
				// end tag?
				s: switch (ch) {
				case '/':
					while (startingPosition < rangeEnd) {
						ch = fDocument.getChar(startingPosition++);
						if ('>' == ch) {
							return TagTypes.LEAF_TAG;
						}
					}
					return TagTypes.EOR_TAG;
				case '"':
					while (startingPosition < rangeEnd) {
						ch = fDocument.getChar(startingPosition++);
						if ('"' == ch)
							break s;
					}
					return TagTypes.EOR_TAG;
				case '\'':
					while (startingPosition < rangeEnd) {
						ch = fDocument.getChar(startingPosition++);
						if ('\'' == ch)
							break s;
					}
					return TagTypes.EOR_TAG;
				case '>':
					return TagTypes.START_TAG;
				default:
					break;
				}

			}
			return TagTypes.EOR_TAG;

		} catch (BadLocationException e) {
			// should not happen, but we treat it as end of range
			return TagTypes.EOR_TAG;
		}
	}
	
	private int consumeWhitespace(int startingPosition, int rangeEnd, LineMode mode) {
		int offset = startingPosition;
		char ch;
		try {
			while (offset < rangeEnd) {
				ch = fDocument.getChar(offset);
				if ((' ' == ch) || ('\t' == ch)) {
					offset++;
				} else if (('\n' == ch) || ('\r' == ch)){
					offset++;
					if (mode == LineMode.ONE){
						char possibleLineEndingCharacter = fDocument.getChar(offset);
						if (('\n' == possibleLineEndingCharacter) || ('\r' == possibleLineEndingCharacter)){
							offset++;
						}
						break;
					}
				} else {
					break;
				}
			}
		} catch (BadLocationException e) {
			return startingPosition;
		}
		return offset;
	}

	protected int eatToEndOfLine(int offset, int rangeEnd) throws BadLocationException {
		if (offset >= rangeEnd) {
			return 0;
		}
		char ch = fDocument.getChar(offset++);
		// 1. eat all spaces and tabs
		while ((offset < rangeEnd) && ((' ' == ch) || ('\t' == ch))) {
			ch = fDocument.getChar(offset++);
		}
		if (offset >= rangeEnd) {
			offset--;
			return 0;
		}

		// now ch is a new line or a non-whitespace
		if ('\n' == ch) {
			if (offset < rangeEnd) {
				ch = fDocument.getChar(offset++);
				if ('\r' != ch) {
					offset--;
				}
			} else {
				offset--;
			}
			return 1;
		}

		if ('\r' == ch) {
			if (offset < rangeEnd) {
				ch = fDocument.getChar(offset++);
				if ('\n' != ch) {
					offset--;
				}
			} else {
				offset--;
			}
			return 1;
		}

		return 0;
	}
}
