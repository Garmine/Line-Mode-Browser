package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BASEFONT;
import net.garmine.parser.html.attributes.Color;
import net.garmine.parser.html.attributes.Face;
import net.garmine.parser.html.attributes.Size;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Basefont extends HtmlElement {
	public String color;
	public String face;
	public int size;

	@Override
	public HtmlElementType getType() {
		return BASEFONT;
	}

	public Basefont(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

