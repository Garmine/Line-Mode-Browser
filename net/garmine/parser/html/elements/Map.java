package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.MAP;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Map extends HtmlElement {
	public String name;

	@Override
	public HtmlElementType getType() {
		return MAP;
	}

	public Map(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "name":
					name = Name.parse(this, v);
					break;
			}
		}
	}
}

