package net.garmine.parser.html.elements;

import java.lang.reflect.Constructor;

import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;

/**
 * Holds Enums for all HTML element.
 * @see #hasCloseTag()
 * @see #getConstructor()
 * @author Garmine
 */
public enum HtmlElementType {
	ABBR(true, Abbr.class), ACRONYM(true, Acronym.class), ADDRESS(true, Address.class), 
	A(true, A.class), APPLET(true, Applet.class), AREA(false, Area.class), 
	ARTICLE(true, Article.class), ASIDE(true, Aside.class), AUDIO(true, Audio.class), 
	
	BASEFONT(true, Basefont.class), BASE(false, Base.class), BDI(true, Bdi.class), 
	BDO(true, Bdo.class), BIG(true, Big.class), B(true, B.class), 
	BLOCKQUOTE(true, Blockquote.class), BODY(true, Body.class), BR(false, Br.class), 
	BUTTON(true, Button.class), 
	
	CANVAS(true, Canvas.class), CAPTION(true, Caption.class), CENTER(true, Center.class), 
	CITE(true, Cite.class), CODE(true, Code.class), COLGROUP(true, Colgroup.class), 
	COL(false, Col.class), COMMAND(false, Command.class), COMPACT(true, Compact.class), 
	
	DATALIST(true, Datalist.class), DD(true, Dd.class), DEL(true, Del.class), 
	DETAILS(true, Details.class), DFN(true, Dfn.class), DIALOG(true, Dialog.class), 
	DIV(true, Div.class), DL(true, Dl.class), DT(true, Dt.class), 
	
	EMBED(false, Embed.class), EM(true, Em.class), 
	
	FIELDSET(true, Fieldset.class), FIGCAPTION(true, Figcaption.class), FIGURE(true, Figure.class), 
	FONT(true, Font.class), FOOTER(true, Footer.class), FORM(true, Form.class), 
	FRAME(true, Frame.class), FRAMESET(true, Frameset.class), 
	
	//G
	
	H1(true, H1.class), H2(true, H2.class), H3(true, H3.class), 
	H4(true, H4.class), H5(true, H5.class), H6(true, H6.class), 
	HEADER(true, Header.class), HEAD(true, Head.class), HR(false, Hr.class), 
	HTML(true, Html.class), 
	
	IFRAME(true, Iframe.class), I(true, I.class), IMG(false, Img.class), 
	INPUT(false, Input.class), INS(true, Ins.class), 
	
	//J
	
	KBD(true, Kbd.class), KEYGEN(true, Keygen.class), 
	
	LABEL(true, Label.class), LEGEND(true, Legend.class), LI(true, Li.class), 
	LINK(false, Link.class), 
	
	MAP(true, Map.class), MARK(true, Mark.class), MENU(true, Menu.class), 
	META(false, Meta.class), METER(true, Meter.class), 
	
	NAV(true, Nav.class), NOFRAMES(true, Noframes.class), NOSCRIPT(true, Noscript.class), 
	
	OBJECT(true, Object.class), OL(true, Ol.class), OPTGROUP(true, Optgroup.class), 
	OPTION(true, Option.class), OUTPUT(true, Output.class), 
	
	PARAM(false, Param.class), P(true, P.class), PRE(true, Pre.class), 
	PROGRESS(true, Progress.class), 
	
	Q(true, Q.class), 
	
	RP(true, Rp.class), RT(true, Rt.class), RUBY(true, Ruby.class), 
	
	SAMP(true, Samp.class), SCRIPT(true, Script.class), SECTION(true, Section.class), 
	SELECT(true, Select.class), S(true, S.class), SMALL(true, Small.class), 
	SOURCE(false, Source.class), SPAN(true, Span.class), STRIKE(true, Strike.class), 
	STRONG(true, Strong.class), STYLE(true, Style.class), SUB(true, Sub.class), 
	SUMMARY(true, Summary.class), SUP(true, Sup.class), 
	
	TABLE(true, Table.class), TBODY(true, Tbody.class), TD(true, Td.class), 
	TEXTAREA(true, Textarea.class), TFOOT(true, Tfoot.class), THEAD(true, Thead.class), 
	TH(true, Th.class), TIME(true, Time.class), TITLE(true, Title.class), 
	TRACK(true, Track.class), TR(true, Tr.class), TT(true, Tt.class), 
	
	U(true, U.class), UL(true, Ul.class), 
	
	VAR(true, Var.class), VIDEO(true, Video.class), 
	
	WBR(true, Wbr.class);
	
	//X
	
	//Y
	
	//Z
	
	private final boolean hasCloseTag;
	private final Constructor<? extends HtmlElement> constructor;
	
	/**
	 * Checks whether this Element has a close tag.
	 * @return True, if this element has a close tag (i.e. it is not a void element).
	 */
	public boolean hasCloseTag(){
		return hasCloseTag;
	}
	
	/**
	 * Gets the constructor for the corresponding HtmlElement.<br>
	 * Arguments: (HtmlMidNode, HtmlAttributeToken[])
	 * @see HtmlElement#HtmlElement(HtmlMidNode, HtmlAttributeToken[])
	 * @return The constructor of the corresponding HtmlElement.
	 */
	public Constructor<? extends HtmlElement> getConstructor(){
		return constructor;
	}
	
	private HtmlElementType(boolean hasCloseTag, Class<? extends HtmlElement> clazz){
		this.hasCloseTag = hasCloseTag;
		Constructor<? extends HtmlElement> c;
		try {
			c = clazz.getConstructor(HtmlMidNode.class, HtmlAttributeToken[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			assert false : e ;
			c = null;
		}
		this.constructor = c;
	}
}
