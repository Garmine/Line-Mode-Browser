package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.PROGRESS;
import net.garmine.parser.html.attributes.Max;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Progress extends HtmlElement {
	public int max;
	public String value;

	@Override
	public HtmlElementType getType() {
		return PROGRESS;
	}

	public Progress(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "max":
					max = Max.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
			}
		}
	}
}

