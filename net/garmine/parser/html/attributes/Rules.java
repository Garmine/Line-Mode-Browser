package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public enum Rules {
	NONE, GROUPS, ROWS, COLS, ALL;
	public static Rules parse(HtmlElement element, String str){
		switch(str){
			case "none":
				return NONE;
				
			case "groups":
				return GROUPS;
				
			case "rows":
				return ROWS;
				
			case "cols":
				return COLS;
				
			case "all":
				return ALL;
				
			default:
				return null;
		}
	}
}

