package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.MENU;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Menu extends HtmlElement {
	public String label;
	public String type;

	@Override
	public HtmlElementType getType() {
		return MENU;
	}

	public Menu(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "label":
					label = Label.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

