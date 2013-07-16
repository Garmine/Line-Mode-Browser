package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public enum Keytype {
	RSA, DSA, EC;
	public static Keytype parse(HtmlElement element, String str){
		switch(str){
			case "dsa":
				return DSA;
				
			case "ec":
				return EC;
				
			case "rsa":
			default:
				return RSA;
		}
	}
}

