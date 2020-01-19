package org.devilgate.tna.file;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class CsvFile implements DelimitedData {

	private final Path path;
	private List<String> columnNames;
	private CsvData fileData;
	private Path newFile;

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

		if (!path.toFile().exists()) {
			throw new FileException(path.toString());
		}

		try (Reader reader = new FileReader(path.toFile())) {
			fileData = new CsvData(reader);
			fileData.populate();
			columnNames = fileData.headers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean scanAndReplace(String column, String from, String to) throws IOException {
		if (fileData.scanAndReplace(column, from, to)) {
			newFile = createNewPath();
			try (Writer writer = new BufferedWriter(new FileWriter(newFile.toFile()))) {
				writer.write(asString());
				return true;
			}
		}
		return false;
	}

	private Path createNewPath() {
		FileAndExtension fe = new FileAndExtension(path);
		String extension = fe.extension();
		LocalDateTime stamp = LocalDateTime.now();
		String newName = fe.name() + "-" + stamp + "." + extension;
		return path.toAbsolutePath().getParent().resolve(newName);
	}

	public String newFileName() {
		return newFile == null ? "null" : newFile.toString();
	}

}

