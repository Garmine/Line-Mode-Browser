package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public enum Preload {
	NONE, METADATA, AUTO;
	public static Preload parse(HtmlElement element, String str){
		switch(str){
			case "metadata":
				return METADATA;
				
			case "none":
				return NONE;
				
			case "auto":
			default:
				return AUTO;
		}
	}
}

