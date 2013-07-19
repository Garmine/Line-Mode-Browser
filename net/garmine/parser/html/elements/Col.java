package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.COL;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Char;
import net.garmine.parser.html.attributes.Charoff;
import net.garmine.parser.html.attributes.Span;
import net.garmine.parser.html.attributes.Valign;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Col extends HtmlElement {
	public String align;
	public char charr;
	public int charoff;
	public int span;
	public Valign valign;
	public int width;

	@Override
	public HtmlElementType getType() {
		return COL;
	}

	public Col(HtmlElement parent, HtmlAttributeToken[] attrs){
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
				case "span":
					span = Span.parse(this, v);
					break;
				case "valign":
					valign = Valign.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

