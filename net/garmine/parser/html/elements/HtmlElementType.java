package net.garmine.parser.html.elements;

/*
<area >
<base>
<br>
<col>
<command>
<embed>
<hr>
<img>
<input>
<link>
<meta>
<param>
<source>
*/

public enum HtmlElementType {
	ABBR(true), ACRONYM(true), ADDRESS(true), 
	A(true), APPLET(true), AREA(false), 
	ARTICLE(true), ASIDE(true), AUDIO(true), 
	
	BASEFONT(true), BASE(false), BDI(true), 
	BDO(true), BIG(true), B(true), 
	BLOCKQUOTE(true), BODY(true), BR(false), 
	BUTTON(true), 
	
	CANVAS(true), CAPTION(true), CENTER(true), 
	CITE(true), CODE(true), COLGROUP(true), 
	COL(false), COMMAND(false), COMPACT(true), 
	
	DATALIST(true), DD(true), DEL(true), 
	DETAILS(true), DFN(true), DIALOG(true), 
	DIV(true), DL(true), DT(true), 
	
	EMBED(false), EM(true), 
	
	FIELDSET(true), FIGCAPTION(true), FIGURE(true), 
	FONT(true), FOOTER(true), FORM(true), 
	FRAME(true), FRAMESET(true), 
	
	//G
	
	H1(true), H2(true), H3(true), 
	H4(true), H5(true), H6(true), 
	HEADER(true), HEAD(true), HR(false), 
	HTML(true), 
	
	IFRAME(true), I(true), IMG(false), 
	INPUT(false), INS(true), 
	
	//J
	
	KBD(true), KEYGEN(true), 
	
	LABEL(true), LEGEND(true), LI(true), 
	LINK(false), 
	
	MAP(true), MARK(true), MENU(true), 
	META(false), METER(true), 
	
	NAV(true), NOFRAMES(true), NOSCRIPT(true), 
	
	OBJECT(true), OL(true), OPTGROUP(true), 
	OPTION(true), OUTPUT(true), 
	
	PARAM(false), P(true), PRE(true), 
	PROGRESS(true), 
	
	Q(true), 
	
	RP(true), RT(true), RUBY(true), 
	
	SAMP(true), SCRIPT(true), SECTION(true), 
	SELECT(true), S(true), SMALL(true), 
	SOURCE(false), SPAN(true), STRIKE(true), 
	STRONG(true), STYLE(true), SUB(true), 
	SUMMARY(true), SUP(true), 
	
	TABLE(true), TBODY(true), TD(true), 
	TEXTAREA(true), TFOOT(true), THEAD(true), 
	TH(true), TIME(true), TITLE(true), 
	TRACK(true), TR(true), TT(true), 
	
	U(true), UL(true), 
	
	VAR(true), VIDEO(true), 
	
	WBR(true);
	
	//X
	
	//Y
	
	//Z
	
	private final boolean hasCloseTag;
	
	public boolean hasCloseTag(){
		return hasCloseTag;
	}
	
	private HtmlElementType(boolean hasCloseTag){
		this.hasCloseTag = hasCloseTag;
	}
}
