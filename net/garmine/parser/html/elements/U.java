package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.U;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class U extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return U;
	}

	public U(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

