package org.devilgate.tna.file;

import java.nio.file.Path;

/**
 * Represents a file whose data is delimited by some character or character combination, such as
 * CSV or TSV.
 */
public interface DelimitedFile extends DelimitedData {

	static DelimitedFile getInstance(Path path) {

		return new CsvFile(path);
	}
}
