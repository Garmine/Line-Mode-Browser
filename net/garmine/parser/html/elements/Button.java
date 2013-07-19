package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BUTTON;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Formaction;
import net.garmine.parser.html.attributes.Formenctype;
import net.garmine.parser.html.attributes.Formmethod;
import net.garmine.parser.html.attributes.Formnovalidate;
import net.garmine.parser.html.attributes.Formtarget;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Button extends HtmlElement {
	public boolean autofocus;
	public boolean disabled;
	public String form;
	public String formaction;
	public String formenctype;
	public Formmethod formmethod;
	public boolean formnovalidate;
	public String formtarget;
	public String name;
	public String type;
	public String value;

	@Override
	public HtmlElementType getType() {
		return BUTTON;
	}

	public Button(HtmlElement parent, HtmlAttributeToken[] attrs){
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
				case "formaction":
					formaction = Formaction.parse(this, v);
					break;
				case "formenctype":
					formenctype = Formenctype.parse(this, v);
					break;
				case "formmethod":
					formmethod = Formmethod.parse(this, v);
					break;
				case "formnovalidate":
					formnovalidate = Formnovalidate.parse(this, v);
					break;
				case "formtarget":
					formtarget = Formtarget.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
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

