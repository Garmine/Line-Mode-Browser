package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public class Size {
	public static int parse(HtmlElement element, String str){
		try{
			int ret = Integer.parseInt(str);
			if(ret > 0){
				return ret;
			}else{
				return 3;
			}
		}catch(Exception e){
			return 3;
		}
	}

	private Size(){}
}

