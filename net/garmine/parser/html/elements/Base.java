package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BASE;
import net.garmine.parser.html.attributes.Href;
import net.garmine.parser.html.attributes.Target;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Base extends HtmlElement {
	public String href;
	public String target;

	@Override
	public HtmlElementType getType() {
		return BASE;
	}

	public Base(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "href":
					href = Href.parse(this, v);
					break;
				case "target":
					target = Target.parse(this, v);
					break;
			}
		}
	}
}

