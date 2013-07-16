package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Contenteditable {
	public static boolean parse(HtmlElement element, String str){
		switch(str){
			case "true":
				return true;
				
			case "false":
				return false;
				
			case "inherit":
				if(element.parent != null){
					return element.parent.contenteditable;
				}else{
					return false;
				}
				
			default: 
				return false;
		}
	}

	private Contenteditable(){}
}

