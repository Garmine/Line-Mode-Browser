package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Frameborder {
	public static boolean parse(HtmlElement element, String str){
		return str.equals("1");
	}

	private Frameborder(){}
}

