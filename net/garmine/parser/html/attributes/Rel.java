package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public enum Rel {
	ALTERNATE, AUTHOR, BOOKMARK, HELP, LICENSE, NEXT, NOFOLLOW, NOREFERRER, PREFETCH, PREV, SEARCH, TAG;
	public static Rel parse(HtmlElement element, String str){
		try{
			return Rel.valueOf(str.toUpperCase());
		}catch(Exception e){
			return null;
		}
	}
}

