package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.METER;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.High;
import net.garmine.parser.html.attributes.Low;
import net.garmine.parser.html.attributes.Max;
import net.garmine.parser.html.attributes.Min;
import net.garmine.parser.html.attributes.Optimum;
import net.garmine.parser.html.attributes.Value;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Meter extends HtmlElement {
	public String form;
	public float high;
	public int low;
	public int max;
	public int min;
	public float optimum;
	public String value;

	@Override
	public HtmlElementType getType() {
		return METER;
	}

	public Meter(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "form":
					form = Form.parse(this, v);
					break;
				case "high":
					high = High.parse(this, v);
					break;
				case "low":
					low = Low.parse(this, v);
					break;
				case "max":
					max = Max.parse(this, v);
					break;
				case "min":
					min = Min.parse(this, v);
					break;
				case "optimum":
					optimum = Optimum.parse(this, v);
					break;
				case "value":
					value = Value.parse(this, v);
					break;
			}
		}
	}
}

