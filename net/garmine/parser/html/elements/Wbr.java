package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.WBR;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Wbr extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return WBR;
	}

	public Wbr(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

