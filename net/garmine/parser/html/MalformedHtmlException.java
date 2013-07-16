package net.garmine.parser.html;

/**
 * Exception which represents errors caused by parsing malformed HTMLs
 * @author Garmine
 */
@SuppressWarnings("serial")
public class MalformedHtmlException extends Exception{
	public MalformedHtmlException(){}
	public MalformedHtmlException(String err){
		super(err);
	}
}