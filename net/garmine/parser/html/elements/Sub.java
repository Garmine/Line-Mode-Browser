package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SUB;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Sub extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return SUB;
	}

	public Sub(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

