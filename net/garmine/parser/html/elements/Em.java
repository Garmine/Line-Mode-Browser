package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.EM;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Em extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return EM;
	}

	public Em(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

