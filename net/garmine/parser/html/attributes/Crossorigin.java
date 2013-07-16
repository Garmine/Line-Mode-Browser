package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public enum Crossorigin {
	ANONYMOUS, USE_CREDENTIALS;
	public static Crossorigin parse(HtmlElement element, String str){
		if(str.equals("use-credentials")){
			return USE_CREDENTIALS;
		}else{
			return ANONYMOUS;
		}
	}
}

