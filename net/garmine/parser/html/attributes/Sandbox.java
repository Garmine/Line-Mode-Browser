package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

/*
""	Applies all restrictions below
allow-same-origin	Allows the iframe content to be treated as being from the same origin as the containing document
allow-top-navigation	Allows the iframe content to navigate (load) content from the containing document
allow-forms	Allows form submission
allow-scripts	Allows script execution
*/

public enum Sandbox {
	SAME_ORIGIN, TOP_NAVIGATION, FORMS, SCRIPTS, NONE;
	
	public static Sandbox parse(HtmlElement element, String str){
		switch(str){
			case "allow-same-origin":
				return SAME_ORIGIN;
				
			case "allow-top-navigation":
				return TOP_NAVIGATION;
				
			case "allow-forms":
				return FORMS;
				
			case "allow-scripts":
				return SCRIPTS;
				
			default:
			case "":
				return NONE;
		}
	}
}

