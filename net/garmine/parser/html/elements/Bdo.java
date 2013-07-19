package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BDO;
import net.garmine.parser.html.attributes.Dir;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Bdo extends HtmlElement {
	public Dir dir;

	@Override
	public HtmlElementType getType() {
		return BDO;
	}

	public Bdo(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "dir":
					dir = Dir.parse(this, v);
					break;
			}
		}
	}
}

