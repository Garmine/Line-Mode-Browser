package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SMALL;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Small extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return SMALL;
	}

	public Small(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

