package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.OPTION;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.attributes.Selected;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Option extends HtmlElement {
	public boolean disabled;
	public String label;
	public boolean selected;
	public String value;

	@Override
	public HtmlElementType getType() {
		return OPTION;
	}

	public Option(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "label":
					label = Label.parse(this, v);
					break;
				case "selected":
					selected = Selected.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
			}
		}
	}
}

