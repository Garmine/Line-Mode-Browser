package net.garmine.network;

/**
 * Encapsulates any Exceptions happened while processing a Request or a Response.
 * @see Request
 * @author Garmine
 */
public class NetworkException extends Exception {
	private static final long serialVersionUID = 1070456285287105571L;
	
	/**
	 * Constructs a new NetworkException encapsulating the cause.
	 * @param t - cause
	 */
	public NetworkException(Throwable t){
		super(t);
	}
}