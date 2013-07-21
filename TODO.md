#TODO

##Attributes

Due to some amazingly terrible design decisions the package net.garmine.parser.html.attributes contains a class / each existing HTML attribute. That's around 160 classes! And each of these classes contain a single public static T parse(HtmlElement element, String str); that parses and returns some meaningful result depending on the value of the attribute. The attribute's type is determined by a String switch in each HtmlElement, which is needed in order to set the value of the elements' attribute fields.

A possible solution would be to use interfaces on the HtmlElements instead (e.g. hasAttributeName), and then a single class could handle each and every field with the help of a HashMap - this'd greatly reduce duplicate code at the cost of ~160 Interfaces instead of Classes and a Class that actually parses the attributes. And the HtmlElements would still have to contain each field...

The laziest solution would be to store everything in a String-String HashMap, however, that'd make it imposssible to make the HtmlElements easy-to-use and typesafe.

**TL;DR** this needs a rewrite!

##Other

HTTP-lib
 * Redesign automatic parsing of incoming data
 * Redesign HttpURLConnection handling (a bit)
 * Redesign Cookie handling

HTML-tokenizer
 * Callbacks (e.g. for &lt;meta&gt;'s encoding)

HTML-tree
 * Legal/illegal children/parent lists

Display
 * Every single thing.

