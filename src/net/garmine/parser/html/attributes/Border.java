package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public class Border {
	public static boolean parse(HtmlElement element, String str){
		return str.equals("1");
	}

	private Border(){}
}

