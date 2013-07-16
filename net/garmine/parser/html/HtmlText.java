package net.garmine.parser.html;

public class HtmlText extends HtmlNode {
	/** Text contained */
	public String text = "";
	
	/**
	 * Constructs a new Rawtext and registers it at the parent (if any).
	 * @param parent - Element which contains the text
	 */
	public HtmlText(HtmlElement parent){
		super(parent);
	}
	
	/**
	 * Constructs a new Rawtext,registers it at the parent (if any), and sets the text inside.
	 * @param parent - Element which contains the text
	 * @param text - initial value
	 */
	public HtmlText(HtmlElement parent, String text){
		super(parent);
		if(text != null){
			this.text = text;
		}else{
			this.text = "";
		}
	}
}
