package net.garmine.parser.html.attributes;

import net.garmine.parser.html.elements.HtmlElement;
/*
alternate	Links to an alternate version of the document (i.e. print page, translated or mirror)
author	Links to the author of the document
bookmark	Permanent URL used for bookmarking
help	Links to a help document
license	Links to copyright information for the document
next	The next document in a selection
nofollow	Links to an unendorsed document, like a paid link.
("nofollow" is used by Google, to specify that the Google search spider should not follow that link)
noreferrer	Specifies that the browser should not send a HTTP referer header if the user follows the hyperlink
prefetch	Specifies that the target document should be cached
prev	The previous document in a selection
search	Links to a search tool for the document
tag	A tag (keyword) for the current document
*/

public enum Rel {
	ALTERNATE, AUTHOR, BOOKMARK, HELP, LICENSE, NEXT, NOFOLLOW, NOREFERRER, PREFETCH, PREV, SEARCH, TAG;
	public static Rel parse(HtmlElement element, String str){
		try{
			return Rel.valueOf(str.toUpperCase());
		}catch(Exception e){
			return null;
		}
	}
}

