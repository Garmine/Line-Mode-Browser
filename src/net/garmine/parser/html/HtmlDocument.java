package net.garmine.parser.html;

import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.nodes.HtmlNodeType;

import static net.garmine.parser.html.nodes.HtmlNodeType.DOCUMENT;

/**
 * General class which represents the root of the HTML tree - i.e. the Document itself.
 * @author Garmine
 */
public class HtmlDocument extends HtmlMidNode {
	/**
	 * Creates a new HTML document.
	 * @param parent - parent of the node
	 */
	public HtmlDocument(HtmlMidNode parent) {
		super(parent);
		if (this.parent != null) throw new IllegalArgumentException("HtmlDocument must be the root element!");
	}

	@Override
	public HtmlNodeType getNodeType() {
		return DOCUMENT;
	}
	
	@Override
	public HtmlDocument asDocument(){
		return this;
	}
}
