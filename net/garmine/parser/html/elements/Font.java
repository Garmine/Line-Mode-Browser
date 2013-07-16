package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Color;
import net.garmine.parser.html.attributes.Face;
import net.garmine.parser.html.attributes.Size;

public class Font extends Element {
	public  color;
	public  face;
	public  size;

	public Font(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "color":
					color = Color.parse(this, v);
					break;
				case "face":
					face = Face.parse(this, v);
					break;
				case "size":
					size = Size.parse(this, v);
					break;
			}
		}
	}
}

