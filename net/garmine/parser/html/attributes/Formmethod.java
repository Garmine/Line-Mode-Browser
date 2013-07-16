package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public enum Formmethod {
	GET, POST;
	public static Formmethod parse(HtmlElement element, String str){
		if(str.equals("get")){
			return GET;
		}else{
			return POST;
		}
	}

	private Formmethod(){}
}

