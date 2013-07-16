package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Compact extends HtmlElement {
	public boolean compact;

	public Compact(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "compact":
					compact = net.garmine.parser.html.attributes.Compact.parse(this, v);
					break;
			}
		}
	}
}

