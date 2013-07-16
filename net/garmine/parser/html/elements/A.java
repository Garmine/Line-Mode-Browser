package net.garmine.parser.html.elements;

import net.garmine.parser.html.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Charset;
import net.garmine.parser.html.attributes.Coords;
import net.garmine.parser.html.attributes.Href;
import net.garmine.parser.html.attributes.Hreflang;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Rel;
import net.garmine.parser.html.attributes.Shape;
import net.garmine.parser.html.attributes.Target;
import net.garmine.parser.html.attributes.Type;

public class A extends HtmlElement {
	public String charset;
	public String coords;
	public String href;
	public String hreflang;
	public String media;
	public String name;
	public Rel rel;
	public Shape shape;
	public String target;
	public String type;

	public A(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "charset":
					charset = Charset.parse(this, v);
					break;
				case "coords":
					coords = Coords.parse(this, v);
					break;
				case "href":
					href = Href.parse(this, v);
					break;
				case "hreflang":
					hreflang = Hreflang.parse(this, v);
					break;
				case "media":
					media = Media.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "rel":
					rel = Rel.parse(this, v);
					break;
				case "shape":
					shape = Shape.parse(this, v);
					break;
				case "target":
					target = Target.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

