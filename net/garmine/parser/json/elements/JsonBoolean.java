package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

public class JsonBoolean extends JsonElement {

	private boolean value;
	
	/**
	 * Constructs a new JSONBoolean with given value.
	 * @param value - initial value.
	 */
	public JsonBoolean(boolean value){
		set(value);
	}
	
	@Override
	public JsonElement set(boolean value) {
		this.value = value;
		return this;
	}
	
	@Override 
	public boolean getBoolean() {
		return value;
	}
	
	@Override
	public JsonType getType() {
		return BOOLEAN;
	}

	@Override
	public Object get() {
		return value;
	}

	@Override
	public boolean getJSBoolean() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		
		JsonBoolean other = (JsonBoolean) obj;
		if (other.hasComments() ^ hasComments())
			return false;
		if (value != other.value)
			return false;
		
		if (hasComments() && !getComments().equals(other.getComments()))
			return false;
		
		return true;
	}

	@Override
	protected String makeString(String t, boolean structured) {
		return Boolean.toString(value);
	}

	@Override
	public Object clone(){
		return new JsonBoolean(value);
	}
}
