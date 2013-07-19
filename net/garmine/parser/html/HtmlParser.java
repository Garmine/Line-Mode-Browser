package net.garmine.parser.html;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import net.garmine.parser.html.nodes.HtmlComment;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.nodes.HtmlNode;
import net.garmine.parser.html.nodes.HtmlText;
import net.garmine.parser.html.tokenizer.HtmlTokenizer;
import net.garmine.parser.html.tokenizer.tokens.*;

import static net.garmine.parser.html.nodes.HtmlElements.HTML_ELEMENTS;
import static net.garmine.parser.html.tokenizer.tokens.HtmlTokenType.*; 
import static net.garmine.parser.html.elements.HtmlElementType.*; 
import static net.garmine.parser.html.nodes.HtmlNodeType.*;

/**
 * The class which constructs HTML trees from HTML documents.
 * @author Garmine
 */
public class HtmlParser {
	/** The Tokenizer inbetween the Parser and the input stream. */
	private final HtmlTokenizer tokenizer;
	/** Temporary buffer  */
	private final StringBuilder chars = new StringBuilder(128);
	/** Flag if Parser shall die on first error */
	private final boolean die;
	
	/** Contains the currently open nodes of the tree. The top one is always the deepest node. */
	private Stack<HtmlMidNode> tree;
	
	/** Type of the previous HTML Token. */
	private HtmlTokenType last = null;
	/** Type of the current HTML Token. */
	private HtmlTokenType type = null;
	
	/**  */
	private boolean first = true;
	/** Flag if last Character Token was a whitespace character. */
	private boolean lastCharIsWS = true;

	/** Temporary storage when we release an HtmlText node */
	private HtmlToken tmp;
	
	/**
	 * Constructs a new HtmlParser
	 * @param in - the input stream
	 * @param die - if true a malformed HTML will cause a {@link MalformedHtmlException} during processing
	 * @throws IOException - if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException - if the die flag is set and the initialization fails due to malformed HTML
	 */
	public HtmlParser(Reader in, boolean die) throws IOException, MalformedHtmlException{
		if (in==null) throw new NullPointerException("In must not be null!");
		this.die = die;
		tokenizer = new HtmlTokenizer(in, this.die);
		tree = new Stack<>();
		tree.push(new HtmlDocument(null));
	}

	/**
	 * Checks if there are any Nodes left.
	 * @return True, if end of stream has not yet been reached or there's still Nodes left to be emitted.
	 */
	public boolean hasNext(){
		return (tmp!=null) || tokenizer.hasNext();
	}
	
	/**
	 * Processes the next Node in the HTML tree.
	 * @return The next Node in the HTML tree, or null if the end of stream has been reached.
	 * @throws IOException - if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException - if the {@link #die} flag is set and parsing fails due to malformed HTML
	 */
	public HtmlNode next() throws IOException, MalformedHtmlException{
		if(first){
			//First call, we shall return the root element
			first = false;
			return tree.peek();
		}
		
		//Process next token(s)!
		while(tokenizer.hasNext()){
			last = type;
			HtmlToken token;
			if(tmp != null){
				//We still have an unprocessed token!
				token = tmp;
				tmp = null;
			}else{
				//New token yay!
				token = tokenizer.next();
			}
			type = token.getType();
			
			HtmlNode ret = null;
			if(type!=CHAR && last==CHAR && chars.length()>0){
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
						break;
						
						//~~
						
					case COMMENT:
						assert !tree.isEmpty() : "Missing HtmlDocument from root";
						ret =  new HtmlComment(tree.peek(), token.getComment());
						
						//~~
						
					case DOCTYPE:
						//TODO: Um...? Do something with it?
						break;
						
						//~~
						
					case TAG:
						assert !tree.isEmpty() : "Missing HtmlDocument from root";
						
						Class<? extends HtmlElement> elementType = HTML_ELEMENTS.get(token.getName());
						if(elementType != null){
							//It's a known element
							try{
								Constructor<? extends HtmlElement> constructor = elementType.getConstructor(HtmlMidNode.class, HtmlAttributeToken[].class);
								//TODO: implement legal parent/children check
								HtmlElement element = constructor.newInstance(tree.peek(), token.getAttributes());
								
								if(!token.isEndTag()){
									//It's an open tag, we shall return something
									if(!token.isSelfClosing() || element.getType().hasCloseTag()){
										//Which has an end tag and is not closed
										tree.push(element);
									}
									//Return element
									ret = element;
									break;
								}else{
									//It's a close tag, we shall return nothing
									if(tree.peek().is(ELEMENT) && tree.peek().asElement().getType() == element.getType()){
										//Tag is closed
										tree.pop();
										//Nothing to return, we shall process (yet) another token
										break;
									}else{
										err("Invalid close tag");
										break;
									}
								}
							}catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
								//This should never ever happen....
								assert false : e.getMessage();
								//And now wtf?
								break;
							}
						}else{
							err("Unknown element: \""+token.getName()+'\"');
							break;
						}
						
						//~~

						/*
						 * This can only happen on EOF, ATTRIBUTE (or null),
						 * none of which shall ever be emitted by the tokenizer 
						 * directly if HtmlTokenizer#hasNext() returns true!
						 */
					default: assert false : type;
				}
			}
			
			//We shall not return null, but it may be 
			//used to indicate the need for a new Token
			if (ret==null) continue;
			return ret;
		}

		//Do we have a text to emit?
		if (chars.length()!=0) return makeText();
		
		//we have run out of tokens yet someone keeps asking for more nodes....
		return null;
	}
	
	/**
	 * Constructs a new HtmlText and resets {@link #chars}
	 * @return An HtmlText node
	 * @throws MalformedHtmlException - if the {@link #die} flag is set and the text's in an illegal place
	 */
	private HtmlText makeText() throws MalformedHtmlException{
		assert !tree.isEmpty() : "Missing HtmlDocument from root";
		
		//Construct text and chop trailing whitespaces if necessary
		String text = chars.toString();
		chars.setLength(0);
		int len = text.length();
		if(text.charAt(len-1) == ' '){
			text = text.substring(0, --len);
		}
		
		//Text is inside some element
		HtmlMidNode n = tree.peek();
		if(n.is(ELEMENT)){
			//This is an HtmlElement
			HtmlElement e = n.asElement();
			
			//TODO: implement real legal parent/children check
			if (e.getType() == HTML || e.getType() == HEAD){
				//Text inside invalid element!
				err("Text inside an illegal element");
			}
			return new HtmlText(e, text);
		}else{
			//This is an HtmlDocument
			err("Text inside root");
			return new HtmlText(n, text);
		}
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
