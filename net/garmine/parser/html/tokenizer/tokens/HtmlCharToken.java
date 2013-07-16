package net.garmine.parser.html.tokenizer.tokens;

import net.garmine.parser.html.tokenizer.tokens.HtmlTokenType;

public class HtmlCharToken extends HtmlToken {
	private final char CHAR;
	
	public HtmlCharToken(char c){
		CHAR = c;
	}
	
	@Override
	public char getChar(){
		return CHAR;
	}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.CHAR;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CHAR;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HtmlCharToken other = (HtmlCharToken) obj;
		if (CHAR != other.CHAR)
			return false;
		return true;
	}
}
