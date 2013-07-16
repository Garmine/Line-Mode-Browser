package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Article extends HtmlElement {

	public Article(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

