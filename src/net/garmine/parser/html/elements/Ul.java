package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.UL;
import net.garmine.parser.html.attributes.Compact;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Ul extends HtmlElement {
	public boolean compact;
	public String type;

	@Override
	public HtmlElementType getType() {
		return UL;
	}

	public Ul(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "compact":
					compact = Compact.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

