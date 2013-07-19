package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.IFRAME;
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
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Iframe extends HtmlElement {
	public String align;
	public boolean frameborder;
	public int height;
	public String longdesc;
	public int marginheight;
	public int marginwidth;
	public String name;
	public Sandbox sandbox;
	public Scrolling scrolling;
	public boolean seamless;
	public String src;
	public String srcdoc;
	public int width;

	@Override
	public HtmlElementType getType() {
		return IFRAME;
	}

	public Iframe(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
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

