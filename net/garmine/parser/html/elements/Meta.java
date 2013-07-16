package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Charset;
import net.garmine.parser.html.attributes.Content;
import net.garmine.parser.html.attributes.Httpequiv;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Scheme;

public class Meta extends Element {
	public  charset;
	public  content;
	public  httpequiv;
	public  name;
	public  scheme;

	public Meta(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "charset":
					charset = Charset.parse(this, v);
					break;
				case "content":
					content = Content.parse(this, v);
					break;
				case "httpequiv":
					httpequiv = Httpequiv.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "scheme":
					scheme = Scheme.parse(this, v);
					break;
			}
		}
	}
}

