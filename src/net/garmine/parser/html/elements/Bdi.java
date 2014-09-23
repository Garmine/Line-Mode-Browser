package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BDI;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Bdi extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return BDI;
	}

	public Bdi(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

