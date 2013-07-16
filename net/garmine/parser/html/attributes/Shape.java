package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public enum Shape {
	DEFAULT, RECT, CIRCLE, POLY;
	
	public static Shape parse(HtmlElement element, String str){
		switch(str){
			case "rect":
				return RECT;
				
			case "circle":
				return CIRCLE;
				
			case "poly":
				return POLY;
				
			default:
			case "default":
				return DEFAULT;
		}
	}
}

