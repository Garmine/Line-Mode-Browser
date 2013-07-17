package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.LABEL;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.For;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Label extends HtmlElement {
	public String forr;
	public String form;

	@Override
	public HtmlElementType getType() {
		return LABEL;
	}

	public Label(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "for":
					forr = For.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
			}
		}
	}
}

