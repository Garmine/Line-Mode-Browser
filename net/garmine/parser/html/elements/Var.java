package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.VAR;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Var extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return VAR;
	}

	public Var(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

