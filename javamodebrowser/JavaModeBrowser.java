package javamodebrowser;

import static net.garmine.http.HttpMethod.*;
import static net.garmine.http.HttpResponseType.*;
import static net.garmine.parser.html.nodes.HtmlNodeType.COMMENT;
import static net.garmine.parser.html.nodes.HtmlNodeType.ELEMENT;
import static net.garmine.parser.html.nodes.HtmlNodeType.TEXT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;

import net.garmine.http.HttpClient;
import net.garmine.http.HttpRequest;
import net.garmine.parser.html.HtmlDocument;
import net.garmine.parser.html.HtmlParser;
import net.garmine.parser.html.MalformedHtmlException;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.nodes.HtmlNode;

/**
 * Java Mode Browser prototype implementation.<br>
 * Prints Cookies, HTTP Headers, and the HTML tree instead of functioning as a real browser.
 * @author Garmine
 */
public class JavaModeBrowser extends HttpClient implements Runnable{
	
	/**
	 * Sleeps for some milliseconds.<br>
	 * Warning: ignores InterruptedException!
	 * @param ms - duration of sleep in milliseconds.
	 */
	public static void sleep(long ms){
		try{
			Thread.sleep(ms);
		}catch(InterruptedException ignored){}
	}
	
	public JavaModeBrowser(){
		super();
	}
	
	@Override
	protected HttpRequest preProcessResponse(HttpRequest in) {
		if(in.getHeaders() != null){
			System.out.println("\nHeaders:");
			for (Entry<String, String> entry : in.getHeaders().entrySet()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}
		}
		
		if(in.getCookies() != null){
			System.out.println("\nCookies:");
			for (Entry<String, String> entry : in.getCookies().entrySet()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}
		}

		System.out.println("\nCode: " + in.getResponseCode() + ", " + in.getResponseMessage());
		
		System.out.println();
		System.out.println("==================================================================================");
		
		return in;
	}
	
	/**
	 * Prints each and every child node and their children recursively.
	 * @param node - HtmlMidNode to print
	 * @param prefix - String to appedn to the beginning of each line
	 */
	private void print(HtmlMidNode node, String prefix){
		ArrayList<HtmlNode> children = node.children;
		for(int c=0; c<children.size(); c++){
			HtmlNode n = children.get(c);
			boolean end = (c==children.size()-1);

			if(n.is(ELEMENT)){
				System.out.println( prefix+(end?"`-":"|-")+n.asElement().getType());
				print(n.asElement(),prefix+(end?"  ":"| "));
			}else{
				System.out.print(   prefix+(end?"`-":"|-")+n.getNodeType());
				if(n.is(COMMENT)){
					System.out.println(": \""+n.asComment().comment.replaceAll("\n", " ")+"\"");
				}else if(n.is(TEXT)){
					System.out.println(": \""+n.asText().text+"\"");
				}else{
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * Visits a site and prints the Cookies, HTTP Headers, and the HTML tree
	 * @param site - site to visit
	 * @throws IOException if reading the input stream fails for whatever reason
	 * @throws MalformedHtmlException it actually does not throws this...
	 */
	public void visit(String site) throws IOException, MalformedHtmlException{
		request(site, null, GET).setType(RAW);
		HttpRequest in = sendWait();
		
		switch(in.getType()){
			case ERROR:
				sleep(50);
				((Exception) in.getData()).printStackTrace();
				sleep(50);
				break;
				
			case NULL:
				System.out.print(in.getData());
				break;
			
			case RAW:
				//Initialize the parser
				HtmlParser parser = new HtmlParser(new BufferedReader(new InputStreamReader((InputStream)in.getData())), false);

				//The first returned Node MUST BE the Document!
				HtmlDocument document = parser.next().asDocument();
				assert document!=null : document;
				
				//Parse the whole document
				while(parser.next() != null);
				
				//Print the tree using our wonderful recursive function!
				System.out.println(document.getNodeType());
				print(document, "");
				System.out.println();
				break;
				
			default:
				throw new IllegalStateException("LOLWAT");
		}

	}
	
	@Override
	public void run(){
		@SuppressWarnings("resource")
		final Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter a site to show the HTML tree, or type in \"exit\" to exit!");
		while(true){
			System.out.print(">> ");
			String site = input.next();
			if (site.equalsIgnoreCase("exit")) break;
			try{
				visit(site);
			}catch (IOException e){
				sleep(50);
				e.printStackTrace();
				sleep(50);
			}catch(MalformedHtmlException e){
				sleep(50);
				e.printStackTrace();
				sleep(50);
			}
			System.out.println("==================================================================================");
		}
	}
	
	public static void main(String... args){
		JavaModeBrowser jmb = new JavaModeBrowser();
		jmb.run();
	}
}
