package net.garmine.parser.html.nodes;

import java.util.HashMap;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Accesskey;
import net.garmine.parser.html.attributes.Class;
import net.garmine.parser.html.attributes.Contenteditable;
import net.garmine.parser.html.attributes.Contextmenu;
import net.garmine.parser.html.attributes.Dir;
import net.garmine.parser.html.attributes.Draggable;
import net.garmine.parser.html.attributes.Dropzone;
import net.garmine.parser.html.attributes.Hidden;
import net.garmine.parser.html.attributes.Id;
import net.garmine.parser.html.attributes.Lang;
import net.garmine.parser.html.attributes.Spellcheck;
import net.garmine.parser.html.attributes.Style;
import net.garmine.parser.html.attributes.Tabindex;
import net.garmine.parser.html.attributes.Title;
import net.garmine.parser.html.attributes.Translate;
import net.garmine.parser.html.elements.HtmlElementType;

import static net.garmine.parser.html.nodes.HtmlNodeType.ELEMENT;

/**
 * An abstract class which represents an HTML element.
 * @author Garmine
 */
public abstract class HtmlElement extends HtmlMidNode {
	public char accesskey;
	public String clazz;
	public Contenteditable contenteditable;
	public String contextmenu;
	public Dir dir;
	public boolean draggable;
	public Dropzone dropzone;
	public boolean hidden;
	public String id;
	public String lang;
	public boolean spellcheck;
	public String style;
	public int tabindex;
	public String title;
	public boolean translate;
	
	/**
	 * Getter for the Element's type
	 * @return The type of the element.
	 */
	public abstract HtmlElementType getType();
	
	/** Stores attributes named "data-*" */
	private HashMap<String, String> dataAttr;
	
	@Override
	public HtmlNodeType getNodeType(){
		return ELEMENT;
	}

	@Override
	public HtmlElement asElement() {
		return this;
	}
	
	/**
	 * Getter for special attributes named "data-*".
	 * @param name - the name of the attribute
	 * @return The value of the attribute, if any.
	 */
	public String getDataAttribute(String name){
		if(dataAttr == null){
			return null;
		}else{
			return dataAttr.get(name);
		}
	}
	
	/**
	 * Getter for special attributes named "data-*".
	 * @param name - the name of the attribute
	 * @param value - the value of the attribute
	 * @return true, if succesful (name must conform the regex "data-.*"), false otherwise.
	 */
	public boolean setDataAttribute(String name, String value){
		if(dataAttr == null || !name.startsWith("data-")){
			return false;
		}else{
			dataAttr.put(name, value);
			return true;
		}
	}
	
	/**
	 * Constructs a new HTML element, and processes the global attributes
	 * @param parent - parent of the element
	 * @param attrs - attributes of the element
	 */
	public HtmlElement(HtmlMidNode parent, HtmlAttributeToken[] attrs){
		super(parent);
		
		for(HtmlAttributeToken attr:attrs){
			String v = attr.getAttrValue();
			switch(attr.getAttrName()){
				case "accesskey":
					accesskey = Accesskey.parse(this, v);
					break;
				case "contenteditable":
					contenteditable = Contenteditable.parse(this, v);
					break;
				case "contextmenu":
					contextmenu = Contextmenu.parse(this, v);
					break;
				case "dir":
					dir = Dir.parse(this, v);
					break;
				case "draggable":
					draggable = Draggable.parse(this, v);
					break;
				case "dropzone":
					dropzone = Dropzone.parse(this, v);
					break;
				case "hidden":
					hidden = Hidden.parse(this, v);
					break;
				case "id":
					id = Id.parse(this, v);
					break;
				case "lang":
					lang = Lang.parse(this, v);
					break;
				case "spellcheck":
					spellcheck = Spellcheck.parse(this, v);
					break;
				case "style":
					style = Style.parse(this, v);
					break;
				case "tabindex":
					tabindex = Tabindex.parse(this, v);
					break;
				case "title":
					title = Title.parse(this, v);
					break;
				case "translate":
					translate = Translate.parse(this, v);
					break;
				case "class":
					clazz = Class.parse(this, v);
					break;
					
				default:
					if(attr.getName().startsWith("data-")){
						if (dataAttr == null) dataAttr = new HashMap<>();
						dataAttr.put(attr.getName(), v);
					}
					break;
			}
		}
	}
}
