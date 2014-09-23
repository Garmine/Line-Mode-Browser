package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;
//top|middle|bottom|baseline
public enum Valign {
	TOP, MIDDLE, BOTTOM, BASELINE;
	public static Valign parse(HtmlElement element, String str){
		switch(str){
			case "top":
				return TOP;
	
			case "middle":
				return MIDDLE;
				
			case "bottom":
				return BOTTOM;
				
			case "baseline":
			default:
				return BASELINE;
		}
	}

	private Valign(){}
}

