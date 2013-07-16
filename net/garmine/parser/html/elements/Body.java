package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Alink;
import net.garmine.parser.html.attributes.Background;
import net.garmine.parser.html.attributes.Bgcolor;
import net.garmine.parser.html.attributes.Link;
import net.garmine.parser.html.attributes.Text;
import net.garmine.parser.html.attributes.Vlink;

public class Body extends Element {
	public  alink;
	public  background;
	public  bgcolor;
	public  link;
	public  text;
	public  vlink;

	public Body(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "alink":
					alink = Alink.parse(this, v);
					break;
				case "background":
					background = Background.parse(this, v);
					break;
				case "bgcolor":
					bgcolor = Bgcolor.parse(this, v);
					break;
				case "link":
					link = Link.parse(this, v);
					break;
				case "text":
					text = Text.parse(this, v);
					break;
				case "vlink":
					vlink = Vlink.parse(this, v);
					break;
			}
		}
	}
}

