package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FOOTER;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Footer extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return FOOTER;
	}

	public Footer(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

