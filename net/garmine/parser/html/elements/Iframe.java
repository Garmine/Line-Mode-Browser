package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Frameborder;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Longdesc;
import net.garmine.parser.html.attributes.Marginheight;
import net.garmine.parser.html.attributes.Marginwidth;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Sandbox;
import net.garmine.parser.html.attributes.Scrolling;
import net.garmine.parser.html.attributes.Seamless;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Srcdoc;
import net.garmine.parser.html.attributes.Width;

public class Iframe extends Element {
	public  align;
	public  frameborder;
	public  height;
	public  longdesc;
	public  marginheight;
	public  marginwidth;
	public  name;
	public  sandbox;
	public  scrolling;
	public  seamless;
	public  src;
	public  srcdoc;
	public  width;

	public Iframe(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "frameborder":
					frameborder = Frameborder.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "longdesc":
					longdesc = Longdesc.parse(this, v);
					break;
				case "marginheight":
					marginheight = Marginheight.parse(this, v);
					break;
				case "marginwidth":
					marginwidth = Marginwidth.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "sandbox":
					sandbox = Sandbox.parse(this, v);
					break;
				case "scrolling":
					scrolling = Scrolling.parse(this, v);
					break;
				case "seamless":
					seamless = Seamless.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "srcdoc":
					srcdoc = Srcdoc.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

