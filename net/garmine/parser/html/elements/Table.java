package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TABLE;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Bgcolor;
import net.garmine.parser.html.attributes.Border;
import net.garmine.parser.html.attributes.Cellpadding;
import net.garmine.parser.html.attributes.Cellspacing;
import net.garmine.parser.html.attributes.Frame;
import net.garmine.parser.html.attributes.Rules;
import net.garmine.parser.html.attributes.Summary;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Table extends HtmlElement {
	public String align;
	public String bgcolor;
	public boolean border;
	public int cellpadding;
	public int cellspacing;
	public String frame;
	public Rules rules;
	public String summary;
	public int width;

	@Override
	public HtmlElementType getType() {
		return TABLE;
	}

	public Table(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "center":
					break;
				case "bgcolor":
					bgcolor = Bgcolor.parse(this, v);
					break;
				case "border":
					border = Border.parse(this, v);
					break;
				case "cellpadding":
					cellpadding = Cellpadding.parse(this, v);
					break;
				case "cellspacing":
					cellspacing = Cellspacing.parse(this, v);
					break;
				case "frame":
					frame = Frame.parse(this, v);
					break;
				case "rules":
					rules = Rules.parse(this, v);
					break;
				case "summary":
					summary = Summary.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

