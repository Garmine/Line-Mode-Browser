package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.CAPTION;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Caption extends HtmlElement {
	public String align;

	@Override
	public HtmlElementType getType() {
		return CAPTION;
	}

	public Caption(HtmlElement parent, HtmlAttributeToken[] attrs){
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

