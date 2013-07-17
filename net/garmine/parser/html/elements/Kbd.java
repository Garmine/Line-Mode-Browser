package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.KBD;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Kbd extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return KBD;
	}

	public Kbd(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

