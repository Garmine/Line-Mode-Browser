package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;

public class Spellcheck {
	public static boolean parse(HtmlElement element, String str){
		return str.equals("true");
	}

	private Spellcheck(){}
}

