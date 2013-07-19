package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public enum Contenteditable {
	TRUE, FALSE, INHERIT;
	
	public static Contenteditable parse(HtmlElement element, String str){
		switch(str){
			case "true":
				return TRUE;

			default: 
			case "false":
				return FALSE;
				
			case "inherit":
				return INHERIT;
		}
	}
}

