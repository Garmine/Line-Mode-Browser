package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Multiple;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Required;
import net.garmine.parser.html.attributes.Size;

public class Select extends Element {
	public  autofocus;
	public  disabled;
	public  form;
	public  multiple;
	public  name;
	public  required;
	public  size;

	public Select(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "autofocus":
					autofocus = Autofocus.parse(this, v);
					break;
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "multiple":
					multiple = Multiple.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "required":
					required = Required.parse(this, v);
					break;
				case "size":
					size = Size.parse(this, v);
					break;
			}
		}
	}
}

