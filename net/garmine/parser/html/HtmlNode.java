package net.garmine.parser.html;

public abstract class HtmlNode {
	/** The parent element */
	public HtmlElement parent;
	
	/**
	 * Creates a new HTML node.
	 * @param parent - parent of the node
	 */
	public HtmlNode(HtmlElement parent){
		this.parent = parent;
		if (parent != null) parent.children.add(this);
	}
}
