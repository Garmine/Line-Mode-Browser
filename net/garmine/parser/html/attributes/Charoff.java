package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Charoff {
	public static int parse(HtmlElement element, String str){
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			return 0;
		}
	}

	private Charoff(){}
}

