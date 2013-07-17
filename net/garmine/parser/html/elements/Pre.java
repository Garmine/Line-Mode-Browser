package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.PRE;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Pre extends HtmlElement {
	public int width;

	@Override
	public HtmlElementType getType() {
		return PRE;
	}

	public Pre(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

