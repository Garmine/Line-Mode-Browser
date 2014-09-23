package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public enum Dir {
	LTR, RTL, AUTO;
	public static Dir parse(HtmlElement element, String str){
		switch(str){
			case "ltr":
				return LTR;
				
			case "rtl":
				return RTL;
				
			case "AUTO":
			default:
				return AUTO;
		}
	}

	private Dir(){}
}

