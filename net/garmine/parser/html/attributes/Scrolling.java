package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public enum Scrolling {
	YES, NO, AUTO;
	
	public static Scrolling parse(HtmlElement element, String str){
		switch(str){
			case "yes":
				return YES;
				
			case "no":
				return NO;
				
			default:
			case "auto":
				return AUTO;
		}
	}
}

