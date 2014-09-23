package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.EMBED;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Embed extends HtmlElement {
	public int height;
	public String src;
	public String type;
	public int width;

	@Override
	public HtmlElementType getType() {
		return EMBED;
	}

	public Embed(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "height":
					height = Height.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

