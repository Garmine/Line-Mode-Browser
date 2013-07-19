package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DT;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Dt extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return DT;
	}

	public Dt(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

