package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.NOFRAMES;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Noframes extends HtmlElement {
	
	@Override
	public HtmlElementType getType() {
		return NOFRAMES;
	}

	public Noframes(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

