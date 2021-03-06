package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FIELDSET;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Fieldset extends HtmlElement {
	public boolean disabled;
	public String form;
	public String name;

	@Override
	public HtmlElementType getType() {
		return FIELDSET;
	}

	public Fieldset(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
			}
		}
	}
}

