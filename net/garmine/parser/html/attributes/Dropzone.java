package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public enum Dropzone {
	NONE, COPY, MOVE, LINK;
	public static Dropzone parse(HtmlElement element, String str){
		switch(str){
			case "copy":
				return COPY;
				
			case "move":
				return MOVE;
				
			case "link":
				return LINK;
				
			case "none":
			default:
				return NONE;
		}
	}

	private Dropzone(){}
}

