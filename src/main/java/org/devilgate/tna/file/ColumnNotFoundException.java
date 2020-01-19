package org.devilgate.tna.file;

import java.util.List;

class ColumnNotFoundException extends RuntimeException {
	ColumnNotFoundException(final String column, final List<String> columnNames) {
		super(String.format("Column '%s' is not in list of columns '%s'", column, columnNames));
	}
}
