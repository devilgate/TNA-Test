package org.devilgate.tna.file;

import java.util.List;

class MismatchedColumnsException extends RuntimeException {

	MismatchedColumnsException(final List<String> line, List<String> headers) {

		super(String.format("The line %s has a different number of columns from the headers %s ",
		                    String.join(", ", line), String.join(", ", headers)));
	}

	MismatchedColumnsException() {}
}
