package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SELECT;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Multiple;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Required;
import net.garmine.parser.html.attributes.Size;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Select extends HtmlElement {
	public boolean autofocus;
	public boolean disabled;
	public String form;
	public boolean multiple;
	public String name;
	public boolean required;
	public int size;

	@Override
	public HtmlElementType getType() {
		return SELECT;
	}

	public Select(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "autofocus":
					autofocus = Autofocus.parse(this, v);
					break;
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "multiple":
					multiple = Multiple.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "required":
					required = Required.parse(this, v);
					break;
				case "size":
					size = Size.parse(this, v);
					break;
			}
		}
	}
}

