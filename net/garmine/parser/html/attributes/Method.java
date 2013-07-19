package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public enum Method {
	GET, POST;
	public static Method parse(HtmlElement element, String str){
		if(str.equals("get")){
			return GET;
		}else{
			return POST;
		}
	}

	private Method(){}
}