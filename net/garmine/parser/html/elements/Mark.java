package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.MARK;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Mark extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return MARK;
	}

	public Mark(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

