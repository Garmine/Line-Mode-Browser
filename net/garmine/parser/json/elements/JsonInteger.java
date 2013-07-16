package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

public class JsonInteger extends JsonElement{

	private int value;
	
	/**
	 * Constructs a new JSONInteger with given value.
	 * @param value - initial value.
	 */
	public JsonInteger(int value){
		set(value);
	}
	
	@Override
	public JsonElement set(int value){
		this.value = value;
		return this;
	}
	
	@Override
	public int getInteger(){
		return value;
	}
	
	@Override
	public JsonType getType() {
		return INT;
	}

	@Override
	public Object get() {
		return value;
	}

	@Override
	public boolean getJSBoolean() {
		return (value == 0) ? false : true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		
		JsonInteger other = (JsonInteger) obj;
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
		return Integer.toString(value);
	}

	@Override
	public Object clone(){
		return new JsonInteger(value);
	}
}
