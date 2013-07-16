package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Manifest;

public class Html extends Element {
	public  manifest;

	public Html(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "manifest":
					manifest = Manifest.parse(this, v);
					break;
			}
		}
	}
}

