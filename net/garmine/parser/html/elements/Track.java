package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.TRACK;
import net.garmine.parser.html.attributes.Default;
import net.garmine.parser.html.attributes.Kind;
import net.garmine.parser.html.attributes.Label;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Srclang;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Track extends HtmlElement {
	public boolean defaultt;
	public Kind kind;
	public String label;
	public String src;
	public String srclang;

	@Override
	public HtmlElementType getType() {
		return TRACK;
	}

	public Track(HtmlElement parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "default":
					defaultt = Default.parse(this, v);
					break;
				case "kind":
					kind = Kind.parse(this, v);
					break;
				case "label":
					label = Label.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "srclang":
					srclang = Srclang.parse(this, v);
					break;
			}
		}
	}
}

