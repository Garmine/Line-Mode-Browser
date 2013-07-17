package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BIG;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Big extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return BIG;
	}

	public Big(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

