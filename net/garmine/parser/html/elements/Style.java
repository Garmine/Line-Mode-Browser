package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Scoped;
import net.garmine.parser.html.attributes.Type;

public class Style extends Element {
	public  media;
	public  scoped;
	public  type;

	public Style(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "media":
					media = Media.parse(this, v);
					break;
				case "scoped":
					scoped = Scoped.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

