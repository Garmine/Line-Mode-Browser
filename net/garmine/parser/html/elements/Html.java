package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.HTML;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Manifest;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Html extends HtmlElement {
	public String manifest;

	@Override
	public HtmlElementType getType() {
		return HTML;
	}

	public Html(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "manifest":
					manifest = Manifest.parse(this, v);
					break;
			}
		}
	}
}

