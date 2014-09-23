package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public class Autocomplete {
	public static boolean parse(HtmlElement element, String str){
		return str.equals("on");
	}

	private Autocomplete(){}
}

