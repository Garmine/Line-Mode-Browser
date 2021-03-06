package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.COMPACT;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Compact extends HtmlElement {
	public boolean compact;

	@Override
	public HtmlElementType getType() {
		return COMPACT;
	}

	public Compact(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "compact":
					compact = net.garmine.parser.html.attributes.Compact.parse(this, v);
					break;
			}
		}
	}
}

