package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FIGCAPTION;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Figcaption extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return FIGCAPTION;
	}

	public Figcaption(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

