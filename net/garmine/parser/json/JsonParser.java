package net.garmine.parser.json;

import java.io.IOException;
import java.io.Reader;

import net.garmine.parser.Parser;
import net.garmine.parser.json.elements.JsonArray;
import net.garmine.parser.json.elements.JsonBoolean;
import net.garmine.parser.json.elements.JsonElement;
import net.garmine.parser.json.elements.JsonFloat;
import net.garmine.parser.json.elements.JsonInteger;
import net.garmine.parser.json.elements.JsonObject;
import net.garmine.parser.json.elements.JsonString;
import net.garmine.util.RingBuffer;

/**
 * JSON parser.<br><br>
 * JSON (JavaScript Object Notation) is a lightweight data-interchange format. It is easy 
 * for humans to read and write. It is easy for machines to parse and generate. It is based 
 * on a subset of the JavaScript Programming Language, Standard ECMA-262 3rd Edition - December 1999.<br><br>
 * JSON is a text format that is completely language independent but uses conventions that are familiar 
 * to programmers of the C-family of languages, including C, C++, C#, Java, JavaScript, Perl, Python,
 * and many others. These properties make JSON an ideal data-interchange language.<br><br>
 * Full description of JSONs here: www.json.org
 * @author Garmine
 * @see JSON
 */
public class JsonParser implements Parser<JsonElement>{
	
	//==========================================================\\
	//~~~>Static stuff											||
	//==========================================================//
	
	/**
	 * Quickly parses a JSON
	 * @param JSON - JSON to parse
	 * @return The parsed JSON.
	 * @throws MalformedJsonException - if anything goes wrong
	 */
	public static 	JsonElement qucikParse(Reader in) throws MalformedJsonException{
		return new Parse(in, false).get();
	}
	
	/**Number of characters to print before the wrong char*/
	private static final int ERR_PRINT_BEFORE = 35;
	/**Number of characters to print after the wrong char*/
	private static final int ERR_PRINT_AFTER  = 35;
	
	//==========================================================\\
	//~~~>Non-Static											||
	//==========================================================//
	
	private final boolean anyKey;
	
	/**
	 * Constructs a JSONParser with default parameters
	 */
	public JsonParser(){
		this(false);
	}
	
	/**
	 * Constructs a JSONParser with default snip values
	 * @param isAnyKey - true if non-String keys are permitted in JSONObjects
	 */
	public JsonParser(boolean isAnyKey){
		anyKey = isAnyKey;
	}/**
	 * Checks if non-String keys are permitted in JSONObjects
	 * @return True if non-String keys are permitted in JSONObjects
	 */
	public boolean isAnyKey(){
		return this.anyKey;
	}/**
	 * Parses a JSON
	 * @return The parsed JSON.
	 * @throws MalformedJsonException - if anything goes wrong
	 */
	@Override
	public JsonElement parse(Reader in) throws MalformedJsonException{
		return  new Parse(in, anyKey).get();
	}

	//==========================================================\\
	//~~~>Parser logic											||
	//==========================================================//
	
	/**
	 * Class which actually parses. It's here to maintain concurrency.
	 * @author Garmine
	 */
	private static class Parse {
		
		/* 
		 * Index: After beginning parsing : each value-parser gets the Object 
		 * with the index on the Object's first character, and NOT on the marker char: {i..}  [i..]  "i.."
		 * except for jComment() since it has a 2 character marker. /*...  //...
		 * and Null/Boolean/Number since they don't have a marker
		 * and passes with the index AFTER it's end: {...}i  [...]i  "..."i 
		 */
		
		//===========================>>
		/** Input Reader, contains the raw JSON. */
		private final Reader INPUT;
		/** The parsed (or under-parse) JSON. */
		private final JsonElement DATA;
		/** Flag if non-String keys are permitted in JSONObjects.  */
		private final boolean ANYKEY;
		//===========================>>
		/** Flag if stream is ended */
		private boolean ended = false;
		/** Current character being examined. */
		private char current;
		/** RingBuffer for error display mechanism */
		private RingBuffer<Character> errBuffer = new RingBuffer<>(ERR_PRINT_BEFORE+1);
		//===========================>>
		/** Number of unclosed quotation marks. */
		private int quMarkNum = 0;
		/** Number of unclosed square brackets. */
		private int sqBracketNum = 0;
		/** Number of unclosed curly brackets. */
		private int cuBracketNum = 0;
		//===========================>>
		
		/**
		 * Parses the given String. Runs only once, use {@link Parse#get()} to obtain the parsed JSON
		 * @param in - JSON to parse.
		 * @param anyKey - true if non-String keys are permitted in JSONObjects
		 * @throws MalformedJsonException if anything goes wrong
		 * @throws NullPointerException if in is null.
		 */
		private Parse(Reader in, boolean anyKey) throws MalformedJsonException{
			if (in==null) throw new NullPointerException("Input Reader mustn't be null!");
			
			//init
			INPUT = in;
			ANYKEY = anyKey;
			
			//read in first character.
			next();
			//try to parse
			DATA = jValue();
			
			//check - is this even needed? Just to be sure...
			if( quMarkNum!=0 || sqBracketNum!=0 || cuBracketNum!=0 ){
				throw jsonError("unclosed token(s)", false);
			}
		}
		
		/**
		 * Reads in the next Character from the input reader.
		 * Will throw a MalformedJSONException if end of the reader has been reached!
		 * @return The current character
		 * @throws MalformedJsonException if end of the reader has been reached.
		 * @see #current
		 */
		private char next() throws MalformedJsonException{
			try {
				int c = INPUT.read();
				if(c==-1){
					if(ended){
						throw jsonError("unclosed token(s)", true);
					}else{
						ended = true;
					}
				}
				current = (char)c;
				errBuffer.push(current);
				return current;
			} catch (IOException e) {
				throw jsonError(e.getMessage(), false);
			}
		}
		
		/**
		 * Calls {@link #next()}, and parses the character as a hexadecimal digit.
		 * @return The parsed hexadecimal digit.
		 * @throws MalformedJsonException - if anything goes wrong
		 * @see Character#digit(char,int)
		 */
		private int nextHex() throws MalformedJsonException{
			int ret = Character.digit(next(), 16);
			if(ret<0){
				throw jsonError("illegal escaped unicode char", false);
			}
			return ret;
		}
		
		/**
		 * Gets the parsed JSON
		 * @return The parsed JSON
		 */
		private JsonElement get(){
			return DATA;
		}
		
		/**
		 * Any Value (contains a gigaswitch!)
		 * @return A parsed JSON
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonElement jValue() throws MalformedJsonException{
			while(true){
				switch(current){
				
				case '{':		//Object
					next();
					cuBracketNum++;
					return jObject();
					
				case '[':		//Array
					next();
					sqBracketNum++;
					return jArray();
				
				case '\"':		//String
					quMarkNum++;
					next();
					return new JsonString(jString());
					
				case 't':		//true
					return jBoolean(true);
					
				case 'f':		//false
					return jBoolean(false);
					
				case 'n':		//null
					return jNull();
					
				case '/':		//comment
					jComment();
					continue;
					
				case ' ':		//space
					next();
					continue;
					
				case '-':		//number
					return jNumber();
					
				default:		//number or error
					if(Character.isDigit(current)){
						return jNumber();
					}else{
						throw jsonError("invalid value", true);
					}
				}
			}
		}
		
		/**
		 * Object {key:value,key:value}
		 * @return JSONObject
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonObject jObject() throws MalformedJsonException{
			JsonObject object = new JsonObject();
			
			while(true){
				String tempKey;
				JsonElement tempValue;
				
				//read key
				switch(current){
					case '\"':
						quMarkNum++;
						next();
						tempKey = jString();
						break;
					case '}':
						next();
						cuBracketNum--;
						return object;
					default:
						if(ANYKEY){
							tempKey = jAnyKey();
						}else{
							throw jsonError("invalid object: invalid key", true);
						}
				}
				
				//key-value separator
				if(current!=':'){
					throw jsonError("invalid object: invalid key/value pair", true);
				}else{
					next();
				}
				
				//read value
				tempValue = jValue();
				
				object.add(tempKey, tempValue);
				
				begin:
				while(true){
					switch(current){
						case ',':
							next();
							break begin;
						case '}':
							next();
							cuBracketNum--;
							return object;
						case '/':
							jComment();
							continue;
						case ' ':
							next();
							continue;
						default:
							throw jsonError("invalid object", true);
					}
				}
			}
		}
		
		/**
		 * Array: [value,value]
		 * @return JSONArray
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonArray jArray() throws MalformedJsonException{
			JsonArray ret = new JsonArray();
			while(true){
				if(current==']'){
					next();
					sqBracketNum--;
					return ret;
				}
				ret.add(jValue());
				begin:
				while(true){
					switch(current){
						case ',':
							next();
							break begin;
						case ']':
							next();
							sqBracketNum--;
							return ret;
						case '/':
							jComment();
							continue;
						case ' ':
							next();
							continue;
						default:
							throw jsonError("invalid array", true);
					}
				}
			}
		}
		
		/**
		 * String "value", note: JSON Objects use String as keys!
		 * @return The parsed String
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private String jString() throws MalformedJsonException{
			StringBuilder ret = new StringBuilder();
			
			while (true){
				
				//end of String
				if(current=='\"'){
					next();
					quMarkNum--;
					return ret.toString();
				}
				
				//escaped characters
				if(current=='\\'){
					switch (next()){
					case '\"':
						ret.append('\"');
						next();
						continue;
					case '\'':
						ret.append('\'');
						next();
						continue;
					case '\\':
						ret.append('\\');
						next();
						continue;
					case '/':
						ret.append('/');
						next();
						continue;
					case 'b':
						ret.append('\b');
						next();
						continue;
					case 'f':
						ret.append('\f');
						next();
						continue;
					case 'n':
						ret.append('\n');
						next();
						continue;
					case 'r':
						ret.append('\r');
						next();
						continue;
					case 't':
						ret.append('\t');
						next();
						continue;
					case 'u':
						ret.append((char) (nextHex()<<12|nextHex()<<8|nextHex()<<4|nextHex()));
						next();
						continue;
					default:
						throw jsonError("illegal escaped character", true);
					}
				}
				//normal character
				ret.append(current);
				next();
			}
		}
		
		/**
		 * Any key, used if the expected JSON isn't 100% standard. <br>
		 * I.e. the JSON Object may look like this: {key:value,key:value} instead of: {"key":value,"key":value}
		 * @return The parsed key as a String
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private String jAnyKey() throws MalformedJsonException{
			StringBuilder ret = new StringBuilder();
			while(true){
				if (next()==':'){
					break;
				}
				ret.append(current);
			}
			return ret.toString();
		}
		
		/**
		 * Number 1; -1; 1.0; -1.0 etc.
		 * @return JSONNumber (float or integer)
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonElement jNumber() throws MalformedJsonException{
			StringBuilder ret = new StringBuilder();
			boolean isFloat = false;
			
			//iterate through number
			begin:
			while(true){
				ret.append(current);
				switch(next()){
					//end
					case '}':
					case ']':
					case ',':
					case '/':
					case ' ':
						break begin;
					//float	
					case '.':
					case 'e':
					case 'E':
						isFloat = true;
						break;
					//allowed chars
					case '+':
					case '-':
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						//it's a number, do nothing!
						break;
					//illegal chars!
					default:
						//contains illegal character!
						throw jsonError("invalid value", true);
				}
			}
			
			if(isFloat){
				return new JsonFloat(Float.parseFloat(ret.toString()));
			}else{
				return new JsonInteger(Integer.parseInt(ret.toString()));
			}
		}
		
		/**
		 * Boolean: true/false
		 * @param value - true if the expected boolean is true, false otherwise
		 * @return JSONBoolean
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonBoolean jBoolean(boolean value) throws MalformedJsonException{
			if(value){
				if(next()=='r' && next()=='u' && next()=='e'){
					next();
					return new JsonBoolean(true);
				}else{
					throw jsonError("invalid value", true);
				}
			}else{
				if(next()=='a' && next()=='l' && next()=='s' && next()=='e'){
					next();
					return new JsonBoolean(false);
				}else{
					throw jsonError("invalid value", true);
				}
			}
		}
		
		/**
		 * Null: null
		 * @return null
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private JsonElement jNull() throws MalformedJsonException{
			if(next()=='u' && next()=='l' && next()=='l'){
				next();
				return null;
			}else{
				throw jsonError("invalid comment", true);
			}
		}
		
		/**
		 * Comment: /*...* /  //...\n<br>
		 * Note: comments are 100% ignored!
		 * @throws MalformedJsonException - if anything goes wrong
		 */
		private void jComment() throws MalformedJsonException{
			next();
			if (current=='/'){							//Comment 'till the end
				throw jsonError("unclosed token(s)", true);
			}else if(current=='*'){						/*Comment like this*/
				next();
				while(!(current=='*' && next()=='/')){}
				next();
			}else{										//Buggy shit like this: stuff_stuff/stuff_stuff
				throw jsonError("invalid comment", true);
			}
		}
		
		/**
		 * Generates a pretty error message
		 * @param msg - Message for the user, generates a String: ("JSON malformed: "+msg)
		 * @param printAll - true if print the JSON and mark the error
		 * @return The formatted MalformedJSONException
		 */
		private MalformedJsonException jsonError(String msg, boolean printAll){
			if(printAll){
				//SB for faster error print! Not like that it'd matter...
				//MAX size (can be smaller):             text before   current  text after     \n    spaces before    ^
				StringBuilder err = new StringBuilder(ERR_PRINT_BEFORE + 1 + ERR_PRINT_AFTER + 1 + ERR_PRINT_BEFORE + 1);
				
				//1st line: error + nearby text
				for (Object o:errBuffer.getElements()) err.append(o);	//h4x
				for(int i=0; i<ERR_PRINT_AFTER; i++){
					int c;
					try{
						c = INPUT.read();
					}catch(Exception ignored){
						break;
					}
					if (c==-1) break;
					err.append((char)c);
				}
				
				err.append('\n');
				
				//2nd line: mark the place
				for (int i=1; i<errBuffer.size(); i++) err.append(' ');
				err.append('^');
				
				return new MalformedJsonException("JSON malformed: "+msg+"\n"+err);
			}else{
				return new MalformedJsonException("JSON malformed: "+msg);
			}
		}
	}
}