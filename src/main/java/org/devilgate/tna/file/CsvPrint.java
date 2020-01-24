package org.devilgate.tna.file;

import java.util.stream.Collectors;

public class CsvPrint {

	private final CsvRows rows;

	CsvPrint(final CsvRows rows) {

		this.rows = rows;
	}

	public String print() {

		return rows.stream().map(
				row -> row.stream().map(
						field -> {

							// Wrap in quotes if the data contains space or commas
							if (field.contains(" ") || field.contains(",")) {
								field = "\"" + field + "\"";
							}
							return field;
						}).collect(Collectors.joining(", "))
		                        ).collect(Collectors.joining("\n"));
	}
}
