package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FIGURE;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Figure extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return FIGURE;
	}

	public Figure(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

