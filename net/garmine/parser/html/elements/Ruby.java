package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.RUBY;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Ruby extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return RUBY;
	}

	public Ruby(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

