package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SPAN;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Span extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return SPAN;
	}

	public Span(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

