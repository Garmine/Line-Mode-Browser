package net.garmine.parser.html.nodes;

import static net.garmine.parser.html.nodes.HtmlNodeType.TEXT;

/**
 * An HTML text node.
 * @author Garmine
 */
public class HtmlText extends HtmlNode {
	/** Text contained */
	public String text = "";
	
	/**
	 * Constructs a new Rawtext and registers it at the parent (if any).
	 * @param parent - Element which contains the text
	 */
	public HtmlText(HtmlMidNode parent){
		super(parent);
	}
	
	/**
	 * Constructs a new Rawtext,registers it at the parent (if any), and sets the text inside.
	 * @param parent - Element which contains the text
	 * @param text - initial value
	 */
	public HtmlText(HtmlMidNode parent, String text){
		super(parent);
		if(text != null){
			this.text = text;
		}else{
			this.text = "";
		}
	}

	@Override
	public HtmlNodeType getNodeType() {
		return TEXT;
	}

	@Override
	public HtmlText asText() {
		return this;
	}
}
