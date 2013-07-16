package net.garmine.parser.html.tokenizer.tokens;

import net.garmine.parser.html.tokenizer.tokens.HtmlTokenType;

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
}
