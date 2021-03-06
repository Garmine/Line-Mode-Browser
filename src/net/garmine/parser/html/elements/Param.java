package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.PARAM;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.attributes.Valuetype;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Param extends HtmlElement {
	public String name;
	public String type;
	public String value;
	public String valuetype;

	@Override
	public HtmlElementType getType() {
		return PARAM;
	}

	public Param(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "name":
					name = Name.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
				case "valuetype":
					valuetype = Valuetype.parse(this, v);
					break;
			}
		}
	}
}

