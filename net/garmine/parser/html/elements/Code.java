package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CODE;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Code extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return CODE;
	}

	public Code(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

