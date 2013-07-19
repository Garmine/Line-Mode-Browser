package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DEL;
import net.garmine.parser.html.attributes.Cite;
import net.garmine.parser.html.attributes.Datetime;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Del extends HtmlElement {
	public String cite;
	public String datetime;

	@Override
	public HtmlElementType getType() {
		return DEL;
	}

	public Del(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "cite":
					cite = Cite.parse(this, v);
					break;
				case "datetime":
					datetime = Datetime.parse(this, v);
					break;
			}
		}
	}
}

