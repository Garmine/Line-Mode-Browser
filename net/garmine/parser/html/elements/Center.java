package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CENTER;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Center extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return CENTER;
	}

	public Center(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

