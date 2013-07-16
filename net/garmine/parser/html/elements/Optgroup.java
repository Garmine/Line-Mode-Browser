package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Label;

public class Optgroup extends Element {
	public  disabled;
	public  label;

	public Optgroup(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "label":
					label = Label.parse(this, v);
					break;
			}
		}
	}
}

