package net.garmine.parser.json;

/**
 * Exception which represents errors caused by parsing malformed JSONs
 * @author Garmine
 */
@SuppressWarnings("serial")
public class MalformedJsonException extends Exception{
	public MalformedJsonException(){}
	public MalformedJsonException(String err){
		super(err);
	}
}