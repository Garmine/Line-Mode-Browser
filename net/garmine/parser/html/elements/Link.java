package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Charset;
import net.garmine.parser.html.attributes.Href;
import net.garmine.parser.html.attributes.Hreflang;
import net.garmine.parser.html.attributes.Media;
import net.garmine.parser.html.attributes.Rel;
import net.garmine.parser.html.attributes.Rev;
import net.garmine.parser.html.attributes.Sizes;
import net.garmine.parser.html.attributes.Target;
import net.garmine.parser.html.attributes.Type;

public class Link extends Element {
	public  charset;
	public  href;
	public  hreflang;
	public  media;
	public  rel;
	public  rev;
	public  sizes;
	public  target;
	public  type;

	public Link(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "charset":
					charset = Charset.parse(this, v);
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
				case "rel":
					rel = Rel.parse(this, v);
					break;
				case "rev":
					rev = Rev.parse(this, v);
					break;
				case "sizes":
					sizes = Sizes.parse(this, v);
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

