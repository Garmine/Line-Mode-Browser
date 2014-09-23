package net.garmine.parser.html.elements;

import java.lang.reflect.Constructor;

import net.garmine.parser.html.nodes.HtmlElement;
import net.garmine.parser.html.nodes.HtmlMidNode;
import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.tokenizer.HtmlTokenizerState;

import static net.garmine.parser.html.tokenizer.HtmlTokenizerState.*;

/**
 * Holds Enums for all HTML element.
 * @see #hasCloseTag()
 * @see #getConstructor()
 * @author Garmine
 */
public enum HtmlElementType {
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    ABBR(       Abbr.class,         true,       null    ),
    ACRONYM(    Acronym.class,      true,       null    ),
    ADDRESS(    Address.class,      true,       null    ),
    A(          A.class,            true,       null    ),
    APPLET(     Applet.class,       true,       null    ),
    AREA(       Area.class,         false,      null    ),
    ARTICLE(    Article.class,      true,       null    ),
    ASIDE(      Aside.class,        true,       null    ),
    AUDIO(      Audio.class,        true,       null    ),
    BASEFONT(   Basefont.class,     true,       null    ),
    BASE(       Base.class,         false,      null    ),
    BDI(        Bdi.class,          true,       null    ),
    BDO(        Bdo.class,          true,       null    ),
    BIG(        Big.class,          true,       null    ),
    B(          B.class,            true,       null    ),
    BLOCKQUOTE( Blockquote.class,   true,       null    ),
    BODY(       Body.class,         true,       null    ),
    BR(         Br.class,           false,      null    ),
    BUTTON(     Button.class,       true,       null    ),
    CANVAS(     Canvas.class,       true,       null    ),
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    CAPTION(    Caption.class,      true,       null    ),
    CENTER(     Center.class,       true,       null    ),
    CITE(       Cite.class,         true,       null    ),
    CODE(       Code.class,         true,       null    ),
    COLGROUP(   Colgroup.class,     true,       null    ),
    COL(        Col.class,          false,      null    ),
    COMMAND(    Command.class,      false,      null    ),
    COMPACT(    Compact.class,      true,       null    ),
    DATALIST(   Datalist.class,     true,       null    ),
    DD(         Dd.class,           true,       null    ),
    DEL(        Del.class,          true,       null    ),
    DETAILS(    Details.class,      true,       null    ),
    DFN(        Dfn.class,          true,       null    ),
    DIALOG(     Dialog.class,       true,       null    ),
    DIV(        Div.class,          true,       null    ),
    DL(         Dl.class,           true,       null    ),
    DT(         Dt.class,           true,       null    ),
    EMBED(      Embed.class,        false,      null    ),
    EM(         Em.class,           true,       null    ),
    FIELDSET(   Fieldset.class,     true,       null    ),
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    FIGCAPTION( Figcaption.class,   true,       null    ),
    FIGURE(     Figure.class,       true,       null    ),
    FONT(       Font.class,         true,       null    ),
    FOOTER(     Footer.class,       true,       null    ),
    FORM(       Form.class,         true,       null    ),
    FRAME(      Frame.class,        true,       null    ),
    FRAMESET(   Frameset.class,     true,       null    ),
    H1(         H1.class,           true,       null    ),
    H2(         H2.class,           true,       null    ),
    H3(         H3.class,           true,       null    ),
    H4(         H4.class,           true,       null    ),
    H5(         H5.class,           true,       null    ),
    H6(         H6.class,           true,       null    ),
    HEADER(     Header.class,       true,       null    ),
    HEAD(       Head.class,         true,       null    ),
    HR(         Hr.class,           false,      null    ),
    HTML(       Html.class,         true,       null    ),
    IFRAME(     Iframe.class,       true,       null    ),
    I(          I.class,            true,       null    ),
    IMG(        Img.class,          false,      null    ),
    INPUT(      Input.class,        false,      null    ),
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    INS(        Ins.class,          true,       null    ),
    KBD(        Kbd.class,          true,       null    ),
    KEYGEN(     Keygen.class,       true,       null    ),
    LABEL(      Label.class,        true,       null    ),
    LEGEND(     Legend.class,       true,       null    ),
    LI(         Li.class,           true,       null    ),
    LINK(       Link.class,         false,      null    ),
    MAP(        Map.class,          true,       null    ),
    MARK(       Mark.class,         true,       null    ),
    MENU(       Menu.class,         true,       null    ),
    META(       Meta.class,         false,      null    ),
    METER(      Meter.class,        true,       null    ),
    NAV(        Nav.class,          true,       null    ),
    NOFRAMES(   Noframes.class,     true,       RAWTEXT ),
    NOSCRIPT(   Noscript.class,     true,       RAWTEXT ),
    OBJECT(     Object.class,       true,       null    ),
    OL(         Ol.class,           true,       null    ),
    OPTGROUP(   Optgroup.class,     true,       null    ),
    OPTION(     Option.class,       true,       null    ),
    OUTPUT(     Output.class,       true,       null    ),
    PARAM(      Param.class,        false,      null    ),
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    P(          P.class,            true,       null    ),
    PRE(        Pre.class,          true,       null    ),
    PROGRESS(   Progress.class,     true,       null    ),
    Q(          Q.class,            true,       null    ),
    RP(         Rp.class,           true,       null    ),
    RT(         Rt.class,           true,       null    ),
    RUBY(       Ruby.class,         true,       null    ),
    SAMP(       Samp.class,         true,       null    ),
    SCRIPT(     Script.class,       true,       HtmlTokenizerState.SCRIPT),
    SECTION(    Section.class,      true,       null    ),
    SELECT(     Select.class,       true,       null    ),
    S(          S.class,            true,       null    ),
    SMALL(      Small.class,        true,       null    ),
    SOURCE(     Source.class,       false,      null    ),
    SPAN(       Span.class,         true,       null    ),
    STRIKE(     Strike.class,       true,       null    ),
    STRONG(     Strong.class,       true,       null    ),
    STYLE(      Style.class,        true,       RAWTEXT ),
    SUB(        Sub.class,          true,       null    ),
    SUMMARY(    Summary.class,      true,       null    ),
    SUP(        Sup.class,          true,       null    ),
    /*....................................................
    :   ENUM   :     CLASS        :   HAS   :   SWITCH   :
    :          :                  :  CLOSE  :     TO     :
    :   NAME   :   REFERENCE      :   TAG   :   STATE:   :
    :..........:..................:.........:...........*/
    TABLE(      Table.class,        true,       null    ),
    TBODY(      Tbody.class,        true,       null    ),
    TD(         Td.class,           true,       null    ),
    TEXTAREA(   Textarea.class,     true,       RCDATA  ),
    TFOOT(      Tfoot.class,        true,       null    ),
    THEAD(      Thead.class,        true,       null    ),
    TH(         Th.class,           true,       null    ),
    TIME(       Time.class,         true,       null    ),
    TITLE(      Title.class,        true,       RCDATA  ),
    TRACK(      Track.class,        true,       null    ),
    TR(         Tr.class,           true,       null    ),
    TT(         Tt.class,           true,       null    ),
    U(          U.class,            true,       null    ),
    UL(         Ul.class,           true,       null    ),
    VAR(        Var.class,          true,       null    ),
    VIDEO(      Video.class,        true,       null    ),
    WBR(        Wbr.class,          true,       null    );

    
    
	private final Constructor<? extends HtmlElement> constructor;
	private final boolean hasCloseTag;
	private final HtmlTokenizerState switchTo;

	/**
	 * Checks whether this Element has a close tag.
	 * @return True, if this element has a close tag (i.e. it is not a void element).
	 */
	public boolean hasCloseTag(){
		return hasCloseTag;
	}
	
	/**
	 * Returns the state the Tokenizer shall switch to, if any.
	 * @return The state the Tokenizer shall switch to, or null, if it shall stay on the current state.
	 */
	public HtmlTokenizerState getTokenizingState(){
		return switchTo;
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

	private HtmlElementType(Class<? extends HtmlElement> clazz, boolean hasCloseTag, HtmlTokenizerState switchTo){ 
		Constructor<? extends HtmlElement> c;
		try {
			c = clazz.getConstructor(HtmlMidNode.class, HtmlAttributeToken[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			assert false : e ;
			c = null;
		}
		this.constructor = c;
		this.hasCloseTag = hasCloseTag;
		this.switchTo = switchTo;
	}
}
