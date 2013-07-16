package net.garmine.parser;

import java.io.Reader;

/**
 * Common interface for parsers
 * @author Garmine
 * @param <E> - return type
 */
public interface Parser<E> {
	
	/**
	 * Parses the given String
	 * @param raw - String to parse
	 * @return The parsed Data
	 * @throws Exception - any malfunctions during parsing
	 */
	public E parse(Reader in) throws Exception;
}
