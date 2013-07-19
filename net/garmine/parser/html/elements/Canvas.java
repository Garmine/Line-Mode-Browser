package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CANVAS;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Canvas extends HtmlElement {
	public int height;
	public int width;

	@Override
	public HtmlElementType getType() {
		return CANVAS;
	}

	public Canvas(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

