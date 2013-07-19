package net.garmine.parser.html.tokenizer.tokens;

import net.garmine.parser.html.tokenizer.tokens.HtmlTokenType;

/**
 * The Token which represents characters.
 * @author Garmine
 */
public class HtmlCharToken extends HtmlToken {
	private final char CHAR;
	
	/**
	 * Constructs a new HTML character Token
	 * @param c - value of the Token
	 */
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

	@Override
	public String toString() {
		char cc = getChar();
		String c;
		switch(cc){
			case '\t':
				c = "\\t";
				break;
			case '\b':
				c = "\\b";
				break;
			case '\n':
				c = "\\n";
				break;
			case '\r':
				c = "\\r";
				break;
			case '\f':
				c = "\\f";
				break;
			case '\'':
				c = "\\\'";
				break;
			case '\"':
				c = "\\\"";
				break;
			case '\\':
				c = "\\\\";
				break;
			case '{':
				c = "\\{";
				break;
			case '}':
				c = "\\}";
				break;
			default:
				c = ""+cc;
				break;
		}
		return getType().toString()+'{'+c+'}';
	}
}
