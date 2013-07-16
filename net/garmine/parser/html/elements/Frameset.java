package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Cols;
import net.garmine.parser.html.attributes.Rows;

public class Frameset extends Element {
	public  cols;
	public  rows;

	public Frameset(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
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

