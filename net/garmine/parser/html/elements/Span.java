package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SPAN;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Span extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return SPAN;
	}

	public Span(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

