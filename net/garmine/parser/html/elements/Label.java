package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.For;
import net.garmine.parser.html.attributes.Form;

public class Label extends Element {
	public  for;
	public  form;

	public Label(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "for":
					for = For.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
			}
		}
	}
}

