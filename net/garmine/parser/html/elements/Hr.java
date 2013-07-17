package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.HR;
import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Noshade;
import net.garmine.parser.html.attributes.Size;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Hr extends HtmlElement {
	public String align;
	public boolean noshade;
	public int size;
	public int width;

	@Override
	public HtmlElementType getType() {
		return HR;
	}

	public Hr(HtmlElement parent, HtmlAttributeToken[] attrs){
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

