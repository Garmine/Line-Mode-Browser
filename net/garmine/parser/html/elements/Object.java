package net.garmine.parser.html.elements;

import net.garmine.parser.html.tokenizer.tokens.HtmlAttributeToken;
import net.garmine.parser.html.attributes.Align;
import net.garmine.parser.html.attributes.Archive;
import net.garmine.parser.html.attributes.Border;
import net.garmine.parser.html.attributes.Classid;
import net.garmine.parser.html.attributes.Codebase;
import net.garmine.parser.html.attributes.Codetype;
import net.garmine.parser.html.attributes.Data;
import net.garmine.parser.html.attributes.Declare;
import net.garmine.parser.html.attributes.Form;
import net.garmine.parser.html.attributes.Height;
import net.garmine.parser.html.attributes.Hspace;
import net.garmine.parser.html.attributes.Name;
import net.garmine.parser.html.attributes.Standby;
import net.garmine.parser.html.attributes.Type;
import net.garmine.parser.html.attributes.Usemap;
import net.garmine.parser.html.attributes.Vspace;
import net.garmine.parser.html.attributes.Width;

public class Object extends Element {
	public  align;
	public  archive;
	public  border;
	public  classid;
	public  codebase;
	public  codetype;
	public  data;
	public  declare;
	public  form;
	public  height;
	public  hspace;
	public  name;
	public  standby;
	public  type;
	public  usemap;
	public  vspace;
	public  width;

	public Object(Element parent, HtmlAttributeToken[] attrs){
		super(parent, attrs);

		for(HtmlAttributeToken attr:attrs){
			String v = attr.getValue();
			switch(attr.getName()){
				case "align":
					align = Align.parse(this, v);
					break;
				case "archive":
					archive = Archive.parse(this, v);
					break;
				case "border":
					border = Border.parse(this, v);
					break;
				case "classid":
					classid = Classid.parse(this, v);
					break;
				case "codebase":
					codebase = Codebase.parse(this, v);
					break;
				case "codetype":
					codetype = Codetype.parse(this, v);
					break;
				case "data":
					data = Data.parse(this, v);
					break;
				case "declare":
					declare = Declare.parse(this, v);
					break;
				case "form":
					form = Form.parse(this, v);
					break;
				case "height":
					height = Height.parse(this, v);
					break;
				case "hspace":
					hspace = Hspace.parse(this, v);
					break;
				case "name":
					name = Name.parse(this, v);
					break;
				case "standby":
					standby = Standby.parse(this, v);
					break;
				case "type":
					type = Type.parse(this, v);
					break;
				case "usemap":
					usemap = Usemap.parse(this, v);
					break;
				case "vspace":
					vspace = Vspace.parse(this, v);
					break;
				case "width":
					width = Width.parse(this, v);
					break;
			}
		}
	}
}

