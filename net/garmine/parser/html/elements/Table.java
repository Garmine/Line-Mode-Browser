package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Center;
import net.garmine.parser.html.attributes.Bgcolor;
import net.garmine.parser.html.attributes.Border;
import net.garmine.parser.html.attributes.Cellpadding;
import net.garmine.parser.html.attributes.Cellspacing;
import net.garmine.parser.html.attributes.Frame;
import net.garmine.parser.html.attributes.Rules;
import net.garmine.parser.html.attributes.Summary;
import net.garmine.parser.html.attributes.Width;

public class Table extends Element {
	public  align;
	public  center;
	public  bgcolor;
	public  border;
	public  cellpadding;
	public  cellspacing;
	public  frame;
	public  rules;
	public  summary;
	public  width;

	public Table(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "center":
					center = Center.parse(this, v);
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

