package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.BLOCKQUOTE;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Cite;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Blockquote extends HtmlElement {
	public String cite;

	@Override
	public HtmlElementType getType() {
		return BLOCKQUOTE;
	}

	public Blockquote(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "cite":
					cite = Cite.parse(this, v);
					break;
			}
		}
	}
}

