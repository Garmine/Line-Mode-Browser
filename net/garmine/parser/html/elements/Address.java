package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.ADDRESS;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Address extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return ADDRESS;
	}

	public Address(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

