package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.H1;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class H1 extends HtmlElement {
	public String align;

	@Override
	public HtmlElementType getType() {
		return H1;
	}

	public H1(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "align":
					align = Align.parse(this, v);
					break;
			}
		}
	}
}

