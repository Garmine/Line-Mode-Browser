package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Compact;
import net.garmine.parser.html.attributes.Reversed;
import net.garmine.parser.html.attributes.Start;
import net.garmine.parser.html.attributes.Type;

public class Ol extends HtmlElement {
	public boolean compact;
	public boolean reversed;
	public int start;
	public String type;

	public Ol(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "compact":
					compact = Compact.parse(this, v);
					break;
				case "reversed":
					reversed = Reversed.parse(this, v);
					break;
				case "start":
					start = Start.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

