package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.STRONG;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Strong extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return STRONG;
	}

	public Strong(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

