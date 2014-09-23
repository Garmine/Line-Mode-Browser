package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

public class JsonFloat extends JsonElement {

	private float value;
	
	/**
	 * Constructs a new JSONFloat with given value.
	 * @param value - initial value.
	 */
	public JsonFloat(float value){
		set(value);
	}
	
	@Override
	public JsonElement set(float value){
		this.value = value;
		return this;
	}
	
	@Override
	public float getFloat(){
		return value;
	}
	
	@Override
	public JsonType getType() {
		return FLOAT;
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
		result = prime * result + Float.floatToIntBits(value);
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
		
		JsonFloat other = (JsonFloat) obj;
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
		return Float.toString(value);
	}

	@Override
	public Object clone(){
		return new JsonFloat(value);
	}
}
