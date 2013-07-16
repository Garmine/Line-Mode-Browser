package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

import java.util.ArrayList;

public class JsonArray extends JsonElement {

	private ArrayList<JsonElement> value;
	
	/**
	 * Constructs a new JSONBoolean with an empty ArrayList.
	 */
	public JsonArray(){
		value = new ArrayList<JsonElement>();
	}
	
	/**
	 * Constructs a new JSONBoolean with given value.
	 * @param value - initial value.
	 */
	public JsonArray(ArrayList<JsonElement> value){
		set(value);
	}
	
	/**
	 * Constructs a new JSONBoolean with given elements.
	 * @param elements - initial elements.
	 */
	public JsonArray(JsonElement... elements){
		value = new ArrayList<JsonElement>();
		addAll(elements);
	}
	
	@Override
	public JsonElement set(ArrayList<JsonElement> value){
		checkNull(value);
		this.value = value;
		return this;
	}
	
	@Override
	public JsonElement add(JsonElement element){
		value.add(element);
		return this;
	}
	
	@Override
	public JsonElement addAll(JsonElement... elements){
		for(JsonElement element : elements){
			add(element);
		}
		return this;
	}
	
	@Override
	public JsonElement addAll(ArrayList<JsonElement> elements){
		checkNull(elements);
		value.addAll(elements);
		return this;
	}
	
	@Override
	public ArrayList<JsonElement> getArray(){
		return value;
	}
	
	@Override
	public JsonElement get(int i){
		return value.get(i);
	}
	
	@Override
	public boolean contains(JsonElement element){
		return value.contains(element);
	}
	
	@Override
	public JsonType getType() {
		return ARRAY;
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
	public int size() {
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
		
		JsonArray other = (JsonArray) obj;
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
        StringBuilder arrsb = new StringBuilder();
        
        //trick to have commas just where they're needed
        char c = '[';
		for(JsonElement element : value){
			arrsb.append(c+n+tt);
			arrsb.append( (element==null) ? ("null") : (element.makeString(tt, structured)) );
			c = ',';
		}
		
		return arrsb.append(n+t+"]").toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object clone(){
		return new JsonArray((ArrayList<JsonElement>)value.clone());
	}
}
