package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Cite;
import net.garmine.parser.html.attributes.Datetime;

public class Ins extends HtmlElement {
	public String cite;
	public String datetime;

	public Ins(HtmlElement parent, HtmlAttributeToken[] attrs){
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

