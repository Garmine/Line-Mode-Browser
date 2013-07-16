package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Cite;
import net.garmine.parser.html.attributes.Datetime;

public class Del extends Element {
	public  cite;
	public  datetime;

	public Del(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "cite":
					cite = Cite.parse(this, v);
					break;
				case "datetime":
					datetime = Datetime.parse(this, v);
					break;
			}
		}
	}
}

