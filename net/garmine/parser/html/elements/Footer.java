package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FOOTER;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Footer extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return FOOTER;
	}

	public Footer(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

