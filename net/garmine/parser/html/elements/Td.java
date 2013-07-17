package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TD;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Abbr;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Axis;
import net.garmine.parser.html.attributes.Bgcolor;
import net.garmine.parser.html.attributes.Char;
import net.garmine.parser.html.attributes.Charoff;
import net.garmine.parser.html.attributes.Colspan;
import net.garmine.parser.html.attributes.Headers;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Nowrap;
import net.garmine.parser.html.attributes.Rowspan;
import net.garmine.parser.html.attributes.Scope;
import net.garmine.parser.html.attributes.Valign;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Td extends HtmlElement {
	public String abbr;
	public String align;
	public String axis;
	public String bgcolor;
	public char charr;
	public int charoff;
	public int colspan;
	public String headers;
	public int height;
	public boolean nowrap;
	public int rowspan;
	public Scope scope;
	public Valign valign;
	public int width;

	@Override
	public HtmlElementType getType() {
		return TD;
	}

	public Td(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "abbr":
					abbr = Abbr.parse(this, v);
					break;
				case "align":
					align = Align.parse(this, v);
					break;
				case "axis":
					axis = Axis.parse(this, v);
					break;
				case "bgcolor":
					bgcolor = Bgcolor.parse(this, v);
					break;
				case "char":
					charr = Char.parse(this, v);
					break;
				case "charoff":
					charoff = Charoff.parse(this, v);
					break;
				case "colspan":
					colspan = Colspan.parse(this, v);
					break;
				case "headers":
					headers = Headers.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "nowrap":
					nowrap = Nowrap.parse(this, v);
					break;
				case "rowspan":
					rowspan = Rowspan.parse(this, v);
					break;
				case "scope":
					scope = Scope.parse(this, v);
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

