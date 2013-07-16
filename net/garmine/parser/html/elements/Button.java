package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Formaction;
import net.garmine.parser.html.attributes.Formenctype;
import net.garmine.parser.html.attributes.Formmethod;
import net.garmine.parser.html.attributes.Post;
import net.garmine.parser.html.attributes.Formnovalidate;
import net.garmine.parser.html.attributes.Formtarget;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;

public class Button extends Element {
	public  autofocus;
	public  disabled;
	public  form;
	public  formaction;
	public  formenctype;
	public  formmethod;
	public  post;
	public  formnovalidate;
	public  formtarget;
	public  name;
	public  type;
	public  value;

	public Button(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
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
				case "post":
					post = Post.parse(this, v);
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

