package net.garmine.parser.html.nodes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.garmine.parser.html.elements.HtmlElementType;

import static net.garmine.parser.html.elements.HtmlElementType.*;

@SuppressWarnings("serial")
/**
 * A shell for an unmodifiable String->HtmlElementType HashMap.
 * @author Garmine
 */
public class HtmlElements {
	/** An unmodifiable Map which provides easy parsing for all of the supported elements.<br>
	 *  It is used instead of {@link HtmlElementType#valueOf(String)} because that'd throw an
	 *  IllegalArgumentException instead of returning null in case the String does not corresponds
	 *  to an {@link HtmlElementType}
	 */
	public static final Map<String, HtmlElementType> HTML_ELEMENTS;
	static{
		HTML_ELEMENTS = Collections.unmodifiableMap(new HashMap<String, HtmlElementType>(){{
				put("abbr", ABBR);
				put("acronym", ACRONYM);
				put("address", ADDRESS);
				put("a", A);
				put("applet", APPLET);
				put("area", AREA);
				put("article", ARTICLE);
				put("aside", ASIDE);
				put("audio", AUDIO);
				put("basefont", BASEFONT);
				put("base", BASE);
				put("bdi", BDI);
				put("bdo", BDO);
				put("big", BIG);
				put("b", B);
				put("blockquote", BLOCKQUOTE);
				put("body", BODY);
				put("br", BR);
				put("button", BUTTON);
				put("canvas", CANVAS);
				put("caption", CAPTION);
				put("center", CENTER);
				put("cite", CITE);
				put("code", CODE);
				put("colgroup", COLGROUP);
				put("col", COL);
				put("command", COMMAND);
				put("compact", COMPACT);
				put("datalist", DATALIST);
				put("dd", DD);
				put("del", DEL);
				put("details", DETAILS);
				put("dfn", DFN);
				put("dialog", DIALOG);
				put("div", DIV);
				put("dl", DL);
				put("dt", DT);
				put("embed", EMBED);
				put("em", EM);
				put("fieldset", FIELDSET);
				put("figcaption", FIGCAPTION);
				put("figure", FIGURE);
				put("font", FONT);
				put("footer", FOOTER);
				put("form", FORM);
				put("frame", FRAME);
				put("frameset", FRAMESET);
				put("h1", H1);
				put("h2", H2);
				put("h3", H3);
				put("h4", H4);
				put("h5", H5);
				put("h6", H6);
				put("header", HEADER);
				put("head", HEAD);
				put("hr", HR);
				put("html", HTML);
				put("iframe", IFRAME);
				put("i", I);
				put("img", IMG);
				put("input", INPUT);
				put("ins", INS);
				put("kbd", KBD);
				put("keygen", KEYGEN);
				put("label", LABEL);
				put("legend", LEGEND);
				put("li", LI);
				put("link", LINK);
				put("map", MAP);
				put("mark", MARK);
				put("menu", MENU);
				put("meta", META);
				put("meter", METER);
				put("nav", NAV);
				put("noframes", NOFRAMES);
				put("noscript", NOSCRIPT);
				put("object", OBJECT);
				put("ol", OL);
				put("optgroup", OPTGROUP);
				put("option", OPTION);
				put("output", OUTPUT);
				put("param", PARAM);
				put("p", P);
				put("pre", PRE);
				put("progress", PROGRESS);
				put("q", Q);
				put("rp", RP);
				put("rt", RT);
				put("ruby", RUBY);
				put("samp", SAMP);
				put("script", SCRIPT);
				put("section", SECTION);
				put("select", SELECT);
				put("s", S);
				put("small", SMALL);
				put("source", SOURCE);
				put("span", SPAN);
				put("strike", STRIKE);
				put("strong", STRONG);
				put("style", STYLE);
				put("sub", SUB);
				put("summary", SUMMARY);
				put("sup", SUP);
				put("table", TABLE);
				put("tbody", TBODY);
				put("td", TD);
				put("textarea", TEXTAREA);
				put("tfoot", TFOOT);
				put("thead", THEAD);
				put("th", TH);
				put("time", TIME);
				put("title", TITLE);
				put("track", TRACK);
				put("tr", TR);
				put("tt", TT);
				put("u", U);
				put("ul", UL);
				put("var", VAR);
				put("video", VIDEO);
				put("wbr", WBR);
		}});
	}
	private HtmlElements(){}
}
