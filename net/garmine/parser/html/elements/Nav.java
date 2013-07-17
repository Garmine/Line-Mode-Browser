package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.NAV;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Nav extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return NAV;
	}

	public Nav(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

