package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.RT;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Rt extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return RT;
	}

	public Rt(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

