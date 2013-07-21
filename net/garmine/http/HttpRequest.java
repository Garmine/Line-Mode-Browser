package net.garmine.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.garmine.parser.Parser;
import net.garmine.parser.json.JsonParser;
import net.garmine.http.HttpResponseType;

/**
 * Asynchronous class for handling HTTP requests and parsing the data if specified
 * @author Garmine
 */
public final class HttpRequest implements Runnable {
	
	//==================================================
	//Thread stuff
	/** Thread processing the Request */
	private final Thread t;
	/** The Request's parent */
	private final HttpClient parent;
	/** Flag if server has responded && processed */
	private boolean isReady = false;
	/** Flag if request has send */
	private boolean isStarted = false;
	
	//==================================================
	//request stuff
	/** Network proxy */
	private Proxy proxy;
	/** Target URL */
	private String url;
	/** The body of the Request (in case of PUT/POST/OPTIONS) */
	private byte[] body;
	/** The expected Data type */
	private HttpResponseType type;
	/** The Request Method (e.g. ReqMethod.GET) */
	private HttpMethod method;
	/** The Reqest Properties (i.e. the Reuest's Headers) */
	private HashMap<String, String> requestProperty;
	/** Parser used to parse the expected Data */
	@SuppressWarnings("rawtypes")
	private Parser parser;
	
	//==================================================
	//response stuff
	/** The response's HTML headers */
	private HashMap<String, String> headers;
	/** The response's HTML cookies */
	private HashMap<String, String> cookies;
	/** The Response data/body (if any) */
	private Object data;
	/** The HTML Response Code (e.g. 200) */
	private int responseCode;
	/** The HTML Response Message (e.g. "OK") */
	private String responseMessage;
	/** 
	 * Time of completion in milliseconds.
	 * @see System#currentTimeMillis() 
	 * */
	private long time;
	
	//==================================================\\
	//constructor										||
	//==================================================//
	/**
	 * Makes a new request with the arguments and the DEFAULT values of the parent
	 * @param url - String representing the URL of the site (e.g. "http://garmine.example.com/eg.php?id=42&page=7")
	 * @param body - The body of the Request (in case of PUT/POST/OPTIONS)
	 * @param method - the Request Method (e.g. ReqMethod.GET)
	 * @param parent - MUST BE the Network which initiates this Request!
	 */
	HttpRequest(String url, byte[] body, HttpMethod method, HttpClient parent){
		this.parent = parent;
		
		setProxy(parent.getProxy());
		setUrl(url);
		setBody(body);
		setType(parent.getDefaultType());
		setMethod(method);
		setRequestProperty(parent.getRequestProperty());
		setParser(null);
		
		t = new Thread(this, "Request-"+System.currentTimeMillis()%100);
	}
	
	//==================================================\\
	//setters											||
	//==================================================//
	/**
	 * Forces that Request is <u>NOT</u> started!
	 * @throws IllegalStateException if Request is started.
	 */
	private void forceNotStarted(){
		if (isStarted) throw new IllegalStateException("ERROR: Request already started!");
	}
	/**
	 * Set's the Proxy for this Request.
	 * @param proxy - Proxy to set.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setProxy(Proxy proxy){
		forceNotStarted();
		this.proxy = proxy;
		return this;
	}
	/**
	 * Set's the url for this Request.
	 * @param url - String representing the URL.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setUrl(String url){
		forceNotStarted();
		if (url==null) throw new NullPointerException("Url mustn't be null !");
		this.url = url;
		return this;
	}
	/**
	 * Sets the Request Body.
	 * @param body - The body of the Request (in case of PUT/POST/OPTIONS)
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setBody(byte[] body){
		forceNotStarted();
		//if (body==null) body = new byte[0];
		this.body = body;
		return this;
	}
	/**
	 * Set's the HTML method (e.g. POST) of this Request.
	 * @param method - method to set.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setMethod(HttpMethod method){
		forceNotStarted();
		this.method = method;
		return this;
	}
	/**
	 * Set's the type of this Request.
	 * @param type - type to set.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setType(HttpResponseType type) {
		forceNotStarted();
		if (type==null) throw new NullPointerException("Type mustn't be null !");
		if (type==HttpResponseType.ERROR) throw new IllegalArgumentException("Type mustn't be ERROR !");
		this.type = type;
		return this;
	}
	/**
	 * Set's the HTML Headers for this Request.
	 * @param requestProperty - RequestProperty to set.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	public HttpRequest setRequestProperty(HashMap<String, String> requestProperty) {
		forceNotStarted();
		this.requestProperty = requestProperty;
		return this;
	}
	/**
	 * Set's the parser for this Request.
	 * @param parser - Parser to set.
	 * @return This for easier chaining (just like StringBuilder does).
	 * @throws IllegalStateException if Request has started
	 */
	@SuppressWarnings("rawtypes")
	public HttpRequest setParser(Parser parser){
		forceNotStarted();
		this.parser = parser;
		return this;
	}
	
	//==================================================\\
	//getters											||
	//==================================================//
	/**
	 * Forces that Request <u>IS</u> ready!
	 * @throws IllegalStateException if Request is not ready.
	 */
	private void forceReady(){
		if (!isReady) throw new IllegalStateException("ERROR: Server did not yet respond! Use isReady()!");
	}
	/**
	 * Getter for the Request's state.
	 * @return True if ready, false otherwise.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public boolean isReady(){
		return isReady;
	}
	/**
	 * Getter for the Request's state.
	 * @return True if started, false otherwise.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public boolean isStarted(){
		return isStarted;
	}
	/**
	 * Getter for the Response's Headers.
	 * @return The HTTP Headers of the Response.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public HashMap<String, String> getHeaders() {
		forceReady();
		return headers;
	}
	/**
	 * Getter for the Response's Cookies.
	 * @return The Response Cookies (Set-Cookie Header field parsed)
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public HashMap<String, String> getCookies() {
		forceReady();
		return cookies;
	}
	/**
	 * Getter for the Response's Data.
	 * @return The Response data OR the Exception which occurred during the Request.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public Object getData() {
		forceReady();
		return data;
	}
	/**
	 * Getter for the Response's Code.
	 * @return The HTTP Response Code.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public int getResponseCode(){
		forceReady();
		return responseCode;
	}
	/**
	 * Getter for the Response's message.
	 * @return The response message according to the HTTP Code.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public String getResponseMessage(){
		forceReady();
		return responseMessage;
	}
	/**
	 * Getter for the Response's type.
	 * @return The type of the Response. WARNING: it'll be <code>ERROR</code> if anything happened during.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public HttpResponseType getType() {
		forceReady();
		return type;
	}
	/**
	 * Getter for the Request's method.
	 * @return The HTTP method of the initial Request.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 */
	public HttpMethod getMethod(){
		return method;
	}
	/**
	 * Time of the REquest's completion in milliseconds.
	 * @return The time of completion in milliseconds.
	 * @throws IllegalStateException if the target hasn't responded yet.
	 * @see System#currentTimeMillis() 
	 */
	public long getTime() {
		forceReady();
		return time;
	}
	
	//==================================================\\
	//send -> starts HTTP request						||
	//==================================================//
	/** Sends the Request with the specified parameters. */
	public void send(){
		forceNotStarted();
		isStarted = true;
		t.start();	//runs to run() ;)
	}

	@Override
	public void run(){
		//trick so that _ONLY_ send() can run this method!
		//only send() sets isStarted to true.
		//if send() is called with true isStarted it'll throw an exception
		//thus it's impossible to call this function more than once.
		if (!isStarted) return;
		
		isReady = false;
		
		try{
			//method = {GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE};
			switch(method){
			
				//safe methods
				case GET:
					GET();
					break;
				case HEAD:
					HEAD();
					break;
				case OPTIONS:
					OPTIONS();
					break;
				case TRACE:
					TRACE();
					break;
				
				//unsafe methods
				case POST:
					POST();
					break;
				case PUT:
					PUT();
					break;
				case DELETE:
					DELETE();
					break;
				
				//non-existing call -> what the f*ck ?!
				default:
					exception(new IllegalArgumentException("ERROR: invalid method: "+method));
					responseCode = -1;
					responseMessage = null;
					break;
			}
		}catch(Exception e){
			exception(e);
		}
		
		time = System.currentTimeMillis();
		isReady = true;
		
		//wake up waiting Network(s)
		synchronized(this){
			this.notifyAll();
		}
	}
	
	//==================================================\\
	//HTTP request methods								||
	//==================================================//
	
	/** The HTTP GET method */
	private void GET() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(false);
		
		//get body
		processBody(conn);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//disconnect
		//conn.disconnect();
	}
	

	/** The HTTP HEAD method */
	private void HEAD() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(false);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//disconnect
		conn.disconnect();
	}
	
	/** The HTTP POST method */
	private void POST() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(true);
		
		//write body
		writeBody(conn);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//disconnect
		conn.disconnect();
	}
	
	/** The HTTP PUT method */
	private void PUT() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(true);
		
		//write body
		writeBody(conn);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//disconnect
		conn.disconnect();
	}
	
	/** The HTTP DELETE method */
	private void DELETE() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(false);
		
		//get body
		processBody(conn);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//disconnect
		conn.disconnect();
	}
	
	/** The HTTP OPTIONS method */
	private void OPTIONS() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(true);
		
		//write body
		writeBody(conn);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//get body
		processBody(conn);
		
		//disconnect
		conn.disconnect();
	}
	
	/** The HTTP TRACE method */
	private void TRACE() throws Exception{
		//establish connection
		HttpURLConnection conn = makeConn(false);
		
		//get headers
		processHeaders(conn);
		
		//get codes
		responseCode = conn.getResponseCode();
		responseMessage = conn.getResponseMessage();
		
		//get body
		processBody(conn);
		
		//disconnect
		conn.disconnect();
	}
	
	//==================================================\\
	//processing facilities								||
	//==================================================//
	
	/**
	 * Establishes a new HttpURLConnection
	 * @param output - flag if used for output
	 * @return The established HttpURLConnection
	 * @throws Exception any errors during the process
	 */
	private HttpURLConnection makeConn(boolean output) throws Exception{
		HttpURLConnection ret = null;
		
		//make connection, and use proxy, if any
		if(proxy != null){
			ret = (HttpURLConnection) new URL(url).openConnection(proxy);
		}else{
			ret = (HttpURLConnection) new URL(url).openConnection();
		}
		
		//don't redirect me, never ever !
		ret.setInstanceFollowRedirects(false);
		
		//set input/output/method
		ret.setDoInput(true);
		ret.setDoOutput(output);
		ret.setRequestMethod(method.toString());
		
		//set headers
		for(Entry<String, String> entry : requestProperty.entrySet()){
			ret.setRequestProperty(entry.getKey(), entry.getValue());
		}
			
		return ret;
	}
	
	/**
	 * Processes HTTP headers (and cookies!)
	 * @param conn - established HttpURLConnection
	 * @throws Exception - any errors during the process
	 */
	private void processHeaders(HttpURLConnection conn) throws Exception{
		headers = new HashMap<String, String>();
		cookies = new HashMap<String, String>();
		
		//=================================
		//get headers, cookies, etc.
		for (int i=0; ; i++) {
			
			//get headers from header field
			String headerName = conn.getHeaderFieldKey(i);
			String headerValue = conn.getHeaderField(i);
			
			//end of headers -> break
			if(headerName == null && headerValue == null){
				break;
			}
			
			//cookies
			if(headerName != null && headerName.equals("Set-Cookie")){
				//process cookies!
				processCookies(headerValue);
				
				//we don't need the Set-Cookie anymore
				continue;
			}
			
			//put header field
			headers.put(headerName, headerValue);
		}
	}
	
	/**
	 * Processes HTTP cookies
	 * @param setCookie - Set-Cookie field from headers
	 * @throws Exception - any errors during the process
	 */
	private void processCookies(String setCookie) throws Exception{
		
		//=================================
		//init
		Reader input = new StringReader(setCookie);
		int n = 0;
		
		//=================================
		//parse Set-Cookie
		pairLoop: while(n != -1){
			//init pair
			StringBuilder key = new StringBuilder();
			StringBuilder value = new StringBuilder();
			boolean keyStarted = false;
			
			keyLoop: while(true){
				n = input.read();
				switch(n){
					case '=':
						//end of key
						break keyLoop;
						
					case ';':
						//end of pair
						value.append("true");
						cookies.put(key.toString(), value.toString());
						continue keyLoop;
					
					case -1:
						//end of stream
						value.append("true");
						cookies.put(key.toString(), value.toString());
						break pairLoop;
						
					case ' ':
						//starting spaces are unneeded!
						if (keyStarted) key.append((char)n);
						continue;
						
					default:
						keyStarted = true;
						key.append((char)n);
						continue;
				}
			}
			
			//value reader loop
			while((n=input.read()) != -1){
				if(n == ';'){
					//end of value
					break;
				}else{
					value.append((char)n);
				}
			}
			
			//store pair
			cookies.put(key.toString(), value.toString());
		}
	}
	
	/**
	 * Processes HTTP body
	 * @param conn - established HttpURLConnection
	 * @throws Exception - any errors during the process
	 */
	private void processBody(HttpURLConnection conn) throws Exception{
		if(type == HttpResponseType.RAW){
			data = conn.getInputStream();
		}else{
			final BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			switch(type){
				//return raw input stream.
				case RAW:
					assert false : HttpResponseType.RAW;
					data = conn.getInputStream();
					break;
			
				//do nothing
				case NULL:
					data = null;
					break;
			
				//return input lines as Strings.
				case SOURCE:
					ArrayList<String> ret = new ArrayList<>();
					String line;
					while ((line = input.readLine()) != null){
						ret.add(line);
					}
					data = ret;
					break;
					
				//return JSON.
				case JSON:
					Parser<?> parser = getParser();
					if(parser!=null){
						data = parser.parse(input);
					}else{
						data = JsonParser.qucikParse(input);
					}
					break;
					
				//DAFUQ ?!
				default:
					throw new IllegalArgumentException("Invalid type : "+type);
			}
		}
	}
	
	/**
	 * Returns the set parser, or the Parent's default parser, if any.
	 * @return The found parser, or null, if none found.
	 */
	private Parser<?> getParser(){
		if(parser!=null){
			return parser;
		}else{
			return parent.getDefaultParser(type);
		}
	}
	
	/**
	 * Writes HTTP body
	 * @param conn - established HttpURLConnection
	 * @throws Exception - any errors during the process
	 */
	private void writeBody(HttpURLConnection conn) throws Exception{
		if (body!=null) conn.getOutputStream().write(body);
		conn.getOutputStream().close();
	}
	
	/**
	 * Called when something goes terribly wrong. 
	 * Changes type to ERROR and Data to a {@link HttpException}.
	 * @param e - Exception which occurred
	 */
	private void exception(Exception e){
		data = new HttpException(e);
		if(responseCode == 0){
			responseCode = -1;
			responseMessage = null;
		}
		type = HttpResponseType.ERROR;
	}
}