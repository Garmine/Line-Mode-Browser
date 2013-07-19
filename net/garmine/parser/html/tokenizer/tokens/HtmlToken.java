package net.garmine.parser.html.tokenizer.tokens;

import java.util.NoSuchElementException;

/**
 * The abstract superclass of every HTML Token.
 * @see net.garmine.parser.html.tokenizer.HtmlTokenizer
 * @author Garmine
 */
public abstract class HtmlToken {
	
	//==========================================================\\
	//~~~>Type stuff											||
	//==========================================================//
	
	private NoSuchElementException typeErr(){
		return new NoSuchElementException("ERROR: Invalid type!");
	}
	
	/**
	 * Checks if the JSON is of the supplied type
	 * @param type - type to check for
	 * @return True if this JSON is the specified type
	 */
	public boolean is(HtmlTokenType type){
		return getType() == type;
	}
	
	/**
	 * Getter fpr the type of the Token.
	 * @return The type of the token.
	 */
	public abstract HtmlTokenType getType();

	//==========================================================\\
	//~~~>Value manipulation									||
	//==========================================================//
	
	//Character token:
	
	/**
	 * Character Token's getter.
	 * @throws NoSuchElementException - if this isn't a Character Token.
	 * @return The Character Token's value.
	 */
	public char getChar(){
		throw typeErr();
	}
	
	//Tag Token:
	
	/**
	 * Getter for the Tag Token's name.
	 * @throws NoSuchElementException - if this isn't a Tag Token.
	 * @return The Tag Token's name
	 */
	public String getName(){
		throw typeErr();
	}

	/**
	 * Getter for the Tag Token's attributes.
	 * @throws NoSuchElementException - if this isn't a Tag Token.
	 * @return The Tag Token's attributes.
	 */
	public HtmlAttributeToken[] getAttributes(){
		throw typeErr();
	}

	/**
	 * Getter for the Tag Token's end-tag flag.
	 * @throws NoSuchElementException - if this isn't a Tag Token.
	 * @return True, if it's an end Tag.
	 */
	public boolean isEndTag(){
		throw typeErr();
	}

	/**
	 * Getter for the Tag Token's self-closing.
	 * @throws NoSuchElementException - if this isn't a Tag Token.
	 * @return True, if this is a self closing Tag.
	 */
	public boolean isSelfClosing(){
		throw typeErr();
	}
	
	//Attribute token:
	
	/**
	 * Getter for the Attribute Token's name.
	 * @throws NoSuchElementException - if this isn't an Attribute Token.
	 * @return The Attribute's name.
	 */
	public String getAttrName(){
		throw typeErr();
	}

	/**
	 * Getter for the Attribute Token's value.
	 * @throws NoSuchElementException - if this isn't an Attribute Token.
	 * @return The Attribute's value.
	 */
	public String getAttrValue(){
		throw typeErr();
	}
	
	//Comment token:
	
	/**
	 * Getter for the Character Token's contents.
	 * @throws NoSuchElementException - if this isn't a Comment Token.
	 * @return The Comment's text.
	 */
	public String getComment(){
		throw typeErr();
	}
	
	//DOCTYPE token:
	
	/**
	 * Getter for the Doctype Token's name.
	 * @throws NoSuchElementException - if this isn't a Doctype Token.
	 * @return The Doctype Token's name.
	 */
	public String getDocName(){
		throw typeErr();
	}

	/**
	 * Getter for the Doctype Token's PUBLIC identifier.
	 * @throws NoSuchElementException - if this isn't a Doctype Token.
	 * @return The Doctype Token's PUBLIC identifier.
	 */
	public String getDocPublic(){
		throw typeErr();
	}

	/**
	 * Getter for the Doctype Token's SYSTEM identifier.
	 * @throws NoSuchElementException - if this isn't a Doctype Token.
	 * @return The Doctype Token's SYSTEM identifier.
	 */
	public String getDocSystem(){
		throw typeErr();
	}

	/**
	 * Getter for the Doctype Token's force-quirks flag.
	 * @throws NoSuchElementException - if this isn't a Doctype Token.
	 * @return True, if the Doctype Token's force-quirks flag is set.
	 */
	public boolean doForceQuirks(){
		throw typeErr();
	}
	
	
	//==========================================================\\
	//~~~>Etc													||
	//==========================================================//
	
	@Override public abstract String toString();
	@Override public abstract int hashCode();
	@Override public abstract boolean equals(Object obj);
}
