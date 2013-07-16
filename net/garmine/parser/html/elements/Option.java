package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.attributes.Selected;
import net.garmine.parser.html.attributes.Value;

public class Option extends HtmlElement {
	public boolean disabled;
	public String label;
	public boolean selected;
	public String value;

	public Option(HtmlElement parent, HtmlAttributeToken[] attrs){
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
				case "selected":
					selected = Selected.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
			}
		}
	}
}

