package net.garmine.http;

/**
 * Encapsulates any Exceptions happened while processing a Request or a Response.
 * @see HttpRequest
 * @author Garmine
 */
public class HttpException extends Exception {
	private static final long serialVersionUID = 1070456285287105571L;
	
	/**
	 * Constructs a new NetworkException encapsulating the cause.
	 * @param t - cause
	 */
	public HttpException(Throwable t){
		super(t);
	}
}