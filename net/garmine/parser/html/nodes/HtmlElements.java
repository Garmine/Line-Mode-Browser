package net.garmine.parser.html.nodes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.garmine.parser.html.elements.*;

@SuppressWarnings("serial")
/**
 * A shell for an unmodifiable String->HtmlElement HashMap.
 * @author Garmine
 */
public class HtmlElements {
	/** An unmodifiable Map which provides easy parsing for all of the supported elements. */
	public static final Map<String, Class<? extends HtmlElement>> HTML_ELEMENTS;
	static{
		HTML_ELEMENTS = Collections.unmodifiableMap(new HashMap<String, Class<? extends HtmlElement>>(){{
			put("abbr", Abbr.class);
			put("acronym", Acronym.class);
			put("address", Address.class);
			put("a", A.class);
			put("applet", Applet.class);
			put("area", Area.class);
			put("article", Article.class);
			put("aside", Aside.class);
			put("audio", Audio.class);
			put("basefont", Basefont.class);
			put("base", Base.class);
			put("bdi", Bdi.class);
			put("bdo", Bdo.class);
			put("big", Big.class);
			put("b", B.class);
			put("blockquote", Blockquote.class);
			put("body", Body.class);
			put("br", Br.class);
			put("button", Button.class);
			put("canvas", Canvas.class);
			put("caption", Caption.class);
			put("center", Center.class);
			put("cite", Cite.class);
			put("code", Code.class);
			put("colgroup", Colgroup.class);
			put("col", Col.class);
			put("command", Command.class);
			put("compact", Compact.class);
			put("datalist", Datalist.class);
			put("dd", Dd.class);
			put("del", Del.class);
			put("details", Details.class);
			put("dfn", Dfn.class);
			put("dialog", Dialog.class);
			put("div", Div.class);
			put("dl", Dl.class);
			put("dt", Dt.class);
			put("embed", Embed.class);
			put("em", Em.class);
			put("fieldset", Fieldset.class);
			put("figcaption", Figcaption.class);
			put("figure", Figure.class);
			put("font", Font.class);
			put("footer", Footer.class);
			put("form", Form.class);
			put("frame", Frame.class);
			put("frameset", Frameset.class);
			put("h1", H1.class);
			put("h2", H2.class);
			put("h3", H3.class);
			put("h4", H4.class);
			put("h5", H5.class);
			put("h6", H6.class);
			put("header", Header.class);
			put("head", Head.class);
			put("hr", Hr.class);
			put("html", Html.class);
			put("iframe", Iframe.class);
			put("i", I.class);
			put("img", Img.class);
			put("input", Input.class);
			put("ins", Ins.class);
			put("kbd", Kbd.class);
			put("keygen", Keygen.class);
			put("label", Label.class);
			put("legend", Legend.class);
			put("li", Li.class);
			put("link", Link.class);
			put("map", net.garmine.parser.html.elements.Map.class);
			put("mark", Mark.class);
			put("menu", Menu.class);
			put("meta", Meta.class);
			put("meter", Meter.class);
			put("nav", Nav.class);
			put("noframes", Noframes.class);
			put("noscript", Noscript.class);
			put("object", net.garmine.parser.html.elements.Object.class);
			put("ol", Ol.class);
			put("optgroup", Optgroup.class);
			put("option", Option.class);
			put("output", Output.class);
			put("param", Param.class);
			put("p", P.class);
			put("pre", Pre.class);
			put("progress", Progress.class);
			put("q", Q.class);
			put("rp", Rp.class);
			put("rt", Rt.class);
			put("ruby", Ruby.class);
			put("samp", Samp.class);
			put("script", Script.class);
			put("section", Section.class);
			put("select", Select.class);
			put("s", S.class);
			put("small", Small.class);
			put("source", Source.class);
			put("span", Span.class);
			put("strike", Strike.class);
			put("strong", Strong.class);
			put("style", Style.class);
			put("sub", Sub.class);
			put("summary", Summary.class);
			put("sup", Sup.class);
			put("table", Table.class);
			put("tbody", Tbody.class);
			put("td", Td.class);
			put("textarea", Textarea.class);
			put("tfoot", Tfoot.class);
			put("thead", Thead.class);
			put("th", Th.class);
			put("time", Time.class);
			put("title", Title.class);
			put("track", Track.class);
			put("tr", Tr.class);
			put("tt", Tt.class);
			put("u", U.class);
			put("ul", Ul.class);
			put("var", Var.class);
			put("video", Video.class);
			put("wbr", Wbr.class);
		}});
	}
	private HtmlElements(){}
}