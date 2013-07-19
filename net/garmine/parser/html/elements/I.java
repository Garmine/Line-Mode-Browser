package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.I;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class I extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return I;
	}

	public I(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

