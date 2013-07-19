package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TITLE;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Title extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return TITLE;
	}

	public Title(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

