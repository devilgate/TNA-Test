package org.devilgate.tna.file;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;

public class CsvFile implements DelimitedFile {

	private final Path path;
	private List<String> columnNames;
	private CsvData fileData;

	public CsvFile(final Path path) {

		this.path  = path;
	}

	@Override
	public List<String> headers() {

		return columnNames;
	}

	@Override
	public String asString() {
		return fileData.asString();
	}

	@Override
	public void populate() {

		try (Reader reader = new FileReader(path.toFile())) {
			fileData = new CsvData(reader);
			fileData.populate();
			columnNames = fileData.headers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

