package net.garmine.network;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import net.garmine.parser.Parser;
import net.garmine.parser.json.JsonParser;

/**
 * This class provides utilities for handling requests/responses through the network.<br>
 * Also, it's concurrent ;)
 * 
 * @author Garmine
 */
public abstract class Network {
	
	//=======================================================
	//Default stuff storage shit
	/** UserAgent String */
	private String userAgent = null;
	/** Default URL */
	protected String url = null;
	/** Proxy (if any) */
	private Proxy proxy = null;
	/** The Proxy represented as a String: "server:port", or "default" if none */
	private String proxyString = "default";
	/** Default request type */
	protected ResponseType defaultType = ResponseType.NULL;
	/** String -> String mapping of HTTP Headers */
	protected HashMap<String, String> requestProperty = new HashMap<String, String>();
	/** String -> String mapping of Cookies */
	protected HashMap<String, String> cookies = new HashMap<String, String>();
	@SuppressWarnings("rawtypes")
	/** Contains default parsers */
	protected HashMap<ResponseType, Parser> defaultParsers = new HashMap<ResponseType, Parser>();
	
	//=======================================================
	//Request handling stuff
	/** Index: last free id for new Requests */
	private Long index = Long.MIN_VALUE;
	/** Contains sent requests. Key is the Request's unique id. */
	private HashMap<Long, Request> requests = new HashMap<Long, Request>();
	/** Contains unsent but initialized Requests. Key is the Thread's id for concurrency. */
	private HashMap<Long, Request> stateMachine = new HashMap<Long, Request>();
	
	//=======================================================
	//Constructor
	/** Constructs a new Network, and initializes default defaultParsers */
	public Network(){
		defaultParsers.put(ResponseType.NULL, null);
		defaultParsers.put(ResponseType.SOURCE, null);
		defaultParsers.put(ResponseType.JSON, new JsonParser());
	}

	//==============================================================\\
	//Setters														||
	//==============================================================//
	/**
	 * Sets the User Agent and also places it in the {@link #requestProperty}.<br>
	 * If the userAgent param is null it deletes the User Agent.
	 * @param userAgent - The User Agent String.
	 */
	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
		if(userAgent != null){
			requestProperty.put("User-Agent", userAgent);
		}else{
			requestProperty.remove("User-Agent");
		}
	}
	/**
	 * Sets the default url.
	 * @param url - String representing the URL.
	 */
	public void setUrl(String url){
		this.url = url;
	}
	/**
	 * Sets the default proxy from the given String and with the default port (8080).<br>
	 * Also modifies the {@link #proxyString} according to the new value.
	 * @param server - String representing the Proxy Server.
	 */
	public void setProxy(String server){
		setProxy(server, 8080);
		proxyString = server+":8080";
	}
	/**
	 * Sets the default proxy from the given String and port.<br>
	 * Also modifies the {@link #proxyString} according to the new value.
	 * @param server - String representing the Proxy Server.
	 * @param port - port of the server.
	 */
	public void setProxy(String server, int port){
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(server, port));
		proxyString = server+":"+port;
	}
	/**
	 * Deletes the default Proxy, and sets the {@link #proxyString} to "default".<br>
	 * The class will use the JVM default proxy from now on!
	 */
	public void delProxy(){
		proxy = null;
		proxyString = "default";
	}
	/**
	 * Sets the default type for the Requests.
	 * @param defaultType - the default type.
	 */
	public void setDefaultType(ResponseType defaultType){
		this.defaultType = defaultType;
	}
	/**
	 * Sets the default parser for the given type.
	 * @param type - type of parser
	 * @param parser - parser to set
	 */
	@SuppressWarnings("rawtypes")
	public void setDefaultParser(ResponseType type, Parser parser){
		defaultParsers.put(type, parser);
	}
	/**
	 * Deletes default parser for the given type.
	 * @param type - type to delete
	 */
	public void delDefaultParser(ResponseType type){
		defaultParsers.remove(type);
	}

	//==============================================================\\
	//Getters														||
	//==============================================================//
	/**
	 * Gets the userAgent.
	 * @return {@link #userAgent}
	 */
	public String getUserAgent(){
		return userAgent;
	}
	/**
	 * Gets the default url.
	 * @return {@link #url}
	 */
	public String getUrl(){
		return url;
	}
	/**
	 * Gets the Proxy.
	 * @return {@link #proxy}
	 */
	public Proxy getProxy(){
		return proxy;
	}
	/**
	 * Gets the Proxy as a String: "server:port", or "default" if none.
	 * @return {@link #proxyString}
	 */
	public String getProxyString(){
		return proxyString;
	}
	/**
	 * Gets the (default) Request properties.
	 * @return {@link #requestProperty}
	 */
	public HashMap<String, String> getRequestProperty(){
		return this.requestProperty;
	}
	/**
	 * Gets the Default Type of requests.
	 * @return {@link #defaultType}
	 */
	public ResponseType getDefaultType(){
		return defaultType;
	}
	/**
	 * Gets the default parser for the given type.
	 * @param type - parser's type.
	 * @return Default parser for the given type, or null, if none set.
	 */
	@SuppressWarnings("rawtypes")
	public Parser getDefaultParser(ResponseType type){
		return defaultParsers.get(type);
	}

	//==============================================================\\
	//Data utilities												||
	//==============================================================//
	/**
	 * Constructs a String for HTTP transmission from input arrays
	 * @param keys - keys
	 * @param values - values
	 * @param prefix - will be appended before
	 * @return the constructed String
	 */
	protected String constructData(String prefix, String[] keys, String[] values){
		if(prefix == null){
			throw new NullPointerException("Prefix mustn't be null!");
		}
		return prefix + constructData(keys, values);
	}
	/**
	 * Constructs a String for HTTP transmission from input arrays
	 * @param keys - keys
	 * @param values - values
	 * @return the constructed String
	 */
	protected String constructData(String[] keys, String[] values){
		//checks
		if(keys==null || values==null){
			throw new NullPointerException("Arguments mustn't be null!");
		}
		if(keys.length < 1 || values.length < 1){return "";}
		
		//construct
		StringBuilder data = new StringBuilder();
		try{
			
		    data.append(keys[0]).append("=").append(URLEncoder.encode(values[0], "UTF-8"));
		    
		    for(int i=1; i<keys.length; i++){
		    	if (i == values.length){break;}
		    	data.append("&").append(keys[i]).append("=").append(URLEncoder.encode(values[i], "UTF-8"));
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return data.toString();
	}
	
	/**
	 * Constructs a String for HTTP transmission from input HashMap
	 * @param data - keys & values
	 * @param prefix - will be appended before
	 * @return the constructed String
	 */
	protected String constructData(String prefix, HashMap<String, String> data){
		if(prefix == null){
			throw new NullPointerException("Prefix mustn't be null!");
		}
		return prefix + constructData(data);
	}
	/**
	 * Constructs a String for HTTP transmission from input HashMap
	 * @param in - keys & values
	 * @return the constructed String
	 */
	protected String constructData(HashMap<String, String> in){
		//checks
		if(in==null){
			throw new NullPointerException("Data mustn't be null!");
		}
		if(in.size() < 1){return "";}
		
		//construct
		StringBuilder data = new StringBuilder();
		try{
			boolean start = false;
			for(Entry<String, String> entry : in.entrySet()){
				if(start){
					data.append("&");
				}
				data.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				start = true;
			}
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return data.toString();
	}
	
	/**
	 * Constructs a String for HTTP transmission from input Strings.<br><br>
	 * If the length of the sequence is:
	 * <pre>-0: returns ""</pre>
	 * <pre>-1: returns data[0](=prefix)</pre>
	 * <pre>-odd: returns data[0](=prefix) + key/value pairs</pre>
	 * <pre>-even: returns key/value pairs</pre>
	 * @param data - String sequence in the form: [prefix] [key, value, [key, value...]]
	 * @return the constructed String
	 */
	protected String constructData(String... data){
		if(data==null){
			throw new NullPointerException("Data mustn't be null!");
		}
		
		//empty
		if(data.length == 0){
			return "";
		}
		
		//init
		StringBuilder ret = new StringBuilder();
		int i = 0;
		
		//has prefix, so:
		//treat data[0] as prefix, and skip it
		if(data.length % 2 == 1){
			i++;
			ret.append(data[0]);
		}
		
		//append remaining key/value pairs
		try{
			boolean start = false;
			for(; i<data.length; i+=2){
				if(start){
					ret.append("&");
				}
				ret.append(data[i]).append("=").append(URLEncoder.encode(data[i+1], "UTF-8"));
				start = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret.toString();
	}

	//==============================================================\\
	//Cookie utilities												||
	//==============================================================//
	/**
	 * Makes a String from the desired cookies (all if no desired) for HTTP transmission
	 * @param names - desired cookies
	 * @return the constructed String
	 */
	protected String constructCookies(String... names){
		StringBuilder ret = new StringBuilder();
		
		//syntax:
		//name1=value1; name2=value2
		
		if(names != null && names.length != 0){
			//put in name + value 
			boolean start = false;
			for(int i=0; i<names.length; i++){
				if(start){
					ret.append("; ");
				}
				ret.append(names[i]).append("=").append(cookies.get(names[i]));
				start = true;
			}
		}else{
			//put in everything
			boolean start = false;
			for(Entry<String, String> entry : cookies.entrySet()){
				if(start){
					ret.append("; ");
				}
				ret.append(entry.getKey()).append("=").append(entry.getValue());
				start = true;
			}
		}
		
		return ret.toString();
	}
	
	/**
	 * Adds all elements in the HashMap to the cookies EXCEPT for special (e.g. "httponly")
	 * @param add - Cookies to add
	 */
	protected void addAllCookies(HashMap<String, String> add){
		for(Entry<String, String> entry : add.entrySet()){
			//filter out special keys
			if(entry.getKey().equalsIgnoreCase("httponly")
					|| entry.getKey().equalsIgnoreCase("secure")
					|| entry.getKey().equalsIgnoreCase("expires")
					|| entry.getKey().equalsIgnoreCase("path")
					|| entry.getKey().equalsIgnoreCase("domain")
					|| entry.getKey().equalsIgnoreCase("max-age")){
				continue;
			}
			//put the rest
			cookies.put(entry.getKey(), entry.getValue());
		}
	}
	
	//==============================================================\\
	//Request utilities												||
	//==============================================================//
	/**
	 * Shortcut for Thread.currentThread().getId();<br>
	 * Used for concurrency by the state machine
	 * @return The current Thread's ID
	 */
	private long ctid(){
		return Thread.currentThread().getId();
	}
	
	/**
	 * Checks request and throws an error if no Request is in the state machine
	 */
	private void checkRequest(){
		if(!stateMachine.containsKey(ctid())){
			throw new IllegalStateException("ERROR: no initialized requests!");
		}
	}
	
	/**
	 * Waits until a Request is ready, then returns it.
	 * @param request - Request to wait for
	 * @return The completed Request
	 */
	private Request waitFor(Request request){
		synchronized (request) {
			while(true){
				try {
					if (!request.isReady()) request.wait();
					break;
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
		return request;
	}
	
	/**
	 * Initializes a new request for the state machine with minimal arguments and the default values.
	 * @param url - String representing the URL of the site (e.g. "http://garmine.example.com/eg.php?id=42&page=7")
	 * @param body - The body of the Request (in case of PUT/POST/OPTIONS)
	 * @param method - the Request Method (e.g. ReqMethod.GET)
	 */
	protected Request request(String url, byte[] body, ReqMethod method){
		if(stateMachine.containsKey(ctid())){
			throw new IllegalStateException("ERROR: request is already initialized!");
		}
		Request ret = new Request(url, body, method, this);
		stateMachine.put(ctid(), ret);
		return ret;
	}
	
	/**
	 * Returns the last Request stored in the state machine for modification
	 * @return current Request
	 */
	protected Request request(){
		checkRequest();
		return stateMachine.get(ctid());
	}
	
	/**
	 * Sends Request (no future modifications are possible!)
	 * @return the ID for future retrieval of the message
	 */
	protected long send(){
		checkRequest();
		Request request = stateMachine.remove(ctid());
		request.send();
		long index = this.index++;
		requests.put(index, request);
		return index;
	}
	
	/**
	 * Sends Request and LOCKS until server responds
	 * @return the preprocessed response for post processing
	 */
	protected Request sendWait(){
		checkRequest();
		Request request = stateMachine.remove(ctid());
		request.send();
		//wait for request
		return preProcessResponse(waitFor(request));
	}
	
	/**
	 * Cancels initialized Request
	 */
	protected void cancelRequest(){
		checkRequest();
		stateMachine.remove(ctid());
	}
	
	/**
	 * Tells if request is completed
	 * @param id - request's ID (got @ <code>send()</code>)
	 * @return true if server responded false otherwise
	 */
	protected boolean isReady(long id){
		if(!requests.containsKey(id)){
			throw new IllegalArgumentException("Invalid id: #"+id);
		}
		return requests.get(id).isReady();
	}
	
	/**
	 * Retrieves and preprocesses response.
	 * @param id - request's ID (got @ send())
	 * @return the preprocessed response for post processing, or null if isn't ready.
	 */
	protected Request getResponse(long id){
		if(!isReady(id)){
			//raise error if request non-exists!
			//return null if isn't ready
			return null;
		}else{
			//get cookies, headers, etc.
			//then return
			return preProcessResponse(requests.remove(id));
		}
	}
	
	/**
	 * Retrieves and preprocesses response. Locks if Request isn't ready.
	 * @param id - request's ID (got @ send())
	 * @return the preprocessed response for post processing
	 */
	protected Request waitResponse(long id){
		if(!isReady(id)){
			//raise error if request non-exists!
			//wait if isn't ready
			return preProcessResponse(waitFor(requests.remove(id)));
		}else{
			//get cookies, headers, etc.
			//then return
			return preProcessResponse(requests.remove(id));
		}
	}
	
	/**
	 * Method for getting cookies, headers, etc. from responses before processing
	 * @param in - Request to preprocess
	 * @return the preprocessed Request for post processing (the implementor MUST return that!)
	 */
	protected abstract Request preProcessResponse(Request in);
}