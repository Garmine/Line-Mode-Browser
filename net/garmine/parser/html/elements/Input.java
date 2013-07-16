package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Accept;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Alt;
import net.garmine.parser.html.attributes.Autocomplete;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Checked;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Formaction;
import net.garmine.parser.html.attributes.Formenctype;
import net.garmine.parser.html.attributes.Formmethod;
import net.garmine.parser.html.attributes.Formnovalidate;
import net.garmine.parser.html.attributes.Formtarget;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.List;
import net.garmine.parser.html.attributes.Max;
import net.garmine.parser.html.attributes.Maxlength;
import net.garmine.parser.html.attributes.Min;
import net.garmine.parser.html.attributes.Multiple;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Pattern;
import net.garmine.parser.html.attributes.Placeholder;
import net.garmine.parser.html.attributes.Readonly;
import net.garmine.parser.html.attributes.Required;
import net.garmine.parser.html.attributes.Size;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Step;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.attributes.Width;

public class Input extends Element {
	public  accept;
	public  align;
	public  alt;
	public  autocomplete;
	public  autofocus;
	public  checked;
	public  disabled;
	public  form;
	public  formaction;
	public  formenctype;
	public  formmethod;
	public  formnovalidate;
	public  formtarget;
	public  height;
	public  list;
	public  max;
	public  maxlength;
	public  min;
	public  multiple;
	public  name;
	public  pattern;
	public  placeholder;
	public  readonly;
	public  required;
	public  size;
	public  src;
	public  step;
	public  type;
	public  value;
	public  width;

	public Input(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "accept":
					accept = Accept.parse(this, v);
					break;
				case "align":
					align = Align.parse(this, v);
					break;
				case "alt":
					alt = Alt.parse(this, v);
					break;
				case "autocomplete":
					autocomplete = Autocomplete.parse(this, v);
					break;
				case "autofocus":
					autofocus = Autofocus.parse(this, v);
					break;
				case "checked":
					checked = Checked.parse(this, v);
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
				case "height":
					height = Height.parse(this, v);
					break;
				case "list":
					list = List.parse(this, v);
					break;
				case "max":
					max = Max.parse(this, v);
					break;
				case "maxlength":
					maxlength = Maxlength.parse(this, v);
					break;
				case "min":
					min = Min.parse(this, v);
					break;
				case "multiple":
					multiple = Multiple.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "pattern":
					pattern = Pattern.parse(this, v);
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
				case "size":
					size = Size.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "step":
					step = Step.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

