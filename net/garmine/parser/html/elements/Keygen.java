package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.KEYGEN;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Autofocus;
import net.garmine.parser.html.attributes.Challenge;
import net.garmine.parser.html.attributes.Disabled;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Keytype;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Keygen extends HtmlElement {
	public boolean autofocus;
	public String challenge;
	public boolean disabled;
	public String form;
	public Keytype keytype;
	public String name;

	@Override
	public HtmlElementType getType() {
		return KEYGEN;
	}

	public Keygen(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "autofocus":
					autofocus = Autofocus.parse(this, v);
					break;
				case "challenge":
					challenge = Challenge.parse(this, v);
					break;
				case "disabled":
					disabled = Disabled.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "keytype":
					keytype = Keytype.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
			}
		}
	}
}

