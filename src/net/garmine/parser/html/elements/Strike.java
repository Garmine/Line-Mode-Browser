package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.STRIKE;
import net.garmine.parser.html.attributes.Class;
import net.garmine.parser.html.attributes.Dir;
import net.garmine.parser.html.attributes.Id;
import net.garmine.parser.html.attributes.Lang;
import net.garmine.parser.html.attributes.Style;
import net.garmine.parser.html.attributes.Title;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Strike extends HtmlElement {
	public String clazz;
	public Dir dir;
	public String id;
	public String lang;
	public String style;
	public String title;

	@Override
	public HtmlElementType getType() {
		return STRIKE;
	}

	public Strike(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

