package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.LI;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Li extends HtmlElement {
	public String type;
	public String value;

	@Override
	public HtmlElementType getType() {
		return LI;
	}

	public Li(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "type":
					type = Type.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
			}
		}
	}
}

