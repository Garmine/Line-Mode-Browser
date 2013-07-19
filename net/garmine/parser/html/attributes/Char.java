package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public class Char {
	public static char parse(HtmlElement element, String str){
		if(str.length() == 0 || str.length() > 1){
			return '\0';
		}else{
			return str.charAt(0);
		}
	}

	private Char(){}
}

