package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.APPLET;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Alt;
import net.garmine.parser.html.attributes.Archive;
import net.garmine.parser.html.attributes.Code;
import net.garmine.parser.html.attributes.Codebase;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Hspace;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Object;
import net.garmine.parser.html.attributes.Vspace;
import net.garmine.parser.html.attributes.Width;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Applet extends HtmlElement {
	public String code;
	public String object;
	public String align;
	public String alt;
	public String archive;
	public String codebase;
	public int height;
	public int hspace;
	public String name;
	public int vspace;
	public int width;

	@Override
	public HtmlElementType getType() {
		return APPLET;
	}

	public Applet(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "code":
					code = Code.parse(this, v);
					break;
				case "object":
					object = Object.parse(this, v);
					break;
				case "align":
					align = Align.parse(this, v);
					break;
				case "alt":
					alt = Alt.parse(this, v);
					break;
				case "archive":
					archive = Archive.parse(this, v);
					break;
				case "codebase":
					codebase = Codebase.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "hspace":
					hspace = Hspace.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
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

