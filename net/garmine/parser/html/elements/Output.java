package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.For;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Name;

public class Output extends HtmlElement {
	public String forr;
	public String form;
	public String name;

	public Output(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "for":
					forr = For.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
			}
		}
	}
}

