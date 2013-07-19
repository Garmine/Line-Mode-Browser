package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.IMG;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Alt;
import net.garmine.parser.html.attributes.Border;
import net.garmine.parser.html.attributes.Crossorigin;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Hspace;
import net.garmine.parser.html.attributes.Ismap;
import net.garmine.parser.html.attributes.Longdesc;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Usemap;
import net.garmine.parser.html.attributes.Vspace;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Img extends HtmlElement {
	public String align;
	public String alt;
	public boolean border;
	public Crossorigin crossorigin;
	public int height;
	public int hspace;
	public boolean ismap;
	public String longdesc;
	public String src;
	public String usemap;
	public int vspace;
	public int width;

	@Override
	public HtmlElementType getType() {
		return IMG;
	}

	public Img(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "alt":
					alt = Alt.parse(this, v);
					break;
				case "border":
					border = Border.parse(this, v);
					break;
				case "crossorigin":
					crossorigin = Crossorigin.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "hspace":
					hspace = Hspace.parse(this, v);
					break;
				case "ismap":
					ismap = Ismap.parse(this, v);
					break;
				case "longdesc":
					longdesc = Longdesc.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "usemap":
					usemap = Usemap.parse(this, v);
					break;
				case "vspace":
					vspace = Vspace.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

