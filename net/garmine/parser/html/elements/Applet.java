package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Code;
import net.garmine.parser.html.attributes.Object;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Alt;
import net.garmine.parser.html.attributes.Archive;
import net.garmine.parser.html.attributes.Codebase;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Hspace;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Vspace;
import net.garmine.parser.html.attributes.Width;

public class Applet extends Element {
	public  code;
	public  object;
	public  align;
	public  alt;
	public  archive;
	public  codebase;
	public  height;
	public  hspace;
	public  name;
	public  vspace;
	public  width;

	public Applet(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
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

