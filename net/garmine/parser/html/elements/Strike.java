package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Class;
import net.garmine.parser.html.attributes.Dir;
import net.garmine.parser.html.attributes.Id;
import net.garmine.parser.html.attributes.Lang;
import net.garmine.parser.html.attributes.Style;
import net.garmine.parser.html.attributes.Title;

public class Strike extends HtmlElement {
	public String clazz;
	public Dir dir;
	public String id;
	public String lang;
	public String style;
	public String title;

	public Strike(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "class":
					clazz = Class.parse(this, v);
					break;
				case "dir":
					dir = Dir.parse(this, v);
					break;
				case "id":
					id = Id.parse(this, v);
					break;
				case "lang":
					lang = Lang.parse(this, v);
					break;
				case "style":
					style = Style.parse(this, v);
					break;
				case "title":
					title = Title.parse(this, v);
					break;
			}
		}
	}
}

