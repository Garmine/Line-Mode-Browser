package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public class High {
	public static float parse(HtmlElement element, String str){
		try{
			return Float.parseFloat(str);
		}catch(Exception e){
			return 0;
		}
	}

	private High(){}
}

