package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.HEADER;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Header extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return HEADER;
	}

	public Header(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

