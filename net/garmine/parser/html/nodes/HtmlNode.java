package net.garmine.parser.html.nodes;

import net.garmine.parser.html.HtmlDocument;


/**
 * General class which represents nodes (etiher root- mid- or terminal ones) of the HTML tree.
 * @author Garmine
 */
public abstract class HtmlNode {
	/** The parent element */
	public HtmlMidNode parent;
	
	/**
	 * Checks if the JSON is of the supplied type
	 * @param type - type to check for
	 * @return True if this JSON is the specified type
	 */
	public boolean is(HtmlNodeType type){
		return getNodeType() == type;
	}
	
	/**
	 * Getter for the type of the node.
	 * @return The type of the node.
	 */
	public abstract HtmlNodeType getNodeType();
	
	/**
	 * Represents this as an HtmlElement.
	 * @return Itself, if it's an {@link HtmlElement}, null otherwise.
	 */
	public HtmlElement asElement(){
		return null;
	}

	/**
	 * Represents this as an HtmlComment.
	 * @return Itself, if it's an {@link HtmlComment}, null otherwise.
	 */
	public HtmlComment asComment(){
		return null;
	}

	/**
	 * Represents this as an HtmlText.
	 * @return Itself, if it's an {@link HtmlText}, null otherwise.
	 */
	public HtmlText asText(){
		return null;
	}
	
	/**
	 * Represents this as an HtmlDocument.
	 * @return Itself, if it's an {@link HtmlDocument}, null otherwise.
	 */
	public HtmlDocument asDocument(){
		return null;
	}
	
	/**
	 * Creates a new HTML node.
	 * @param parent - parent of the node
	 */
	public HtmlNode(HtmlMidNode parent){
		this.parent = parent;
		if (parent != null) parent.children.add(this);
	}
}
