package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DATALIST;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Datalist extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return DATALIST;
	}

	public Datalist(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

