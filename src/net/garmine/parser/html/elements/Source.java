package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SOURCE;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Source extends HtmlElement {
	public String media;
	public String src;
	public String type;

	@Override
	public HtmlElementType getType() {
		return SOURCE;
	}

	public Source(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "media":
					media = Media.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

