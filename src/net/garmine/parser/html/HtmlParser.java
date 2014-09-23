package net.garmine.parser.html;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import net.garmine.parser.html.elements.HtmlElementType;
import net.garmine.parser.html.nodes.HtmlComment;
import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.nodes.HtmlNode;
import net.garmine.parser.html.nodes.HtmlText;
import net.garmine.parser.html.tokenizer.HtmlTokenizer;
import net.garmine.parser.html.tokenizer.tokens.*;

import static net.garmine.parser.html.tokenizer.tokens.HtmlTokenType.*; 
import static net.garmine.parser.html.elements.HtmlElementType.*; 
import static net.garmine.parser.html.nodes.HtmlNodeType.*;
import static net.garmine.parser.html.nodes.HtmlElements.HTML_ELEMENTS;

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
	
	/** Flag if this is the first call: if it is we shall return the root element (an HtmlDocument) */
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
	 *//*	
	 * DOES NOT WORK! 
	 * Reason: 
	 * 		HtmlTokenizer#hasNext() returns true when it has any character 
	 * 		left, which includes any whitespace characters, however, the 
	 * 		Parser WILL NOT return texts which contain ONLY whitespace.
	 * 		That's why it's possible for HtmlParser#hasNext() to return 
	 * 		false-positives which is NOT wanted....
	 * 
	public boolean hasNext(){
		return first || (tmp!=null) || tokenizer.hasNext() || (chars.length()>0);
	}
	*/
	
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
		while(true){
			HtmlToken token;
			if(tmp != null){
				//We still have an unprocessed token!
				token = tmp;
				tmp = null;
			}else{
				//New token yay!
				token = tokenizer.next();
				if (token==HtmlEofToken.EOF) break;
			}
			last = type;
			type = token.getType();
			
			HtmlNode ret = null;
			if(type!=CHAR && last==CHAR && chars.length()>0){
				//We shall emit an HtmlText instead of processing the next Token
				tmp = token;
				ret = makeText();
			}else{
				//We shall process the next token!
				switch(type){
					case CHAR:
						//Texts are released as lots of character tokens:
						//We shall collect them, and release a single HtmlText Object
						char c = token.getChar();
						//TODO: <pre>
						if(Character.isWhitespace(c)){
							if(!lastCharIsWS){
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
						
						//Get the type of the Tag Token
						HtmlElementType et = HTML_ELEMENTS.get(token.getName());
						if(et != null){
							//It's a known element
							if(!token.isEndTag()){
								//It's an open tag, we shall return something
								tokenizer.switchTo(et.getTokenizingState());
								
								//TODO: implement legal parent/children check
								
								HtmlElement element;
								try{
									element = et.getConstructor().newInstance(tree.peek(), token.getAttributes());
								}catch(IllegalAccessException | InstantiationException | InvocationTargetException e){
									//This should never ever happen....
									assert false : e;
									//And now wtf?
									break;
								}
								
								//Can this tag have any children?
								if(!token.isSelfClosing() && et.hasCloseTag()){
									tree.push(element);
								}
								
								//Return element
								ret = element;
								break;
							}else{
								//It's a close tag, we shall return nothing
								if(et.hasCloseTag() && tree.peek().is(ELEMENT) && tree.peek().asElement().getType() == et){
									//Tag is closed
									tree.pop();
									//Nothing to return, we shall process (yet) another token
									break;
								}else{
									err("Invalid close tag");
									break;
								}
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
		if (chars.length()>0) return makeText();
		
		//We have run out of tokens yet someone keeps asking for more nodes....
		return null;
	}
	
	/**
	 * Constructs a new HtmlText and resets {@link #chars}
	 * @return An HtmlText node
	 * @throws MalformedHtmlException - if the {@link #die} flag is set and the text's in an illegal place
	 */
	private HtmlText makeText() throws MalformedHtmlException{
		assert !tree.isEmpty() : "Missing HtmlDocument from root";
		assert chars.length()>0 : "Empty buffer";
		
		//Construct text and chop trailing whitespaces if necessary
		String text = chars.toString();
		chars.setLength(0);
		lastCharIsWS = true;
		//Len must be 
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
