package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Noframes extends HtmlElement {
	
	public Noframes(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

