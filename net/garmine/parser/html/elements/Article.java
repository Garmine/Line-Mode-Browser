package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.ARTICLE;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Article extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return ARTICLE;
	}

	public Article(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

