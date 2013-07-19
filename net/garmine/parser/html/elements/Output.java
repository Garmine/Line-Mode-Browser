package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.OUTPUT;
import net.garmine.parser.html.attributes.For;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Output extends HtmlElement {
	public String forr;
	public String form;
	public String name;

	@Override
	public HtmlElementType getType() {
		return OUTPUT;
	}

	public Output(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

