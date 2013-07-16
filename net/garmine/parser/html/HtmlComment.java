package net.garmine.parser.html;

public class HtmlComment extends HtmlNode{
	/** Comment contained */
	public String comment = "";
	
	/**
	 * Constructs a new Rawtext and registers it at the parent (if any).
	 * @param parent - Element which contains the text
	 */
	public HtmlComment(HtmlElement parent){
		super(parent);
	}
	
	/**
	 * Constructs a new Rawtext,registers it at the parent (if any), and sets the text inside.
	 * @param parent - Element which contains the text
	 * @param comment - initial value
	 */
	public HtmlComment(HtmlElement parent, String comment){
		super(parent);
		this.comment = comment;
	}
}
