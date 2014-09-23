package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BIG;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Big extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return BIG;
	}

	public Big(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

