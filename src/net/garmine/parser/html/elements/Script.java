package net.garmine.parser.html.elements;

import static net.garmine.parser.html.elements.HtmlElementType.SCRIPT;
import net.garmine.parser.html.attributes.Async;
import net.garmine.parser.html.attributes.Charset;
import net.garmine.parser.html.attributes.Defer;
import net.garmine.parser.html.attributes.Src;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

public class Script extends HtmlElement {
	public boolean async;
	public String charset;
	public boolean defer;
	public String src;
	public String type;

	@Override
	public HtmlElementType getType() {
		return SCRIPT;
	}

	public Script(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "async":
					async = Async.parse(this, v);
					break;
				case "charset":
					charset = Charset.parse(this, v);
					break;
				case "defer":
					defer = Defer.parse(this, v);
					break;
				case "src":
					src = Src.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
			}
		}
	}
}

