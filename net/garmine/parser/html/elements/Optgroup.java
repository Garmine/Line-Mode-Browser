package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.OPTGROUP;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Optgroup extends HtmlElement {
	public boolean disabled;
	public String label;

	@Override
	public HtmlElementType getType() {
		return OPTGROUP;
	}

	public Optgroup(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "label":
					label = Label.parse(this, v);
					break;
			}
		}
	}
}

