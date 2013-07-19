package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.STYLE;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Scoped;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Style extends HtmlElement {
	public String media;
	public boolean scoped;
	public String type;

	@Override
	public HtmlElementType getType() {
		return STYLE;
	}

	public Style(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

