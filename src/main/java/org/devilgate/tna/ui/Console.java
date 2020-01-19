package org.devilgate.tna.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.devilgate.tna.file.CsvFile;

public class Console {

	private static final String DEFAULT_FILE_NAME = "tnalondontest.dat";

	private final CsvFile inputFile;

	public static void main(String[] args) throws URISyntaxException, IOException {

		Console c = new Console();
	}

	private Console() throws URISyntaxException, IOException {

		// final URI uri = getClass().getResource(DEFAULT_FILE_NAME).toURI();
		// Map<String, String> env = new HashMap<>();
		// env.put("create", "true");
		// FileSystem zipfs = FileSystems.newFileSystem(uri, env);
		// Path inPath = Paths.get(uri);
		// inputFile = new CsvFile(inPath);

		// InputStream in = getClass().getResourceAsStream(DEFAULT_FILE_NAME);

		Path filePath = FileSystems.getDefault().getPath(DEFAULT_FILE_NAME);
		inputFile = new CsvFile(filePath);
		// inputFile = new CsvFile(inPath);
		inputFile.populate();
		System.out.println(inputFile.asString());
	}

	private void run() {


	}
}
