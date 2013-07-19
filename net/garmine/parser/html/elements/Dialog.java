package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DIALOG;
import net.garmine.parser.html.attributes.Open;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Dialog extends HtmlElement {
	public boolean open;

	@Override
	public HtmlElementType getType() {
		return DIALOG;
	}

	public Dialog(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "open":
					open = Open.parse(this, v);
					break;
			}
		}
	}
}

