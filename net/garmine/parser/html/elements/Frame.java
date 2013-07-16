package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Frameborder;
import net.garmine.parser.html.attributes.Longdesc;
import net.garmine.parser.html.attributes.Marginheight;
import net.garmine.parser.html.attributes.Marginwidth;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Noresize;
import net.garmine.parser.html.attributes.Scrolling;
import net.garmine.parser.html.attributes.Src;

public class Frame extends Element {
	public  frameborder;
	public  longdesc;
	public  marginheight;
	public  marginwidth;
	public  name;
	public  noresize;
	public  scrolling;
	public  src;

	public Frame(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "frameborder":
					frameborder = Frameborder.parse(this, v);
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
				case "noresize":
					noresize = Noresize.parse(this, v);
					break;
				case "scrolling":
					scrolling = Scrolling.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
			}
		}
	}
}

