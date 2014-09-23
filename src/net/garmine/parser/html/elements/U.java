package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.U;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class U extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return U;
	}

	public U(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

