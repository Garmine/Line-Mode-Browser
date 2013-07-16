package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Href;
import net.garmine.parser.html.attributes.Target;

public class Base extends HtmlElement {
	public String href;
	public String target;

	public Base(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "href":
					href = Href.parse(this, v);
					break;
				case "target":
					target = Target.parse(this, v);
					break;
			}
		}
	}
}

