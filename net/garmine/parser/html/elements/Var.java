package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Var extends HtmlElement {

	public Var(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

