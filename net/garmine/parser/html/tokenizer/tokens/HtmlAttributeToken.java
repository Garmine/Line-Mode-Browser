package net.garmine.parser.html.tokenizer.tokens;

/**
 * The Token which represents HTML attributes.
 * @author Garmine
 */
public class HtmlAttributeToken extends HtmlToken {
	private final String name;
	private final String value;
	
	/**
	 * Constructs a new HTML Attribute token.
	 * @param name - name of the attribute
	 * @param value - value of the attribute
	 */
	public HtmlAttributeToken(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getAttrName(){
		return name;
	}
	
	@Override
	public String getAttrValue(){
		return value;
	}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.ATTRIBUTE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		HtmlAttributeToken other = (HtmlAttributeToken) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getType().toString()+'{'+getName()+'}';
	}
}
