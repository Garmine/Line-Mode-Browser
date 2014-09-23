package net.garmine.parser.html.nodes;

import static net.garmine.parser.html.nodes.HtmlNodeType.COMMENT;

/**
 * An HTML comment node.
 * @author Garmine
 */
public class HtmlComment extends HtmlNode{
	/** Comment contained */
	public String comment = "";
	
	/**
	 * Constructs a new Rawtext and registers it at the parent (if any).
	 * @param parent - Element which contains the text
	 */
	public HtmlComment(HtmlMidNode parent){
		super(parent);
	}
	
	/**
	 * Constructs a new Rawtext,registers it at the parent (if any), and sets the text inside.
	 * @param parent - Element which contains the text
	 * @param comment - initial value
	 */
	public HtmlComment(HtmlMidNode parent, String comment){
		super(parent);
		this.comment = comment;
	}

	@Override
	public HtmlNodeType getNodeType() {
		return COMMENT;
	}

	@Override
	public HtmlComment asComment() {
		return this;
	}
}
