package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.ABBR;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Abbr extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return ABBR;
	}

	public Abbr(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

