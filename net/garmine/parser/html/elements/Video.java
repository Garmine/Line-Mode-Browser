package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Autoplay;
import net.garmine.parser.html.attributes.Controls;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Loop;
import net.garmine.parser.html.attributes.Muted;
import net.garmine.parser.html.attributes.Poster;
import net.garmine.parser.html.attributes.Preload;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Width;

public class Video extends Element {
	public  autoplay;
	public  controls;
	public  height;
	public  loop;
	public  muted;
	public  poster;
	public  preload;
	public  src;
	public  width;

	public Video(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "autoplay":
					autoplay = Autoplay.parse(this, v);
					break;
				case "controls":
					controls = Controls.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "loop":
					loop = Loop.parse(this, v);
					break;
				case "muted":
					muted = Muted.parse(this, v);
					break;
				case "poster":
					poster = Poster.parse(this, v);
					break;
				case "preload":
					preload = Preload.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

