package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.META;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Charset;
import net.garmine.parser.html.attributes.Content;
import net.garmine.parser.html.attributes.Httpequiv;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Scheme;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Meta extends HtmlElement {
	public String charset;
	public String content;
	public String httpequiv;
	public String name;
	public String scheme;

	@Override
	public HtmlElementType getType() {
		return META;
	}

	public Meta(HtmlElement parent, HtmlAttributeToken[] attrs){
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

