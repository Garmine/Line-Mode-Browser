package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Code extends HtmlElement {

	public Code(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

