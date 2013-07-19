package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BR;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Br extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return BR;
	}

	public Br(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

