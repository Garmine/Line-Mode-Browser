package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Datetime;

public class Time extends HtmlElement {
	public String datetime;

	public Time(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "datetime":
					datetime = Datetime.parse(this, v);
					break;
			}
		}
	}
}

