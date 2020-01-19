package org.devilgate.tna.file;

import java.io.IOException;
import java.util.List;

/**
 * Represents delimited data, such as CSV, abstracted from any underlying file.
 */
public interface DelimitedData {

	/**
	 * Load the data from the underlying data source (which should be set at construction).
	 */
	void populate() throws IOException;

	/**
	 * Return the list of header values for the data, if any.
	 *
	 * @return the header values, or an empty list.
	 */
	List<String> headers();

	/**
	 * Return the contents of the delimited data as a String.
	 *
	 * @return the delimited data, after parsing
	 */
	String asString();
}
