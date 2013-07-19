package net.garmine.parser.html.attributes;

import net.garmine.parser.html.nodes.HtmlElement;

/*
col	Specifies that the cell is a header for a column
row	Specifies that the cell is a header for a row
colgroup	Specifies that the cell is a header for a group of columns
rowgroup	Specifies that the cell is a header for a group of rows
*/

public enum Scope {
	COL, ROW, COLGROUP, ROWGROUP;
	
	public static Scope parse(HtmlElement element, String str){
		switch(str){
			case "col":
				return COL;
				
			case "row":
				return ROW;
				
			case "colgroup":
				return COLGROUP;
				
			case "rowgroup":
				return ROWGROUP;
				
			default:
				return null;
		}
	}
}

