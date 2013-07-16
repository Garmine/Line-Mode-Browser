package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Checked;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Icon;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.attributes.Radiogroup;
import net.garmine.parser.html.attributes.Type;

public class Command extends HtmlElement {
	public boolean checked;
	public boolean disabled;
	public String icon;
	public String label;
	public String radiogroup;
	public String type;

	public Command(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "checked":
					checked = Checked.parse(this, v);
					break;
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "icon":
					icon = Icon.parse(this, v);
					break;
				case "label":
					label = Label.parse(this, v);
					break;
				case "radiogroup":
					radiogroup = Radiogroup.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

