package net.garmine.parser.html.tokenizer.tokens;

import net.garmine.parser.html.tokenizer.tokens.HtmlTokenType;

/**
 * The singleton Token which represents the End Of File/End Of Stream 
 * @author Garmine
 */
public final class HtmlEofToken extends HtmlToken {
	/** The only one and true instance of the HTMLEOFToken */
	public static final HtmlEofToken EOF = new HtmlEofToken();
	
	private HtmlEofToken(){}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.EOF;
	}

	@Override
	public int hashCode() {
		//some random number.... dunno if it's good or not, but it's unique for sure!
		return 283133264;
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}

	@Override
	public String toString() {
		return getType().toString();
	}
}
