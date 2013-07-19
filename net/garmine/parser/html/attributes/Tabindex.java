package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

public class Tabindex {
	public static int parse(HtmlElement element, String str){
		try{
			int ret = Integer.parseInt(str);
			if(ret > 0){
				return ret;
			}else{
				return -1;
			}
		}catch(Exception e){
			return -1;
		}
	}

	private Tabindex(){}
}

