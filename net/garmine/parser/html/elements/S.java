package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.S;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class S extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return S;
	}

	public S(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

