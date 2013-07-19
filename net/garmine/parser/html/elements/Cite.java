package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CITE;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Cite extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return CITE;
	}

	public Cite(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

