package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.AREA;
import net.garmine.parser.html.attributes.Alt;
import net.garmine.parser.html.attributes.Coords;
import net.garmine.parser.html.attributes.Href;
import net.garmine.parser.html.attributes.Hreflang;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Nohref;
import net.garmine.parser.html.attributes.Rel;
import net.garmine.parser.html.attributes.Shape;
import net.garmine.parser.html.attributes.Target;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Area extends HtmlElement {
	public String alt;
	public String coords;
	public String href;
	public String hreflang;
	public String media;
	public boolean nohref;
	public Rel rel;
	public Shape shape;
	public String target;
	public String type;

	@Override
	public HtmlElementType getType() {
		return AREA;
	}

	public Area(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "alt":
					alt = Alt.parse(this, v);
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
				case "nohref":
					nohref = Nohref.parse(this, v);
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

