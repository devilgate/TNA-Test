package org.devilgate.tna.ui;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

import org.devilgate.tna.file.CsvFile;

public class Console {

	private static final String DEFAULT_FILE_NAME = "tnalondontest.dat";
	private static final String INITIAL_PROMPT = "Please enter the path to a CSV file:\n";
	private static final String USING_DEFAULT = "No file name given; using default of ";
	private static final String COLUMNS_LIST = "The file contains the following columns:\n";
	private static final String HEADER_SEPARATOR = " | ";
	private static final String CHANGE_COLUMNS_PROMPT =
			"Enter the name of the column you want to change:\n";
	private static final String COLUMN_NOT_EXIST =
			"\nThe column %s does not exist in the file. Please enter another "
			+ "name %n%n";
	private static final String PROMPT_FOR_EXISTING_VALUE =
			"%nPlease enter the existing value of column '%s' that you want to "
			+ "change:%n%n";
	private static final String PROMPT_FOR_NEW_VALUE =
			"%nPlease enter the new value for column '%s' that the should "
			+ "replace '%s':%n%n";
	private static final String ABOUT_TO_REPLACE_PROMPT =
			"About to replace all values of column '%s' in file '%s' having value "
			+ "'%s' with the value '%s'. A new file will be created.%n%n";
	private static final String OK_TO_GO_AHEAD = "OK to go ahead? [y/N]\n";
	private static final String NEW_FILE_CREATED = "New file %s created";
	private static final String UPDATING = "Updating...";
	private static final String UPDATE_CANCELLED = "Update cancelled";

	private CsvFile inputFile;

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		Console c = new Console();
		c.run();
	}

	private void run() throws IOException {

		System.out.println(INITIAL_PROMPT);
		String fileName = scanner.next();
		if (fileName == null || fileName.length() == 0) {
			System.out.println(USING_DEFAULT + DEFAULT_FILE_NAME + "\n");
			fileName = DEFAULT_FILE_NAME;
		}

		loadFile(fileName);
		showHeaders();
		String columnToChange = promptForHeader();

		System.out.printf(PROMPT_FOR_EXISTING_VALUE, columnToChange);
		String fromValue = scanner.next();

		System.out.printf(PROMPT_FOR_NEW_VALUE, columnToChange, fromValue);
		String toValue = scanner.next();

		System.out.printf(ABOUT_TO_REPLACE_PROMPT, columnToChange, fileName, fromValue, toValue);
		System.out.println(OK_TO_GO_AHEAD);
		if (scanner.next().equalsIgnoreCase("y")) {
			System.out.println(UPDATING);
			if (inputFile.createCopyWithReplacedText(columnToChange, fromValue, toValue)) {
				System.out.printf(NEW_FILE_CREATED, inputFile.newFileName());
				return;
			}
		}
		System.out.println(UPDATE_CANCELLED);
		System.exit(0);
	}

	private void loadFile(final String fileName) {

		Path filePath = FileSystems.getDefault().getPath(fileName);
		inputFile = new CsvFile(filePath);
		inputFile.populate();
	}

	private void showHeaders() {
		System.out.println(COLUMNS_LIST);
		StringBuilder builder = new StringBuilder();
		for (String header : inputFile.headers()) {
			builder.append(header).append(HEADER_SEPARATOR);
		}
		System.out.println(builder + "\n");
	}

	private String promptForHeader() {
		System.out.println(CHANGE_COLUMNS_PROMPT);
		String columnName = scanner.next();
		while (!inputFile.headers().contains(columnName)) {
			System.out.printf(COLUMN_NOT_EXIST, columnName);
			showHeaders();
			columnName = scanner.next();
		}

		return columnName;
	}
}
