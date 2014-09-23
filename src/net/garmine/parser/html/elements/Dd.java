package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DD;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Dd extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return DD;
	}

	public Dd(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

