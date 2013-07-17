package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.DIV;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Div extends HtmlElement {
	public String align;

	@Override
	public HtmlElementType getType() {
		return DIV;
	}

	public Div(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
			}
		}
	}
}

