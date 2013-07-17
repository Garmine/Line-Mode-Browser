package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CANVAS;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Canvas extends HtmlElement {
	public int height;
	public int width;

	@Override
	public HtmlElementType getType() {
		return CANVAS;
	}

	public Canvas(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "height":
					height = Height.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

