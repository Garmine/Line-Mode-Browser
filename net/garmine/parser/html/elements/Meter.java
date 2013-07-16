package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.High;
import net.garmine.parser.html.attributes.Low;
import net.garmine.parser.html.attributes.Max;
import net.garmine.parser.html.attributes.Min;
import net.garmine.parser.html.attributes.Optimum;
import net.garmine.parser.html.attributes.Value;

public class Meter extends HtmlElement {
	public String form;
	public float high;
	public int low;
	public int max;
	public int min;
	public float optimum;
	public String value;

	public Meter(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
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

