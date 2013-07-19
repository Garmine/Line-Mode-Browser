package net.garmine.parser.html.nodes;

import java.util.ArrayList;

/**
 * General class which represents middle nodes of the HTML tree.
 * @author Garmine
 */
public abstract class HtmlMidNode extends HtmlNode {
	/** Children of the node */
	public ArrayList<HtmlNode> children;
	
	/**
	 * Creates a new HTML middle node.
	 * @param parent - parent of the node
	 */
	public HtmlMidNode(HtmlMidNode parent) {
		super(parent);
		children = new ArrayList<>();
	}
}
