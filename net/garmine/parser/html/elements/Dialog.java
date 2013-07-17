package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DIALOG;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Open;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Dialog extends HtmlElement {
	public boolean open;

	@Override
	public HtmlElementType getType() {
		return DIALOG;
	}

	public Dialog(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "open":
					open = Open.parse(this, v);
					break;
			}
		}
	}
}

