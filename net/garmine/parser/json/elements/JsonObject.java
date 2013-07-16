package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

import java.util.HashMap;
import java.util.Map;

public class JsonObject extends JsonElement {

	private HashMap<String, JsonElement> value;
	
	/**
	 * Constructs a new JSONBoolean with an empty HashMap.
	 */
	public JsonObject(){
		value = new HashMap<String, JsonElement>();
	}
	
	/**
	 * Constructs a new JSONBoolean with given value.
	 * @param value - initial value.
	 */
	public JsonObject(HashMap<String, JsonElement> value){
		set(value);
	}
	
	@Override
	public JsonElement set(HashMap<String, JsonElement> value){
		checkNull(value);
		this.value = value;
		return this;
	}
	
	@Override
	public JsonElement add(String key, JsonElement element){
		value.put(key, element);
		return this;
	}
	
	@Override
	public HashMap<String, JsonElement> getObject(){
		return value;
	}
	
	@Override
	public JsonElement get(String key){
		return value.get(key);
	}
	
	@Override
	public boolean contains(JsonElement element){
		return value.containsValue(element);
	}
	
	@Override
	public boolean contains(String key){
		return value.containsKey(key);
	}
	
	@Override
	public JsonType getType() {
		return OBJECT;
	}

	@Override
	public Object get() {
		return value;
	}

	@Override
	public boolean getJSBoolean() {
		return true;
	}
	
	@Override
	public int size(){
		return value.size();
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
		
		JsonObject other = (JsonObject) obj;
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
		String n = "";	//new line
		String tt = "";	//tabulators
		
		//create new stuff, if structured
		if(structured){
			n = "\n";
			tt = t+"\t";
		}
		
		//temp storage
        StringBuilder objsb = new StringBuilder();
        
        //trick to have commas just where they're needed
        char c = '{';
		for(Map.Entry<String, JsonElement> entry : value.entrySet()){
			JsonElement value = entry.getValue();
			objsb.append(c+n+tt+escape(entry.getKey())+":");
			objsb.append( (value==null) ? ("null") : (value.makeString(tt, structured)) );
			c = ',';
		}
		
		return objsb.append(n+t+"}").toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object clone(){
		return new JsonObject((HashMap<String, JsonElement>) value.clone());
	}
}
