package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DIV;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Div extends HtmlElement {
	public String align;

	@Override
	public HtmlElementType getType() {
		return DIV;
	}

	public Div(HtmlMidNode parent, HtmlAttributeToken[] attrs){
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

