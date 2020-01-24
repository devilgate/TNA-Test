package org.devilgate.tna.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CsvData implements DelimitedData {

	private final BufferedReader reader;
	private CsvRows rows = new CsvRows();

	CsvData(Reader reader) {

		this.reader = new BufferedReader(reader);
	}

	@Override
	public void populate() throws IOException {

		String line = reader.readLine();
		while (line != null) {
			rows.addRow(line);
			line = reader.readLine();
		}
	}

	@Override
	public List<String> headers() {
		return rows.headers();
	}

	@Override
	public String asString() {

		return new CsvPrint(rows).print();
	}

	boolean scanAndReplace(String column, String from, String to) {

		return rows.scanAndReplace(column, from, to);
	}
}
