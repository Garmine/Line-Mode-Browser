package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TIME;
import net.garmine.parser.html.attributes.Datetime;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Time extends HtmlElement {
	public String datetime;

	@Override
	public HtmlElementType getType() {
		return TIME;
	}

	public Time(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "datetime":
					datetime = Datetime.parse(this, v);
					break;
			}
		}
	}
}

