package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SAMP;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Samp extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return SAMP;
	}

	public Samp(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

