package net.garmine.parser.html.tokenizer.tokens;

import java.util.NoSuchElementException;

public abstract class HtmlToken {
	
	//==========================================================\\
	//~~~>Type stuff											||
	//==========================================================//
	
	private NoSuchElementException typeErr(){
		return new NoSuchElementException("ERROR: Invalid type!");
	}
	
	protected NullPointerException nullErr(){
		return new NullPointerException("ERROR: Value mustn't be null!");
	}
	
	protected void checkNull(Object value){
		if (value == null) throw nullErr();
	}
	
	/**
	 * Checks if the JSON is of the supplied type
	 * @param type - type to check for
	 * @return True if this JSON is the specified type
	 */
	public boolean is(HtmlTokenType type){
		return getType() == type;
	}
	
	public abstract HtmlTokenType getType();

	//==========================================================\\
	//~~~>Value manipulation									||
	//==========================================================//
	
	//Character token:
	public char getChar(){
		throw typeErr();
	}
	
	//Tag Token:
	public String getName(){
		throw typeErr();
	}
	
	public HtmlAttributeToken[] getAttributes(){
		throw typeErr();
	}
	
	public boolean isEndTag(){
		throw typeErr();
	}
	
	public boolean isSelfClosing(){
		throw typeErr();
	}
	
	//Attribute token:
	public String getAttrName(){
		throw typeErr();
	}
	
	public String getAttrValue(){
		throw typeErr();
	}
	
	//Comment token:
	public String getComment(){
		throw typeErr();
	}
	
	//DOCTYPE token:
	public String getDocName(){
		throw typeErr();
	}
	
	public String getDocPublic(){
		throw typeErr();
	}
	
	public String getDocSystem(){
		throw typeErr();
	}
	
	public boolean doForceQuirks(){
		throw typeErr();
	}
	
	
	//==========================================================\\
	//~~~>Etc													||
	//==========================================================//
	
	@Override public abstract int hashCode();
	@Override public abstract boolean equals(Object obj);
}
