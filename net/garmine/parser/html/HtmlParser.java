package net.garmine.parser.html;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

import net.garmine.parser.Parser;
import net.garmine.parser.html.tokenizer.HtmlTokenizer;
import net.garmine.parser.html.tokenizer.tokens.*;

import static net.garmine.parser.html.HtmlElements.HTML_ELEMENTS;
import static net.garmine.parser.html.tokenizer.tokens.HtmlTokenType.*; 
import static net.garmine.parser.html.elements.HtmlElementType.*; 

public class HtmlParser implements Parser<HtmlDocument> {
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
			switch(type){
				case CHAR:
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
					break;
					
				case COMMENT:
					if(!tree.isEmpty()){
						ret =  new HtmlComment(tree.peek(), token.getComment());
					}else{
						ret =  new HtmlComment(null, token.getComment());
					}
					
				case DOCTYPE:
					//Um...? Do something with it?
					continue;
					
				case TAG:
					
					break;
					
				default:
					//This should never ever happen...
					ret = null;
			}
			
			return ret;
		}
		
		if(chars.length() != 0){
			//We still have a text to emit!
			String text = chars.toString();
			chars.setLength(0);
			
			if(!tree.isEmpty()){
				//Text is inside some element
				HtmlElement e = tree.peek();
				if(die && (e.getType() == HTML || e.getType() == HEAD)){	//TODO: which are invalid elements?
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
		
		//we have run out of tokens yet someone keeps asking for more nodes....
		return null;
	}
	
	public boolean hasNext(){
		return tokenizer.hasNext();
	}
	
	@Override
	public HtmlDocument parse(Reader in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
