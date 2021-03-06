package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.NOSCRIPT;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Noscript extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return NOSCRIPT;
	}

	public Noscript(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

