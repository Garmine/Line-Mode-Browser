package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TEXTAREA;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Cols;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Maxlength;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Placeholder;
import net.garmine.parser.html.attributes.Readonly;
import net.garmine.parser.html.attributes.Required;
import net.garmine.parser.html.attributes.Rows;
import net.garmine.parser.html.attributes.Wrap;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Textarea extends HtmlElement {
	public boolean autofocus;
	public String cols;
	public boolean disabled;
	public String form;
	public int maxlength;
	public String name;
	public String placeholder;
	public boolean readonly;
	public boolean required;
	public String rows;
	public Wrap wrap;

	@Override
	public HtmlElementType getType() {
		return TEXTAREA;
	}

	public Textarea(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "autofocus":
					autofocus = Autofocus.parse(this, v);
					break;
				case "cols":
					cols = Cols.parse(this, v);
					break;
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "maxlength":
					maxlength = Maxlength.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "placeholder":
					placeholder = Placeholder.parse(this, v);
					break;
				case "readonly":
					readonly = Readonly.parse(this, v);
					break;
				case "required":
					required = Required.parse(this, v);
					break;
				case "rows":
					rows = Rows.parse(this, v);
					break;
				case "wrap":
					wrap = Wrap.parse(this, v);
					break;
			}
		}
	}
}

