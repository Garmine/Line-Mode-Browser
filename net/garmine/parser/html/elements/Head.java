package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.HEAD;
import net.garmine.parser.html.attributes.Profile;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Head extends HtmlElement {
	public String profile;

	@Override
	public HtmlElementType getType() {
		return HEAD;
	}

	public Head(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "profile":
					profile = Profile.parse(this, v);
					break;
			}
		}
	}
}

