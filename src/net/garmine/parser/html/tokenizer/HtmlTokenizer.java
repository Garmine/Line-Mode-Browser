package net.garmine.parser.html.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.LinkedList;

import net.garmine.parser.html.MalformedHtmlException;
import net.garmine.parser.html.tokenizer.HtmlTokenizerState;
import net.garmine.parser.html.tokenizer.tokens.*;

import static net.garmine.parser.html.tokenizer.HtmlTokenizerState.*;
import static net.garmine.parser.html.tokenizer.HtmlEntities.HTML_ENTITIES;

/**
 * The class which tokenizes HTML documents.
 * @author Garmine
 */
public class HtmlTokenizer {
	/**
	 * An temporary storage Class for INSIDE use only! 
	 * It overrides the {@link #hashCode()} and {@link #equals()} 
	 * methods in such a way that it'll only consider the 
	 * {{@link #name} of the Attribute!
	 * @author Garmine
	 */
	private class Attribute{
		/** The name of the Attribute */
		private final String name;
		/** The value of the Attribute */
		private final String value;
		/**
		 * Construct a new Attribute.
		 * @param name - the name of the Attribute
		 * @param value - the value of the Attribute
		 */
		private Attribute(String name, String value){
			this.name = name;
			this.value = value;
		}
		@Override
		public int hashCode(){
			return name.hashCode();
		}
		@Override
		public boolean equals(Object o){
			return name.equals(o);
		}
	}
	
	/** Flag if Tokenizer shall die on first error */
	private final boolean die;
	
	//=======================[INPUT/OUTPUT]======================//
	/** The integer ({@value}) that a Reader returns when it reaches the End Of Stream */
	public static final int EOF = -1;
	/** The input source */
	private final Reader input;
	/** Current character (or EOF) */
	private int current;
	/** Reconsume flag */
	private boolean re = false;
	/** Unconsume flag */
	private boolean un = false;
	/** 
	 * Output queue (buffer): we shall only emit 1 Token / call, 
	 * but the state machine may emit multiple Tokens before it stops.
	 */
	private final LinkedList<HtmlToken> queue;

	//=========================[BUFFERS]=========================//
	/** Temporary buffer (~2KB = 1024 chars initally) */
	private char[] buffer = new char[1024];
	/** Buffer index */
	private int bi = 0;
	/** Unconsume buffer index */
	private int uni = 0;
	/** Unconsume buffer (~1KB = 512 chars initally) */
	private char[] unBuffer = new char[512];
	
	//========================[TAG TOKEN]========================//
	/** Flag if current tag token is an end tag */
	private boolean endTag = false;
	/** Last open tag token's name */
	private String lastOpenTag = null;
	/** Current tag token's name('s buffer) */
	private StringBuilder tagName = new StringBuilder(64);
	/** Current attribute's name('s buffer) */
	private StringBuilder attrName = new StringBuilder(64);
	/** Current attribute's value('s buffer) */
	private StringBuilder attrValue = new StringBuilder(64);
	/** Current tag token's attributes */
	private HashSet<Attribute> attributes = new HashSet<>();
	
	//======================[COMMENT TOKEN]======================//
	/** Current comment token's data('s buffer) */
	private StringBuilder comment = new StringBuilder(64);
	
	//======================[DOCTYPE TOKEN]======================//
	/** Current DOCTYPE token's name('s buffer) */
	private StringBuilder docName = new StringBuilder(64);
	/** Current DOCTYPE token's SYSTEM identifier('s buffer) */
	private StringBuilder docSystem = new StringBuilder(64);
	/** Current DOCTYPE token's PUBLIC identifier('s buffer) */
	private StringBuilder docPublic = new StringBuilder(64);
	/** Current DOCTYPE token's force-quirks flag */
	private boolean docForceQuirks = false;
	
	//==========================[STATE]==========================//
	/** Statemachine's current state */
	HtmlTokenizerState state = DATA;
	/** End of stream flag */
	private boolean end = false;
	
	/**
	 * Constructs and initializes a new HTML Tokenizer.
	 * @param in - input Stream
	 * @param die - flag if Tokenizer shall die on first error (~strict mode)
	 */
	public HtmlTokenizer(Reader in, boolean die){
		if (in == null) throw new NullPointerException("in mustn't be null!");
		input = in;
		this.die = die;
		queue = new LinkedList<>();
		//tokenize();
	}

	/**
	 * Switches the HtmlTokenizer to State s unless s==null.<br>
	 * <br>
	 * Use with sense!<br>
	 * E.g.: <code>tokenizer.switchTo(HtmlTokenizerState.SCRIPT);</code> after a &lt;SCRIPT&gt; tag
	 * @param s - state to switch to
	 */
	public void switchTo(HtmlTokenizerState s){
		if (s!=null) state = s;
	}
	
	/**
	 * Checks if there are any Tokens left.
	 * @return True, if end of stream has not yet been reached or there's still Tokens left to be emitted.
	 */
	//REMOVED: so that the HtmlParser will have more direct control over the Tokenization
	/*public boolean hasNext(){
		return !queue.isEmpty() || !end;
	}*/

	/**
	 * Tokenizes and returns the next HtmlToken in the input stream.
	 * @return The next HtmlToken
	 * @throws IOException if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException if the {@link #die} flag is set and the initialization fails due to malformed HTML
	 */
	public HtmlToken next() throws IOException, MalformedHtmlException {
		if(!queue.isEmpty()){
			//We still have HtmlTokens to emit
			return queue.remove();
		}else if(!end){
			//We need new HtmlTokens
			tokenize();
			if(!queue.isEmpty()){
				//We have new HtmlTokens to emit
				return queue.remove();
			}else{
				//We've reached the end
				return HtmlEofToken.EOF;
			}
		}else{
			//We've reached the end
			return HtmlEofToken.EOF;
		}
		/* OLD hasNext() code
		if(!hasNext()){
			return HtmlEofToken.EOF;
		}else{
			if (!end) tokenize();
			assert !queue.isEmpty() : "ERROR: #hasNext() returned true yet there is NOTHING in the queue!";
			return queue.remove();
		}
		*/
	}
	
	//=============================
	
	/**
	 * Processes the input stream 'till it constructs a Token which can be emitted.
	 * @throws IOException if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException if the {@link #die} flag is set and the initialization fails due to malformed HTML
	 */
	private void tokenize() throws IOException, MalformedHtmlException {
		while(true){
			boolean nop = false;
			switch(state){
				case DATA: 
					read();
					switch(current){
						case '&': 
							for (char c:readEntity('\0')) emit(c);
							break;
							
						case '<': 
							state = TAG_OPEN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit(current);
							return;
							
						case EOF:
							emit(EOF);
							return;
							
						default:
							emit(current);
							return;
					}
					break;
					
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
				case RCDATA: 
					read();
					switch(current){
						case '&': 
							for (char c:readEntity('\0')) emit(c);
							break;
							
						case '<': 
							state = RCDATA_LESS_THAN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							emit(EOF);
							return;
							
						default:
							emit(current);
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
				case RAWTEXT: 
					read();
					switch(current){
						case '<': 
							state = RAWTEXT_LESS_THAN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							emit(EOF);
							return;
							
						default:
							emit(current);
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT: 
					read();
					switch(current){
						case '<': 
							state = SCRIPT_LESS_THAN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							emit(EOF);
							return;
							
						default:
							emit(current);
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
					/*
				case PLAINTEXT: 
					read();
					switch(current){
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							emit(EOF);
							return;
							
						default:
							emit(current);
							return;
					}
					//break;
					*/
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case TAG_OPEN: 
					read();
					switch(current){
						case '!':
							state = MARKUP_DECL_OPEN;
							break;
							
						case '/':
							state = END_TAG_OPEN;
							break;
							
						case '?':
							err("XML declaration opening inside HTML document: \"<?\"");
							state = BOGUS_COMMENT;
							break;
							
						default:
							if(isAsciiLetter(current)){
								newTag(false);
								tagName.append(lower(current));
								state = TAG_NAME;
							}else{
								err("Illegal character after \'<\'");
								emit('<');
								re = true;
								state = DATA;
								return;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case END_TAG_OPEN: 
					read();
					switch(current){
						case '>':
							err("Invalid end tag: \"</>\"");
							state = DATA;
							break;
							
						case EOF:
							err("End of stream reached inside an end tag");
							emit('<'); 
							emit('/');
							re = true;
							state = DATA;
							return;
							
						default:
							if(isAsciiLetter(current)){
								newTag(true);
								tagName.append(lower(current));
								state = TAG_NAME;
							}else{
								err("Invalid end tag: \"</"+current+"\"");
								state = BOGUS_COMMENT;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case TAG_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BEFORE_ATTRIBUTE_NAME;
							break;
							
						case '/':
							state = SELF_CLOSING_START_TAG;
							break;
							
						case '>':
							emitTag(false);
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							tagName.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside a tag");
							re = true;
							state = DATA;
							break;
							
						default:
							if(isUpperAscii(current)){
								tagName.append(lower(current));
							}else{
								tagName.append((char)current);
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RCDATA_LESS_THAN: 
					read();
					switch(current){
						case '/':
							bi = 0;
							state = RCDATA_END_TAG_OPEN;
							break;
							
						default:
							emit('<');
							re = true;
							state = RCDATA;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RCDATA_END_TAG_OPEN: 
					read();
					if(isAsciiLetter(current)){
						newTag(true);
						tagName.append(lower(current));
						buff((char)current);
						state = RCDATA_END_TAG_NAME;
					}else{
						emit('<');
						emit('/');
						re = true;
						state = RCDATA;
						return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RCDATA_END_TAG_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							if(!nop && equals(tagName, lastOpenTag)){
								state = BEFORE_ATTRIBUTE_NAME;
								break;
							}else{
								nop = true;
							}
						case '/':
							if(!nop && equals(tagName, lastOpenTag)){
								state = SELF_CLOSING_START_TAG;
								break;
							}else{
								nop = true;
							}
						case '>':
							if(!nop && equals(tagName, lastOpenTag)){
								emitTag(false);
								state = DATA;
								return;
							}else{
								nop = true;
							}
						default:
							if(isAsciiLetter(current)){
								tagName.append(lower(current));
								buff((char)current);
							}else{
								emit('<');
								emit('/');
								emitBuffer();
								re = true;
								state = RCDATA;
								return;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RAWTEXT_LESS_THAN: 
					read();
					switch(current){
						case '/':
							bi = 0;
							state = RAWTEXT_END_TAG_OPEN;
							break;
							
						default:
							emit('<');
							re = true;
							state = RAWTEXT;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RAWTEXT_END_TAG_OPEN: 
					read();
					if(isAsciiLetter(current)){
						newTag(true);
						tagName.append(lower(current));
						buff((char)current);
						state = RAWTEXT_END_TAG_NAME;
					}else{
						emit('<');
						emit('/');
						re = true;
						state = RAWTEXT;
						return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case RAWTEXT_END_TAG_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							if(!nop && equals(tagName, lastOpenTag)){
								state = BEFORE_ATTRIBUTE_NAME;
								break;
							}else{
								nop = true;
							}
						case '/':
							if(!nop && equals(tagName, lastOpenTag)){
								state = SELF_CLOSING_START_TAG;
								break;
							}else{
								nop = true;
							}
						case '>':
							if(!nop && equals(tagName, lastOpenTag)){
								state = DATA;
								emitTag(false);
								return;
							}else{
								nop = true;
							}
						default:
							if(isAsciiLetter(current)){
								tagName.append(lower(current));
								buff((char)current);
							}else{
								emit('<');
								emit('/');
								emitBuffer();
								state = RAWTEXT;
								re = true;
								return;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_LESS_THAN: 
					read();
					switch(current){
						case '/':
							bi = 0;
							state = SCRIPT_END_TAG_OPEN;
							break;
							
						case '!':
							emit('<'); 
							emit('!');
							state = SCRIPT_ESCAPE_START;
							return;
							
						default:
							emit('<');
							re = true;
							state = SCRIPT;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_END_TAG_OPEN: 
					read();
					if(isAsciiLetter(current)){
						newTag(true);
						tagName.append(lower(current));
						buff((char)current);
						state = SCRIPT_END_TAG_NAME;
					}else{
						emit('<');
						emit('/');
						re = true;
						state = SCRIPT;
						return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
				case SCRIPT_END_TAG_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							if(!nop && equals(tagName, lastOpenTag)){
								state = BEFORE_ATTRIBUTE_NAME;
								break;
							}else{
								nop = true;
							}
						case '/':
							if(!nop && equals(tagName, lastOpenTag)){
								state = SELF_CLOSING_START_TAG;
								break;
							}else{
								nop = true;
							}
						case '>':
							if(!nop && equals(tagName, lastOpenTag)){
								state = DATA;
								emitTag(false);
								return;
							}else{
								nop = true;
							}
						default:
							if(isAsciiLetter(current)){
								tagName.append(lower(current));
								buff((char)current);
							}else{
								emit('<');
								emit('/');
								emitBuffer();
								re = true;
								state = SCRIPT;
								return;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_ESCAPE_START: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_ESCAPE_START_DASH;
							return;
							
						default:
							re = true;
							state = SCRIPT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_ESCAPE_START_DASH: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_ESCAPED_DASH_DASH;
							return;
							
						default:
							re = true;
							state = SCRIPT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_ESCAPED: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_ESCAPED_DASH;
							return;
							
						case '<': 
							state = SCRIPT_ESCAPED_LESS_THAN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
				case SCRIPT_ESCAPED_DASH: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_ESCAPED_DASH_DASH;
							return;
							
						case '<': 
							state = SCRIPT_ESCAPED_LESS_THAN;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							state = SCRIPT_ESCAPED;
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							state = SCRIPT_ESCAPED;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
				case SCRIPT_ESCAPED_DASH_DASH: 
					read();
					switch(current){
						case '-':
							emit('-');
							return;
							
						case '<': 
							state = SCRIPT_ESCAPED_LESS_THAN;
							break;
							
						case '>':
							emit('>');
							state = SCRIPT;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							state = SCRIPT_ESCAPED;
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							state = SCRIPT_ESCAPED;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_ESCAPED_LESS_THAN: 
					read();
					switch(current){
						case '/':
							bi = 0;
							state = SCRIPT_ESCAPED_END_TAG_OPEN;
							break;
							
						default:
							emit('<');
							if(isAsciiLetter(current)){
								bi = 0;
								buff(lower(current));
								emit(current);
								state = SCRIPT_DOUBLE_ESCAPE_START;
							}else{
								re = true;
								state = SCRIPT_ESCAPED;
							}
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_ESCAPED_END_TAG_OPEN: 
					read();
					if(isAsciiLetter(current)){
						newTag(true); 
						tagName.append(lower(current));
						buff((char)current);
						state = SCRIPT_ESCAPED_END_TAG_NAME;
					}else{
						emit('<');
						emit('/');
						re = true;
						state = SCRIPT_ESCAPED;
						return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_ESCAPED_END_TAG_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							if(!nop && equals(tagName, lastOpenTag)){
								state = BEFORE_ATTRIBUTE_NAME;
								break;
							}else{
								nop = true;
							}
						case '/':
							if(!nop && equals(tagName, lastOpenTag)){
								state = SELF_CLOSING_START_TAG;
								break;
							}else{
								nop = true;
							}
						case '>':
							if(!nop && equals(tagName, lastOpenTag)){
								state = DATA;
								emitTag(false);
								return;
							}else{
								nop = true;
							}
						default:
							if(isAsciiLetter(current)){
								tagName.append(lower(current));
								buff((char)current);
							}else{
								emit('<');
								emit('/');
								emitBuffer();
								re = true;
								state = SCRIPT_ESCAPED;
								return;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPE_START: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
						case '/':
						case '>':
							if(new String(buffer, 0, bi).equals("script")){
								state = SCRIPT_DOUBLE_ESCAPED;
							}else{
								state = SCRIPT_ESCAPED;
							}
							emit(current);
							return;
							
						default:
							if(isAsciiLetter(current)){
								buff(lower(current));
								emit(current);
								return;
							}else{
								re = true;
								state = SCRIPT_ESCAPED;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPED: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_DOUBLE_ESCAPED_DASH;
							return;
							
						case '<': 
							emit('<');
							state = SCRIPT_DOUBLE_ESCAPED_LESS_THAN;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPED_DASH: 
					read();
					switch(current){
						case '-':
							emit('-');
							state = SCRIPT_DOUBLE_ESCAPED_DASH_DASH;
							return;
							
						case '<': 
							emit('<');
							state = SCRIPT_DOUBLE_ESCAPED_LESS_THAN;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							state = SCRIPT_DOUBLE_ESCAPED;
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							state = SCRIPT_DOUBLE_ESCAPED;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPED_DASH_DASH: 
					read();
					switch(current){
						case '-':
							emit('-');
							return;
							
						case '<': 
							emit('<');
							state = SCRIPT_DOUBLE_ESCAPED_LESS_THAN;
							return;
							
						case '>':
							emit('>');
							state = SCRIPT;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							emit('\uFFFD');
							state = SCRIPT_DOUBLE_ESCAPED;
							return;
							
						case EOF:
							err("End of stream reached inside script");
							re = true;
							state = DATA;
							break;
							
						default:
							emit(current);
							state = SCRIPT_DOUBLE_ESCAPED;
							return;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPED_LESS_THAN: 
					read();
					switch(current){
						case '/':
							bi = 0;
							emit('/');
							state = SCRIPT_DOUBLE_ESCAPE_END;
							return;
							
						default:
							re = true;
							state = SCRIPT_DOUBLE_ESCAPED;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SCRIPT_DOUBLE_ESCAPE_END: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
						case '/':
						case '>':
							emit(current);
							if(new String(buffer, 0, bi).equals("script")){
								state = SCRIPT_ESCAPED;
							}else{
								state = SCRIPT_DOUBLE_ESCAPED;
							}
							return;
							
						default:
							if(isAsciiLetter(current)){
								buff(lower(current));
								emit(current);
								return;
							}else{
								re = true;
								state = SCRIPT_DOUBLE_ESCAPED;
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BEFORE_ATTRIBUTE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '/':
							state = SELF_CLOSING_START_TAG;
							break;
							
						case '>':
							state = DATA;
							emitTag(false);
							return;
							
						case '\0':
							err("Null character inside HTML document");
							newAttr();
							attrName.append('\uFFFD');
							state = ATTRIBUTE_NAME;
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							re = true;
							state = DATA;
							break;
	
						case '\"':
						case '\'':
						case '<': 
						case '=':
							err("Illegal character inside attribute name: \'"+current+'\'');
						default:
							newAttr();
							if(isUpperAscii(current)){
								attrName.append(lower(current));
							}else{
								attrName.append((char)current);
							}
							state = ATTRIBUTE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case ATTRIBUTE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = AFTER_ATTRIBUTE_NAME;
							break;
							
						case '/':
							emitAttr();
							state = SELF_CLOSING_START_TAG;
							break;
							
						case '=':
							state = BEFORE_ATTRIBUTE_VALUE;
							break;
							
						case '>':
							emitAttr();
							emitTag(false);
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							attrName.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							re = true;
							state = DATA;
							break;
	
						case '\"':
						case '\'':
						case '<': 
							err("Illegal character inside attribute name: \'"+current+'\'');
						default:
							if(isUpperAscii(current)){
								attrName.append(lower(current));
							}else{
								attrName.append((char)current);
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_ATTRIBUTE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
			
						case '/':
							emitAttr();
							state = SELF_CLOSING_START_TAG;
							break;
							
						case '=':
							state = BEFORE_ATTRIBUTE_VALUE;
							break;
							
						case '>':
							emitAttr();
							emitTag(false);
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							emitAttr();
							newAttr();
							attrName.append('\uFFFD');
							state = ATTRIBUTE_NAME;
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							re = true;
							state = DATA;
							break;
	
						case '\"':
						case '\'':
						case '<': 
							err("Illegal character inside attribute name: \'"+current+'\'');
						default:
							newAttr();
							if(isUpperAscii(current)){
								attrName.append(lower(current));
							}else{
								attrName.append((char)current);
							}
							state = ATTRIBUTE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BEFORE_ATTRIBUTE_VALUE: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '\"':
							state = ATTRIBUTE_VALUE_DOUBLE_QUOTED;
							break;
							
						case '&': 
							re = true;
							state = ATTRIBUTE_VALUE_UNQUOTED;
							break;
							
						case '\'':
							state = ATTRIBUTE_VALUE_SINGLE_QUOTED;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							attrValue.append('\uFFFD');
							state = ATTRIBUTE_VALUE_UNQUOTED;
							break;
							
						case '>':
							err("Illegal attribute: end of tag after \'=\'");
							emitAttr();
							emitTag(false);
							state = DATA;
							return;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							state = DATA;
							re = true;
							break;
	
						case '<': 
						case '=':
						case '`':
							err("Illegal character inside attribute value: \'"+current+'\'');
						default:
							attrValue.append((char)current);
							state = ATTRIBUTE_VALUE_UNQUOTED;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case ATTRIBUTE_VALUE_DOUBLE_QUOTED: 
					read();
					switch(current){
						case '\"':
							state = AFTER_ATTRIBUTE_VALUE_QUOTED;
							break;
							
						case '&': 
							attrValue.append(readEntity('\"'));
							break;
			
						case '\0':
							err("Null character inside HTML document");
							attrValue.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							state = DATA;
							re = true;
							break;
							
						default:
							attrValue.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case ATTRIBUTE_VALUE_SINGLE_QUOTED: 
					read();
					switch(current){
						case '\'':
							state = AFTER_ATTRIBUTE_VALUE_QUOTED;
							break;
							
						case '&': 
							attrValue.append(readEntity('\''));
							break;
							
						case '\0':
							err("Null character inside HTML document");
							attrValue.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							re = true;
							state = DATA;
							break;
							
						default:
							attrValue.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case ATTRIBUTE_VALUE_UNQUOTED: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							emitAttr();
							state = BEFORE_ATTRIBUTE_NAME;
							break;
							
						case '&': 
							attrValue.append(readEntity('>'));
							break;
							
						case '>':
							emitAttr();
							emitTag(false);
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							attrValue.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside tag");
							emitAttr();
							state = DATA;
							re = true;
							break;
							
						case '\"':
						case '\'':
						case '<': 
						case '=':
						case '`':
							err("Illegal character inside attribute value: \'"+current+'\'');
						default:
							attrValue.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_ATTRIBUTE_VALUE_QUOTED:
					emitAttr();
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BEFORE_ATTRIBUTE_NAME;
							break;
							
						case '/':
							state = SELF_CLOSING_START_TAG;
							break;
							
						case '>':
							emitTag(false);
							state = DATA;
							return;
							
						case EOF:
							err("End of stream reached inside tag");
							re = true;
							state = DATA;
							break;
							
						default:
							err("Null character inside HTML document");
							re = true;
							state = BEFORE_ATTRIBUTE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case SELF_CLOSING_START_TAG: 
					read();
					switch(current){
						case '>':
							emitTag(true);
							state = DATA;
							return;
							
						case EOF:
							err("End of stream reached at the end of a tag");
							re = true;
							state = DATA;
							break;
							
						default:
							err("Illegal character at end of tag: \'"+current+'\'');
							re = true;
							state = BEFORE_ATTRIBUTE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BOGUS_COMMENT:
					newComment();
					while(read() != '>' || current != EOF){
						if(current == '\0'){
							comment.append('\uFFFD');
						}else{
							comment.append((char)current);
						}
					}
					emitComment();
		
					re = (current==EOF);
					state = DATA;
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case MARKUP_DECL_OPEN: 
					//We gotta check the next 7 characters...
					char[] proto = new char[7];
					
					//Read them in the first 2
					for(int i=0; i<2; i++){
						read();
						//If we find an EOF reconsume the read-in chars
						if(current == EOF){
							for(int j=i-2; j>=0; j--){
								unConsume(proto[j]);
							}
						}else{
							proto[i] = (char)current;
						}
					}
					
					//Check for "--"
					if(current != EOF){
						if(proto[0] == '-' && proto[1] == '-'){
							newComment();
							state = COMMENT_START;
							break;
						}
						
						//Read in the rest 5
						for(int i=2; i<7; i++){
							read();
							//If we find an EOF reconsume the read-in chars
							if(current == EOF){
								for(int j=i-2; j>=0; j--){
									unConsume(proto[j]);
								}
							}else{
								proto[i] = (char)current;
							}
						}
						
						if(current != EOF){
							String str = new String(proto);
							if(str.equalsIgnoreCase("DOCTYPE")){
								state = DOCTYPE;
								break;
							}else if(str.equals("[CDATA[")){
								state = CDATA_SECTION;
								break;
							}else{
								//Don't consume the characters!
								for(int i=5; i>=0; i--){
									unConsume(proto[i]);
								}
							}
						}
					}
					
					//We did not find "--"/"DOCTYPE"/"[CDATA[" and/or 
					//We encountered an EOF while reading in,
					//So: ERROR!
					err("Non - comment/DOCTYPE/CDATA tag opened with \"<!\"");
					state = BOGUS_COMMENT;
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT_START: 
					read();
					switch(current){
						case '-':
							state = COMMENT_START_DASH;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append('\uFFFD');
							state = COMMENT;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside comment");
						case '>':
							err("Illegal comment: \"<!-->\"");
							emitComment();
							state = DATA;
							return;
							
						default:
							comment.append((char)current);
							state = COMMENT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT_START_DASH: 
					read();
					switch(current){
						case '-':
							state = COMMENT_END;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append("-\uFFFD");
							state = COMMENT;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside comment");
						case '>':
							err("Illegal comment: \"<!--->\"");
							emitComment();
							state = DATA;
							return;
							
						default:
							comment.append('-').append((char)current);
							state = COMMENT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT: 
					read();
					switch(current){
						case '-':
							state = COMMENT_END_DASH;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside comment");
							emitComment();
							re = true;
							state = DATA;
							return;
							
						default:
							comment.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT_END_DASH: 
					read();
					switch(current){
						case '-':
							state = COMMENT_END;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append("-\uFFFD");
							state = COMMENT;
							break;
							
						case EOF:
							err("End of stream reached inside comment");
							state = DATA;
							emitComment();
							re = true;
							return;
							
						default:
							comment.append("-"+(char)current);
							state = COMMENT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT_END: 
					read();
					switch(current){
						case '>':
							emitComment();
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append("--\uFFFD");
							state = COMMENT;
							break;
							
						case '!':
							err("Illegal string inside comment: \"--!\"");
							state = COMMENT_END_BANG;
							break;
							
						case '-':
							err("Illegal string inside comment: \"---\"");
							comment.append('-');
							break;
							
						case EOF:
							err("End of stream reached inside comment");
							emitComment();
							re = true;
							state = DATA;
							return;
							
						default:
							err("Illegal string inside comment: \"--\"");
							comment.append("--"+(char)current);
							state = COMMENT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case COMMENT_END_BANG: 
					read();
					switch(current){
						case '-':
							comment.append("--!");
							state = COMMENT_END_DASH;
							break;
							
						case '>':
							emitComment();
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							comment.append("--!\uFFFD");
							state = COMMENT;
							break;
							
						case EOF:
							err("End of stream reached inside comment");
							emitComment();
							re = true;
							state = DATA;
							return;
							
						default:
							comment.append("--!"+(char)current);
							state = COMMENT;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BEFORE_DOCTYPE_NAME;
							break;
							
						case EOF:
							err("End of stream reached inside Doctype");
							newDoc();
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							return;
							
						default:
							err("Illegal character inside Doctype: \'"+current+'\'');
							re = true;
							state = BEFORE_DOCTYPE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BEFORE_DOCTYPE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '\0':
							err("Null character inside HTML document");
							newDoc();
							docName.append('\uFFFD');
							state = DOCTYPE_NAME;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("Illegal Doctype: \"<!DOCTYPE>\"");
							newDoc();
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							return;
							
						default:
							newDoc();
							if(isUpperAscii((char)current)){
								docName.append(lower(current));
							}else{
								docName.append((char)current);
							}
							state = DOCTYPE_NAME;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = AFTER_DOCTYPE_NAME;
							break;
							
						case '>':
							emitDoc();
							state = DATA;
							return;
							
						case '\0':
							err("Null character inside HTML document");
							docName.append('\uFFFD');
							break;
							
						case EOF:
							err("End of stream reached inside Doctype");
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							return;
							
						default:
							if(isUpperAscii((char)current)){
								docName.append(lower(current));
							}else{
								docName.append((char)current);
							}
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_DOCTYPE_NAME: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '>':
							emitDoc();
							state = DATA;
							return;
							
						case EOF:
							err("End of stream reached inside Doctype");
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							break;
							
						default:
							//We gotta check the next 6 characters...
							
							//Read them in
							proto = new char[6];
							for(int i=0; i<6; i++){
								read();
								//If we find an EOF reconsume the read-in chars
								if(current == EOF){
									for(int j=i-2; j>=0; j--){
										unConsume(proto[j]);
									}
								}else{
									proto[i] = (char)current;
								}
							}
							
							//Only check the 6 charaters if we didn't encounter an EOF
							//while reading them in
							if(current != EOF){
								String str = new String(proto);
								if(str.equalsIgnoreCase("PUBLIC")){
									//YAY! Public keyword
									state = AFTER_DOCTYPE_PUBLIC_KEYWORD;
									break;
								}else if(str.equalsIgnoreCase("SYSTEM")){
									//YAY! System keyword
									state = AFTER_DOCTYPE_SYSTEM_KEYWORD;
									break;
								}else{
									//Don't consume the characters!
									for(int i=5; i>=0; i--){
										unConsume(proto[i]);
									}
								}
							}
							
							//We did not find "PUBLIC"/"SYSTEM" and/or 
							//We encountered an EOF while reading in,
							//So: ERROR!
							err("Illegal content inside Doctype");
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_DOCTYPE_PUBLIC_KEYWORD: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BEFORE_DOCTYPE_PUBLIC_IDENTIFIER;
							break;
							
						case '\"':
							err("No whitespace after Doctype PUBLIC keyword");
							docPublic.setLength(0); 
							state = DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED;
							break;
							
						case '\'':
							err("No whitespace after Doctype PUBLIC keyword");
							docPublic.setLength(0); 
							state = DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("End of Doctype after PUBLIC keyword");
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							err("Illegal character after Doctype PUBLIC keyword: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BEFORE_DOCTYPE_PUBLIC_IDENTIFIER: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '\"':
							docPublic.setLength(0);
							state = DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED;
							break;
							
						case '\'':
							docPublic.setLength(0);
							state = DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("End of Doctype after PUBLIC keyword");
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							err("Illegal character after Doctype PUBLIC keyword: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED: 
					read();
					switch(current){
						case '\"':
							state = AFTER_DOCTYPE_PUBLIC_IDENTIFIER;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							docPublic.append('\uFFFD');
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("Illegal character inside Doctype PUBLIC identifier: \'"+current+'\'');
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							docPublic.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED: 
					read();
					switch(current){
						case '\'':
							state = AFTER_DOCTYPE_PUBLIC_IDENTIFIER;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							docPublic.append('\uFFFD');
							break;
	
						case EOF:
							re = true;
						case '>':
							err("Illegal character inside Doctype PUBLIC identifier: \'"+current+'\'');
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							docPublic.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_DOCTYPE_PUBLIC_IDENTIFIER: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS;
							break;
							
						case '>':
							state = DATA;
							emitDoc();
							break;
							
						case '\"':
							err("Illegal character after Doctype PUBLIC identifier: \'"+current+'\'');
							docSystem.setLength(0);
							state = DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED;
							break;
							
						case '\'':
							err("Illegal character after Doctype PUBLIC identifier: \'"+current+'\'');
							docSystem.setLength(0);
							state = DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED;
							break;
							
						case EOF:
							err("End of stream reached inside Doctype");
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							break;
							
						default:
							err("Illegal character inside Doctype: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '>':
							emitDoc();
							state = DATA;
							break;
							
						case '\"':
							docSystem.setLength(0);
							state = DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED;
							break;
							
						case '\'':
							docSystem.setLength(0);
							state = DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED;
							break;
							
						case EOF:
							err("End of stream reached inside Doctype");
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							break;
							
						default:
							err("Illegal character inside Doctype: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_DOCTYPE_SYSTEM_KEYWORD: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							state = BEFORE_DOCTYPE_SYSTEM_IDENTIFIER;
							break;
							
						case '\"':
							err("No whitespace after Doctype SYSTEM keyword");
							docSystem.setLength(0); 
							state = DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED;
							break;
							
						case '\'':
							err("No whitespace after Doctype SYSTEM keyword");
							docSystem.setLength(0); 
							state = DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("End of Doctype after SYSTEM keyword");
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							err("Illegal character after Doctype SYSTEM keyword: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BEFORE_DOCTYPE_SYSTEM_IDENTIFIER: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '\"':
							docSystem.setLength(0); 
							state = DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED;
			
							break;
						case '\'':
							docSystem.setLength(0); 
							state = DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED;
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("End of Doctype after SYSTEM keyword");
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							err("Illegal character after Doctype SYSTEM keyword: \'"+current+'\'');
							docForceQuirks = true;
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED: 
					read();
					switch(current){
						case '\"':
							state = AFTER_DOCTYPE_SYSTEM_IDENTIFIER;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							docSystem.append('\uFFFD');
							break;
	
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("Illegal character inside Doctype SYSTEM identifier: \'"+current+'\'');
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							docSystem.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED: 
					read();
					switch(current){
						case '\'':
							state = AFTER_DOCTYPE_SYSTEM_IDENTIFIER;
							break;
							
						case '\0':
							err("Null character inside HTML document");
							docSystem.append('\uFFFD');
							break;
							
						case EOF:
							re = true;
							err("End of stream reached inside Doctype");
						case '>':
							err("Illegal character inside Doctype SYSTEM identifier: \'"+current+'\'');
							docForceQuirks = true;
							emitDoc();
							state = DATA;
							break;
							
						default:
							docSystem.append((char)current);
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case AFTER_DOCTYPE_SYSTEM_IDENTIFIER: 
					read();
					switch(current){
						case '\t':
						case '\n':
						case '\f':
						case ' ':
							//zzzZZZzzzZZZzzz
							break;
							
						case '>':
							emitDoc();
							state = DATA;
							break;
							
						case EOF:
							err("End of stream reached inside Doctype");
							docForceQuirks = true;
							emitDoc();
							re = true;
							state = DATA;
							break;
							
						default:
							err("Illegal character after Doctype SYSTEM identifier: \'"+current+'\'');
							state = BOGUS_DOCTYPE;
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case BOGUS_DOCTYPE: 
					read();
					switch(current){
						case '>':
							emitDoc();
							state = DATA;
							break;
							
						case EOF:
							emitDoc();
							state = DATA;
							re = true;
							break;
							
						default:
							//zzzZZZzzzZZZzz
							break;
					}
					break;
	
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
				case CDATA_SECTION:
					//3 element "pseudo-queue" (lawl)
					int[] arr = new int[3];
					if((arr[0] = read()) == EOF){
						re = true;
					}
					if((arr[1] = read()) == EOF){
						emit(arr[0]);
						re = true;
					}
					arr[2] = read();
					//read in until we find "]]>", or EOF, whichever comes first
					while(!(arr[0] == ']' && arr[1] == ']' && arr[2] == '>')){
						emit(arr[0]);
						if(arr[2] == EOF){
							emit(arr[1]);
							re = true;
							break;
						}
						arr[0] = arr[1];
						arr[1] = arr[2];
						arr[2] = read();
					}
					state = DATA;
					return;
			}
		}
	}
	
	
	//~~
	

	/**
	 * Tries to read in an HTML entity, and returns either it's value, or ['&'] if it is not an entity.
	 * @param additional - additional break character
	 * @return The value of the entity, or ['&'].
	 * @throws IOException if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException if the {@link #die} flag is set and the entitiy is malformed/illegal
	 */
	private char[] readEntity(char additional) throws IOException, MalformedHtmlException{
		//return value if this is not a character reference!
		final char[] NOP = new char[]{'&'};
		read();
		switch(current){
			case '\t':
			case '\n':
			case '\f':
			case ' ':
			case '<':
			case EOF:
				re = true;
				return NOP;
				
				//~~
				
			case '#':		//numeric charater reference
				read();
				boolean hex = false;
				//Is it hexadecimal?
				if(current == 'x' || current == 'X'){
					hex = true;
					read();
				}
				
				//Read in some decimal/hexadecimal numbers
				StringBuilder raw = new StringBuilder();
				while( (('0'<=current&&current<='9') || (hex && ( ('a'<=current&&current<='f') || ('A'<=current&&current<='F') ))) && current != EOF ){
					raw.append((char)current);
					read();
				}
				
				//Error: did not find any numbers
				if(raw.length() == 0){
					err("Invalid numerical character reference: \"&#"+current+"\"...");
					if (current != EOF) unConsume((char)current);
					unConsume('#');
					return NOP;
				}
				
				//Error: no closing ';'
				if(current != ';'){
					err("Unclosed numerical character reference.");
					if (current != EOF) unConsume((char)current);
					for(int i=raw.length()-1; i>=0; i--){
						unConsume(raw.charAt(i));
					}
					unConsume('#');
					return NOP;
				}
				
				//Parse numerics
				int code = Integer.parseInt(raw.toString(), (hex?16:10));
				//Genius gigaswitch to filter some stuff
				char ret = '\0';
				switch(code){
					case 0x00: ret = '\uFFFD'; break; case 0x0D: ret = '\r';     break;
					case 0x80: ret = '\u20AC'; break; case 0x81: ret = '\u0081'; break;
					case 0x82: ret = '\u201A'; break; case 0x83: ret = '\u0192'; break;
					case 0x84: ret = '\u201E'; break; case 0x85: ret = '\u2026'; break;
					case 0x86: ret = '\u2020'; break; case 0x87: ret = '\u2021'; break;
					case 0x88: ret = '\u02C6'; break; case 0x89: ret = '\u2030'; break;
					case 0x8A: ret = '\u0160'; break; case 0x8B: ret = '\u2039'; break;
					case 0x8C: ret = '\u0152'; break; case 0x8D: ret = '\u008D'; break;
					case 0x8E: ret = '\u017D'; break; case 0x8F: ret = '\u008F'; break;
					case 0x90: ret = '\u0090'; break; case 0x91: ret = '\u2018'; break;
					case 0x92: ret = '\u2019'; break; case 0x93: ret = '\u201C'; break;
					case 0x94: ret = '\u201D'; break; case 0x95: ret = '\u2022'; break;
					case 0x96: ret = '\u2013'; break; case 0x97: ret = '\u2014'; break;
					case 0x98: ret = '\u02DC'; break; case 0x99: ret = '\u2122'; break;
					case 0x9A: ret = '\u0161'; break; case 0x9B: ret = '\u203A'; break;
					case 0x9C: ret = '\u0153'; break; case 0x9D: ret = '\u009D'; break;
					case 0x9E: ret = '\u017E'; break; case 0x9F: ret = '\u0178'; break;
				}
				if(ret != '\0'){
					err("Numerical character reference to an illegal character");
					return new char[]{ret};
				}else if (code > Character.MAX_VALUE){
					err("Numerical character reference is out of range");
					return new char[]{'\uFFFD'};
				}else if(0xD800<=code && code<0xDFFF){
					err("Numerical character reference between 0xD800 and 0xDFFF");
					return new char[]{'\uFFFD'};
				}else{
					if (code==0x000B || code==0xFFFE || code==0xFFFF || 0x0001<=code&&code<=0x0008
								|| 0x0001<=code&&code<=0x0008 || 0x000E<=code&&code<=0x001F 
								|| 0x007F<=code&&code<=0x009F || 0xFDD0<=code&&code<=0xFDEF){
						err("Numerical character reference to an illegal character");
					}
					return new char[]{(char)code};
				}
				
				//~~
				
			default:
				if(additional != '\0' && current == additional){
					re = true;
					return NOP;
				}
				
				//Text-based character reference:
				//Let's consume a max. of 50 characters
				char[] proto = new char[50];
				int n;
				for(n=0; n<50; ){
					switch(current){
						default:
							if(additional == '\0' || current != additional){
								proto[n++] = (char)current;
								break;
							}
//						case additional:
						case '\t':
						case '\n':
						case '\f':
						case ' ':
						case '<':
						case EOF:
							for(int i=n-1; i>=0; i--){
								unConsume(proto[i]);
							}
							return NOP;
							
						case ';':
							String ref = new String(proto, 0, n);
							String repl = HTML_ENTITIES.get(ref);
							if(repl == null){
								err("Unknown named entity: \""+ref+'\"');
								return new char[]{};
							}else{
								return repl.toCharArray();
							}
					}
					read();
				}
				//Read in 50 chars, could not find a single thing...
				for(int i=n-1; i>=0; i--){
					unConsume(proto[i]);
				}
				return NOP;
				
		}
	}
	
	
	//~~
	

	/**
	 * Emits a Character Token.
	 * @param c - value of the Token
	 */
	private void emit(int c){
		if(c == -1){
			end = true;
		}else if(c >= 0){
			emit(new HtmlCharToken((char)c));
		}else{
			throw new IllegalArgumentException(c+" out of range!");
		}
	}
	
	/**
	 * Emits a Token.
	 * @param t - Token to emit
	 */
	private void emit(HtmlToken t){
		queue.add(t);
	}
	
	//~~
	
	
	/**
	 * Initializes a new Tag Token (resets {@link #tagName} and {@link #attributes}).
	 * @param isEndTag - true, if this is an end tag.
	 */
	private void newTag(boolean isEndTag){
		endTag = isEndTag;
		tagName.setLength(0);
		attributes = new HashSet<>();
	}

	/**
	 * Emits the current Tag Token.
	 * @param isSelfClosing - true, if this is a self closing tag.
	 */
	private void emitTag(boolean isSelfClosing){
		String name = tagName.toString();
		
		//Convert attributes to attribute tokens
		int i = 0;
		HtmlAttributeToken[] attrs = new HtmlAttributeToken[attributes.size()];
		for(Attribute attr:attributes){
			attrs[i++] = new HtmlAttributeToken(attr.name, attr.value);
		}
		
		//Push the fresh Tag Token!
		emit(new HtmlTagToken(name, attrs, endTag, isSelfClosing));
		if (!endTag) lastOpenTag=name;
	}
	
	/**
	 * Initializes a new Attribute Token (resets {@link #attrName} and {@link #attrValue}).
	 */
	private void newAttr(){
		attrName.setLength(0);
		attrValue.setLength(0);
	}
	
	/**
	 * Adds the current Attribute Token to the current Tag Token.
	 * @throws MalformedHtmlException if the {@link #die} flag is set and an Attrubite was already emitted with the same name.
	 */
	private void emitAttr() throws MalformedHtmlException{
		String name = attrName.toString();
		Attribute attr = new Attribute(name, attrValue.toString());
		if(attributes.contains(attr)){
			err("The Tag Token already contains a(n) "+name+" attribute.");
		}else{
			attributes.add(attr);
		}
	}
	
	
	//~~
	
	
	/**
	 * Initializes a new Comment Token (resets {@link #comment}).
	 */
	private void newComment(){
		comment.setLength(0);
	}

	/**
	 * Emits the current Comment Token.
	 */
	private void emitComment(){
		emit(new HtmlCommentToken(comment.toString()));
	}
	
	
	//~~
	
	
	/**
	 * Initializes a new Doctype Token (resets {@link #docName}, {@link #docPublic}, {@link #docSystem} and {@link #docForceQuirks}).
	 */
	private void newDoc(){
		docName.setLength(0);
		docPublic.setLength(0);
		docSystem.setLength(0);
		docForceQuirks = false;
	}

	/**
	 * Emits the current Doctype Token.
	 */
	private void emitDoc(){
		emit(new HtmlDoctypeToken(
				docName.toString(), docPublic.toString(), docSystem.toString(), docForceQuirks
		));
	}
	
	
	//~~
	
	
	/**
	 * Checks if a StringBuilder's contents equal to a String without calling {@link StringBuilder#toString()}.
	 * @param sb - the StringBuilder
	 * @param str - the String
	 * @return True, if sb.toString().equals(str) would be true, false otherwise.
	 */
	private boolean equals(StringBuilder sb, String str){
		if(sb == null && str != null){
			return false;
		}else if(sb != null && str == null){
			return false;
		}else if(sb.length() != str.length()){
			return false;
		}else{
			for(int i=0; i<sb.length(); i++){
				if(sb.charAt(i) != str.charAt(i)){
					return false;
				}
			}
			return true;
		}
	}
	
	
	//~~
	
	
	/**
	 * Checks if the char is an ASCII letter.
	 * @param c - character to check
	 * @return True, if the char is an ASCII letter.
	 */
	private boolean isAsciiLetter(int c){
		//if [a-zA-Z]
		return isLowerAscii(c) || isUpperAscii(c);
	}
	
	/**
	 * Checks if the char is a lowercase ASCII letter.
	 * @param c - character to check
	 * @return True, if the char is a lowercase ASCII letter.
	 */
	private boolean isLowerAscii(int c){
		//if [a-z]
		return ('a' <= c && c <= 'z');
	}
	
	/**
	 * Checks if the char is an uppercase ASCII letter.
	 * @param c - character to check
	 * @return True, if the char is an uppercase ASCII letter.
	 */
	private boolean isUpperAscii(int c){
		//if [A-Z]
		return ('A' <= c && c <= 'Z');
	}
	
	//~~
	
	
	/**
	 * Converts a char to loweracase.
	 * @param c - character to convert.
	 * @return The lowercase c
	 */
	private char lower(int c){
		return Character.toLowerCase((char)c);
	}
	
	
	//~~
	
	
	/**
	 * Emits a character token for each of the characters in the buffer (in the order they were added to it).<br/>
	 * Does not clear the buffer!
	 */
	private void emitBuffer(){
		for(int i=0; i<bi; i++){
			emit(buffer[i]);
		}
	}
	
	/**
	 * Adds a character to the buffer.
	 * @param c - character to add
	 */
	private void buff(char c){
		buffer[bi++] = c;
		//Damn resize!
		if(buffer.length == bi){
			char[] old = buffer;
			buffer = new char[buffer.length*2];
			//Totally ineffective!
			//2KB should be enough, K??
			System.arraycopy(old, 0, buffer, 0, old.length);
		}
	}
	
	
	//~~
	
	
	/**
	 * Unconsumes a character. <b>WARNING:</b> it's a stack, so unconsome everything
	 * in <b>REVERSE</b> order!
	 * @param c - character to unconsume
	 */
	private void unConsume(char c){
		un = true;
		unBuffer[uni++] = c;
		//Damn resize!
		if(unBuffer.length == uni){
			char[] old = unBuffer;
			unBuffer = new char[unBuffer.length*2];
			//Totally ineffective!
			//1KB should be enough, K??
			System.arraycopy(old, 0, unBuffer, 0, old.length);
		}
	}
	
	
	//~~
	
	
	/**
	 * Reads
	 * @return The next character, or -1 if the end of the stream is reached.
	 * @throws IOException if reading the input stream fails for whatever reason
	 */
	private int read() throws IOException{
		if(re){
			re = false;
		}else if(un){
			current = unBuffer[uni--];
			if(uni==-1){
				uni = 0;
				un = false;
			}
		}else{
			current = input.read();
		}
		return current;
	}

	/**
	 * Throws a MalformedHtmlException with the message if the die flag is set
	 * @param message - short description of the cause
	 * @throws MalformedHtmlException if the {@link #die} flag is set
	 */
	private void err(String message) throws MalformedHtmlException{
		if(die){
			throw new MalformedHtmlException(message);
		}
	}
}
