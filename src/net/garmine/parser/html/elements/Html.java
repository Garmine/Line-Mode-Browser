package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.HTML;
import net.garmine.parser.html.attributes.Manifest;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Html extends HtmlElement {
	public String manifest;

	@Override
	public HtmlElementType getType() {
		return HTML;
	}

	public Html(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "manifest":
					manifest = Manifest.parse(this, v);
					break;
			}
		}
	}
}

