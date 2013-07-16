package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public enum Wrap {
	SOFT, HARD;
	
	public static Wrap parse(HtmlElement element, String str){
		if(str.equals("hard")){
			return HARD;
		}else{
			return SOFT;
		}
	}
}

