package net.garmine.parser.html.attributes;

import net.garmine.parser.html.HtmlElement;

public enum Kind {
	CAPTIONS, CHAPTERS, DESCRIPTIONS, METADATA, SUBTITLES;
	
	public static Kind parse(HtmlElement element, String str){
		switch(str){
			case "captions":
				return CAPTIONS;
				
			case "chapters":
				return CHAPTERS;
				
			case "descriptions":
				return DESCRIPTIONS;
				
			case "metadata":
				return METADATA;
				
			case "subtitles":
				return SUBTITLES;
			
			default:
				return null;
		}
	}
}

