package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Pattern {
	public static java.util.regex.Pattern parse(HtmlElement element, String str){
		try{
			return java.util.regex.Pattern.compile(str);
		}catch(Exception e){
			return null;
		}
	}

	private Pattern(){}
}

