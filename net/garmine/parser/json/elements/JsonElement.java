package net.garmine.parser.json.elements;

import static net.garmine.parser.json.elements.JsonType.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import net.garmine.parser.json.JsonParser;


/**
 * A type safe Object which encapsulates Java Objects as JSONs. 
 * Implemented {@link #equals(Object)}, {@link #hashCode()} and 
 * {@link #clone()} for full support, {@link #toString()} generates 
 * a valid JSON from encapsulated Objects.
 * @author Garmine
 * @see JsonParser
 */
public abstract class JsonElement implements Cloneable {
	
	//==========================================================\\
	//~~~>Type stuff											||
	//==========================================================//
	
	/*private NoSuchElementException typeErr(){
		return new NoSuchElementException("ERROR: Invalid type! Didn't expect: "+getType());
	}*/
	
	private NoSuchElementException typeErr(JsonType expected){
		return new NoSuchElementException("ERROR: Invalid type! Expected: "+expected+"; It's: "+getType());
	}
	
	protected NullPointerException nullErr(){
		return new NullPointerException("ERROR: Value mustn't be null!");
	}
	
	protected void checkNull(Object value){
		if (value == null) throw nullErr();
	}
	
	/**
	 * Checks if the JSON is of the supplied type
	 * @param type - type to check for
	 * @return True if this JSON is the specified type
	 */
	public boolean is(JsonType type){
		return getType() == type;
	}
	
	/**
	 * Get's the JSON's type
	 * @return The JSON's type
	 */
	public abstract JsonType getType();
	
	//==========================================================\\
	//~~~>Value manipulation									||
	//==========================================================//
	
	/**
	 * Changes the underlying boolean
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONBoolean
	 */
	public JsonElement set(boolean value){
		throw typeErr(BOOLEAN);
	}
	
	/**
	 * Changes the underlying int
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONInteger
	 */
	public JsonElement set(int value){
		throw typeErr(INT);
	}
	
	/**
	 * Changes the underlying float
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONFloat
	 */
	public JsonElement set(float value){
		throw typeErr(FLOAT);
	}
	
	/**
	 * Changes the underlying String
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONString
	 */
	public JsonElement set(String value){
		throw typeErr(STRING);
	}
	
	/**
	 * Changes the underlying ArrayList
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public JsonElement set(ArrayList<JsonElement> value){
		throw typeErr(ARRAY);
	}
	
	/**
	 * Changes the underlying HashMap
	 * @param value - value to set, mustn't be null!
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONObject
	 */
	public JsonElement set(HashMap<String, JsonElement> value){
		throw typeErr(OBJECT);
	}
	
	/**
	 * Adds an element to the JSONArray
	 * @param element - value to add
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public JsonElement add(JsonElement element){
		throw typeErr(ARRAY);
	}
	
	/**
	 * Adds all elements specified to the JSONArray
	 * @param elements - values to add
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public JsonElement addAll(JsonElement... elements){
		throw typeErr(ARRAY);
	}
	
	/**
	 * Adds all elements of the ArrayList to the JSONArray
	 * @param elements - ArrayList to add
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public JsonElement addAll(ArrayList<JsonElement> elements){
		throw typeErr(ARRAY);
	}
	
	/**
	 * Adds a key, value pair to the JSONObject
	 * @param key - key to set
	 * @param element - value to set
	 * @return This
	 * @throws NoSuchElementException if this isn't a JSONObject
	 */
	public JsonElement add(String key, JsonElement element){
		throw typeErr(OBJECT);
	}
	
	//==========================================================\\
	//~~~>Value retrieval										||
	//==========================================================//
	
	/**
	 * Raw getter
	 * @return The JSON's value
	 */
	public abstract Object get();
	
	/**
	 * Getter for the JSONBoolean
	 * @return The JSON's value
	 * @throws NoSuchElementException if this isn't a JSONBoolean
	 */
	public boolean getBoolean(){
		throw typeErr(BOOLEAN);
	}
	
	/**
	 * Parses the internal value according to the rules of JavaScript.<br><br>
	 * If the Boolean object has no initial value, or if it's value is one of the following:
	 * 0, null, false, "" (i.e. an empty String), NaN: the object is set to <b>false</b>.<br>
	 * For any other value it is set to <b>true</b> (even with the string "false")!
	 * @return A boolean parsed according to the rules of JavaScript
	 * @throws NoSuchElementException if this JSON is a JSONError
	 */
	public abstract boolean getJSBoolean();
	
	/**
	 * Getter for the JSONInteger
	 * @return The JSON's value
	 * @throws NoSuchElementException if this isn't a JSONInteger
	 */
	public int getInteger(){
		throw typeErr(INT);
	}
	
	/**
	 * Getter for the JSONFloat
	 * @return The JSON's value
	 * @throws NoSuchElementException if this isn't a JSONFloat
	 */
	public float getFloat(){
		throw typeErr(FLOAT);
	}
	
	/**
	 * Getter for the JSONString
	 * @return The JSON's value
	 * @throws NoSuchElementException if this isn't a JSONString
	 */
	public String getString(){
		throw typeErr(STRING);
	}
	
	/**
	 * Getter for the JSONArray, use this for maximal access to the underlying ArrayList
	 * @return The underlying ArrayList
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public ArrayList<JsonElement> getArray(){
		throw typeErr(ARRAY);
	}

	/**
	 * Getter for the JSONObject, use this for maximal access to the underlying HashMap
	 * @return The underlying HashMap
	 * @throws NoSuchElementException if this isn't a JSONObject
	 */
	public HashMap<String, JsonElement> getObject(){
		throw typeErr(OBJECT);
	}
	
	/**
	 * The JSONArray's get() method
	 * @param i - index
	 * @return The JSONArray's ith element
	 * @throws NoSuchElementException if this isn't a JSONArray
	 */
	public JsonElement get(int i){
		throw typeErr(ARRAY);
	}
	
	/**
	 * The JSONObject's get() method
	 * @param key - key
	 * @return The JSONObject's associated value
	 * @throws NoSuchElementException if this isn't a JSONObject
	 */
	public JsonElement get(String key){
		throw typeErr(OBJECT);
	}
	
	/**
	 * Checks if the element is contained
	 * @param element - element to check
	 * @return True, if the JSON is a JSONObject or JSONArray and contains that element
	 */
	public boolean contains(JsonElement element){
		return false;
	}
	
	/**
	 * Checks if key is contained
	 * @param key - key to check
	 * @return True, if the JSON is a JSONObject and contains that key
	 */
	public boolean contains(String key){
		return false;
	}
	
	/**
	 * Get's the underlying JSONArray's/JSONObject's size.
	 * @return The size, or -1 if this isn't an JSONArray/JSONObject.
	 */
	public int size(){
		return -1;
	}
	
	//==========================================================\\
	//~~~>Comments												||
	//==========================================================//
	
	private ArrayList<String> comments = null;
	
	/**
	 * Appends a comment to this JSON object
	 * @param comment - comment to append
	 * @return this
	 */
	public JsonElement comment(String comment){
		if(comments == null){
			comments = new ArrayList<String>();
		}
		comments.add(comment);
		return this;
	}
	
	/**
	 * Check if this JSON has comments
	 * @return True if this JSON has comments
	 */
	public boolean hasComments(){
		return comments != null;
	}
	
	/**
	 * Getter for the JSON's comments
	 * @return The comments inside, <b>null</b> if none
	 */
	public ArrayList<String> getComments(){
		return comments;
	}
	
	//==========================================================\\
	//~~~>Etc													||
	//==========================================================//
	
	@Override public abstract int hashCode();
	@Override public abstract boolean equals(Object obj);
	protected abstract String makeString(String t, boolean structured);
	@Override public abstract Object clone();
	
	/**
	 * Generates a standard JSON from encapsulated Object(s).
	 * @return The JSON String as specified at <a href="http://www.ietf.org/rfc/rfc4627.txt">IETF</a>.
	 */
	@Override
	public String toString(){
		return makeString("", false);
	}
	
	/**
	 * Pretty formats a String. I.e. adds newlines and tabulators.
	 * @return The JSON in a pretty, human-readable format.
	 */
	public String toPrettyString(){
		return makeString("", true);
	}
	
	/**
	 * Escapes a String (i.e. replaces "\n" with "\\n" etc.)
	 * @param s - String to escape
	 * @return Escaped String
	 */
	protected String escape(String s){
		StringBuilder ret = new StringBuilder().append("\"");
		
		for(char c:s.toCharArray()){
			switch(c){
				
				case '\"':
					ret.append("\\\"");
					break;
					
				case '\t':
					ret.append("\\t");
					break;
					
				case '\r':
					ret.append("\\r");
					break;
					
				case '\n':
					ret.append("\\n");
					break;
					
				case '\f':
					ret.append("\\f");
					break;
					
				case '\b':
					ret.append("\\b");
					break;
					
				case '\\':
					ret.append("\\\\");
					break;
					
				case '/':
					ret.append("\\/");
					break;
				
				default:
					
					// \u0000-\u001f   \u007f-\u009f   \u00ad   \u0600-\u0604   \u070f   \u17b4   \u17b5   \u200c-\u200f   \u2028-\u202f   \u2060-\u206f   \ufeff   \ufff0-\uffff
					if( (0x0000<=c && c<=0x001F) || (0x007F<=c && c<=0x009F) || 
							(c==0x00AD) || (0x0600<=c && c<=0x0604) || (c==0x070F) || 
							(c==0x17B4) || (c==0x17B5) || (0x200C<=c && c<=0x200F) || 
							(0x2028<=c && c<=0x202F) || (0x2060<=c && c<=0x206F) || 
							(c==0xFEFF) || (0xFFF0<=c && c<=0xFFFF) ){
						//"unicode" escape some strange stuff
						ret.append("\\u" + Integer.toString((c & 0xFFFF)+0x10000, 16).substring(1));
					}else{
						//normal character
						ret.append(c);
					}
					break;
			}
		}
		
		String retStr = ret.append("\"").toString();
		
		try{
			return new String(retStr.getBytes("UTF-8"));
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return retStr;
		}
	}
}
