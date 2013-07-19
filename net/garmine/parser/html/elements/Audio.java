package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.AUDIO;
import net.garmine.parser.html.attributes.Autoplay;
import net.garmine.parser.html.attributes.Controls;
import net.garmine.parser.html.attributes.Loop;
import net.garmine.parser.html.attributes.Muted;
import net.garmine.parser.html.attributes.Preload;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Audio extends HtmlElement {
	public boolean autoplay;
	public boolean controls;
	public boolean loop;
	public boolean muted;
	public Preload preload;
	public String src;

	@Override
	public HtmlElementType getType() {
		return AUDIO;
	}

	public Audio(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "autoplay":
					autoplay = Autoplay.parse(this, v);
					break;
				case "controls":
					controls = Controls.parse(this, v);
					break;
				case "loop":
					loop = Loop.parse(this, v);
					break;
				case "muted":
					muted = Muted.parse(this, v);
					break;
				case "preload":
					preload = Preload.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
			}
		}
	}
}

