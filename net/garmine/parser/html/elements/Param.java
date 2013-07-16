package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.attributes.Valuetype;

public class Param extends HtmlElement {
	public String name;
	public String type;
	public String value;
	public String valuetype;

	public Param(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
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

