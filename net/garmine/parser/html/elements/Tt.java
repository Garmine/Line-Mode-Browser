package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TT;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Tt extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return TT;
	}

	public Tt(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

