package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

public class JsonString extends JsonElement {

	private String value;
	
	/**
	 * Constructs a new JSONString with given value.
	 * @param value - initial value.
	 */
	public JsonString(String value){
		set(value);
	}
	
	@Override
	public JsonElement set(String value){
		checkNull(value);
		this.value = value;
		return this;
	}
	
	@Override
	public String getString(){
		return value;
	}
	
	@Override
	public JsonType getType() {
		return STRING;
	}

	@Override
	public Object get() {
		return value;
	}

	@Override
	public boolean getJSBoolean() {
		return (value.equals("")) ? false : true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((hasComments()) ? getComments().hashCode() : 0);
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
		
		JsonString other = (JsonString) obj;
		if (other.hasComments() ^ hasComments())
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		
		if (hasComments() && !getComments().equals(other.getComments()))
			return false;
		
		return true;
	}

	@Override
	protected String makeString(String t, boolean structured) {
		return escape(value);
	}

	@Override
	public Object clone(){
		return new JsonString(new String(value));
	}
}
