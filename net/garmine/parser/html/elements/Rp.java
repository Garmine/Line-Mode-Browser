package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.RP;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Rp extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return RP;
	}

	public Rp(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

