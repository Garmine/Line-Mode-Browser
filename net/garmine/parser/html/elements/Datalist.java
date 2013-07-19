package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DATALIST;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Datalist extends HtmlElement {

	@Override
	public HtmlElementType getType() {
		return DATALIST;
	}

	public Datalist(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);
	}
}

