package net.garmine.parser.html;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import net.garmine.parser.html.tokenizer.HtmlTokenizer;
import net.garmine.parser.html.tokenizer.tokens.*;

import static net.garmine.parser.html.HtmlElements.HTML_ELEMENTS;
import static net.garmine.parser.html.tokenizer.tokens.HtmlTokenType.*; 
import static net.garmine.parser.html.elements.HtmlElementType.*; 

public class HtmlParser {
	private final HtmlTokenizer tokenizer;
	private final StringBuilder chars = new StringBuilder(128);
	private final boolean die;
	
	private HtmlTokenType last = null;
	private boolean lastCharIsWS = true;
	private Stack<HtmlElement> tree;
	
	private HtmlToken tmp;
	
	/**
	 * Constructs a new HtmlParser
	 * @param in - the input stream
	 * @param die - if true a malformed HTML will cause a {@link MalformedHtmlException} during processing
	 */
	public HtmlParser(Reader in, boolean die){
		if (in==null) throw new NullPointerException("In must not be null!");
		this.die = die;
		tokenizer = new HtmlTokenizer(in, this.die);
		tree = new Stack<>();
	}
	/**
	 * 
	 * @return The next element in the HTML tree.
	 * @throws IOException - if an IOException occurs while processing the input stream
	 * @throws MalformedHtmlException - if the {@link #die} flag is set and  
	 */
	public HtmlNode next() throws IOException, MalformedHtmlException{
		//Process next token(s)!
		while(tokenizer.hasNext()){
			HtmlToken token;
			if(tmp != null){
				//We still have an unprocessed token!
				token = tmp;
				tmp = null;
			}else{
				//New token yay!
				token = tokenizer.next();
			}
			HtmlTokenType type = token.getType();
			
			HtmlNode ret = null;
			if(type!=CHAR && last==CHAR){
				//We shall emit an HtmlText instead of processing the next Token
				tmp = token;
				ret = makeText();
				lastCharIsWS = true;
			}else{
				//We shall process the next token!
				switch(type){
					case CHAR:
						//Texts are released as lots of character tokens:
						//We shall collect them, and release a single HtmlText Object
						if (last!=CHAR) chars.setLength(0);
						char c = token.getChar();
						if(Character.isWhitespace(c)){
							if(!lastCharIsWS){	//TODO: <pre>
								chars.append(' ');
							}
							lastCharIsWS = true;
						}else{
							chars.append(c);
							lastCharIsWS = false;
						}
						continue;
						
					case COMMENT:
						if(!tree.isEmpty()){
							ret =  new HtmlComment(tree.peek(), token.getComment());
						}else{
							ret =  new HtmlComment(null, token.getComment());
						}
						break;
						
					case DOCTYPE:
						//TODO: Um...? Do something with it?
						continue;
						
					case TAG:
						Class<? extends HtmlElement> elementType = HTML_ELEMENTS.get(token.getName());
						if(elementType != null){
							try{
								Constructor<? extends HtmlElement> constructor = elementType.getConstructor(HtmlElement.class, HtmlAttributeToken[].class);
								//TODO: implement legal children check
								HtmlElement element = constructor.newInstance((tree.isEmpty()?null:tree.peek()), token.getAttributes());
								
								if(!token.isEndTag()){
									if(!token.isSelfClosing() || element.getType().hasCloseTag()){
										//Which has an end tag and is not closed
										tree.push(element);
									}
									
									//Return element
									ret = element;
								}else{
									if(tree.peek().getType() == element.getType()){
										//Tag is closed
										tree.pop();
									}else if(die){
										//Invalid close tag
										throw new MalformedHtmlException();
									}//else: ignore!
									
									//Nothing to return, we shall process (yet) another token
									continue;
								}
							}catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
								//This should never ever happen.... LOL
								assert false : e.getMessage();
							}
						}else if(die){
							throw new MalformedHtmlException();
						}else{
							continue;
						}
						break;

						/*
						 * This can only happen on EOF or ATTRIBUTE (or null),
						 * none of which shall ever be emitted by the tokenizer directly
						 * if HtmlTokenirer#hasNext() returns true!
						 */
					default: assert false : type;
				}
			}
			
			last = type;
			assert ret != null : "Missing a \"continue\" somewhere!";
			return ret;
		}

		//Do we have a text to emit?
		if (chars.length()!=0) return makeText();
		
		//we have run out of tokens yet someone keeps asking for more nodes....
		return null;
	}
	
	public boolean hasNext(){
		return tokenizer.hasNext();
	}
	
	private HtmlText makeText() throws MalformedHtmlException{
		String text = chars.toString();
		chars.setLength(0);
		
		if(!tree.isEmpty()){
			//Text is inside some element
			HtmlElement e = tree.peek();
			//TODO: implement legal children check
			if(die && (e.getType() == HTML || e.getType() == HEAD)){
				//Text inside invalid element!
				throw new MalformedHtmlException();
			}else{
				//Oh, everything's fine
				return new HtmlText(e, text);
			}
		}else if(die){
			//Text cannot be root!
			throw new MalformedHtmlException();
		}else{
			//Bah, who cares?
			return new HtmlText(null, text);
		}
	}
}
