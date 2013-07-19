package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.B;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class B extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return B;
	}

	public B(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

