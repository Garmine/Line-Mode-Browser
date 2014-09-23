package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FORM;
import net.garmine.parser.html.attributes.Accept;
import net.garmine.parser.html.attributes.Acceptcharset;
import net.garmine.parser.html.attributes.Action;
import net.garmine.parser.html.attributes.Autocomplete;
import net.garmine.parser.html.attributes.Enctype;
import net.garmine.parser.html.attributes.Method;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Novalidate;
import net.garmine.parser.html.attributes.Target;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Form extends HtmlElement {
	public String accept;
	public String acceptcharset;
	public String action;
	public boolean autocomplete;
	public String enctype;
	public Method method;
	public String name;
	public boolean novalidate;
	public String target;

	@Override
	public HtmlElementType getType() {
		return FORM;
	}

	public Form(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "accept":
					accept = Accept.parse(this, v);
					break;
				case "acceptcharset":
					acceptcharset = Acceptcharset.parse(this, v);
					break;
				case "action":
					action = Action.parse(this, v);
					break;
				case "autocomplete":
					autocomplete = Autocomplete.parse(this, v);
					break;
				case "enctype":
					enctype = Enctype.parse(this, v);
					break;
				case "method":
					method = Method.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "novalidate":
					novalidate = Novalidate.parse(this, v);
					break;
				case "target":
					target = Target.parse(this, v);
					break;
			}
		}
	}
}

