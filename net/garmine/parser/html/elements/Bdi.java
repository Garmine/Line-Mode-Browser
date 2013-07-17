package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BDI;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Bdi extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return BDI;
	}

	public Bdi(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

