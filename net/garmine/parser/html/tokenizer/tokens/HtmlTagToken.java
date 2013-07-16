package net.garmine.parser.html.tokenizer.tokens;

public class HtmlTagToken extends HtmlToken {
	private final String name;
	private final HtmlAttributeToken[] attrs;
	private final boolean endTag;
	private final boolean selfClosing;
	
	public HtmlTagToken(String name, HtmlAttributeToken[] attributes, boolean isEndTag, boolean isSelfClosing){
		this.name = name;
		this.attrs = attributes;
		this.endTag = isEndTag;
		this.selfClosing = isSelfClosing;
	}
	
	@Override
	public String getName(){
		return name;
	}

	@Override
	public HtmlAttributeToken[] getAttributes(){
		return attrs;
	}

	@Override
	public boolean isEndTag(){
		return endTag;
	}
	
	@Override
	public boolean isSelfClosing(){
		return selfClosing;
	}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.TAG;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attrs == null) ? 0 : attrs.hashCode());
		result = prime * result + (endTag ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (selfClosing ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HtmlTagToken other = (HtmlTagToken) obj;
		if (endTag != other.endTag)
			return false;
		if (selfClosing != other.selfClosing)
			return false;
		if (attrs == null) {
			if (other.attrs != null)
				return false;
		} else if (!attrs.equals(other.attrs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
