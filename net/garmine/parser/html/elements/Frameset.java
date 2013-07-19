package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.FRAMESET;
import net.garmine.parser.html.attributes.Cols;
import net.garmine.parser.html.attributes.Rows;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Frameset extends HtmlElement {
	public String cols;
	public String rows;

	@Override
	public HtmlElementType getType() {
		return FRAMESET;
	}

	public Frameset(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "cols":
					cols = Cols.parse(this, v);
					break;
				case "rows":
					rows = Rows.parse(this, v);
					break;
			}
		}
	}
}

