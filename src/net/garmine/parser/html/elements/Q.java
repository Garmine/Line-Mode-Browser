package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.Q;
import net.garmine.parser.html.attributes.Cite;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Q extends HtmlElement {
	public String cite;

	@Override
	public HtmlElementType getType() {
		return Q;
	}

	public Q(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "cite":
					cite = Cite.parse(this, v);
					break;
			}
		}
	}
}

