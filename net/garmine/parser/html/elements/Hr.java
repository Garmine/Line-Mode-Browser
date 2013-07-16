package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Noshade;
import net.garmine.parser.html.attributes.Size;
import net.garmine.parser.html.attributes.Width;

public class Hr extends Element {
	public  align;
	public  noshade;
	public  size;
	public  width;

	public Hr(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "noshade":
					noshade = Noshade.parse(this, v);
					break;
				case "size":
					size = Size.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

