package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Draggable {
	public static boolean parse(HtmlElement element, String str){
		return str.equals("true");
	}

	private Draggable(){}
}

