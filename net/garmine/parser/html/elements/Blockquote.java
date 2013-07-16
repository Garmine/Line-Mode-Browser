package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Cite;

public class Blockquote extends HtmlElement {
	public String cite;

	public Blockquote(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "cite":
					cite = Cite.parse(this, v);
					break;
			}
		}
	}
}

