package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DL;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Dl extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return DL;
	}

	public Dl(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

