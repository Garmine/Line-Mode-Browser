package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.THEAD;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Char;
import net.garmine.parser.html.attributes.Charoff;
import net.garmine.parser.html.attributes.Valign;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Thead extends HtmlElement {
	public String align;
	public char charr;
	public int charoff;
	public Valign valign;

	@Override
	public HtmlElementType getType() {
		return THEAD;
	}

	public Thead(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "char":
					charr = Char.parse(this, v);
					break;
				case "charoff":
					charoff = Charoff.parse(this, v);
					break;
				case "valign":
					valign = Valign.parse(this, v);
					break;
			}
		}
	}
}

