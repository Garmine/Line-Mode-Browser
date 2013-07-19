package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.OL;
import net.garmine.parser.html.attributes.Compact;
import net.garmine.parser.html.attributes.Reversed;
import net.garmine.parser.html.attributes.Start;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Ol extends HtmlElement {
	public boolean compact;
	public boolean reversed;
	public int start;
	public String type;

	@Override
	public HtmlElementType getType() {
		return OL;
	}

	public Ol(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

