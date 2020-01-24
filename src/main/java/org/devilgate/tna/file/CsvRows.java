package org.devilgate.tna.file;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Holds the rows of CSV data.
 */
public class CsvRows {

	private List<List<String>> rows = new LinkedList<>();

	/**
	 * Adds a row of CSV data to the object.
	 */
	public void addRow(String rowData) {

		checkForUnbalancedQuotes(rowData);
		rows.add(parse(rowData));

		// Check for mismatched headers and detail column counts
		if (rows.size() > 1 && headers().size() != rows.get(rows.size() - 1).size()) {
			throw new MismatchedColumnsException();
		}
	}

	/**
	 * Returns the header values of the CSV data
	 * @return a list of header names
	 */
	public List<String> headers() {

		// The first row should be the headers
		return rows.get(0).stream().map(String::trim).collect(Collectors.toList());
	}

	/**
	 * Parses a line of CSV data
	 * @param line CSV data
	 * @return a list of parsed values
	 */
	private List<String> parse(String line) {

		List<String> parsedRow = new LinkedList<>();

		ListIterator<String> iterator = Arrays.asList(line.split(",")).listIterator();
		while (iterator.hasNext()) {

			parsedRow.add(nextPart(iterator));
		}

		return parsedRow;
	}

	/**
	 * Returns the next part of the CSV line
	 */
	private String nextPart(final ListIterator<String> iterator) {

		String part = iterator.next();
		if (part.trim().startsWith("\"")) {
			return mergeParts(iterator, part).replaceAll("\\s*\"", "");
		}
		return part.trim();
	}

	/**
	 * Recombines the parts of a quoted column that were split up by the intial parsing step.
	 */
	private String mergeParts(final ListIterator<String> iterator, String firstPart) {

		if (firstPart.trim().endsWith("\"")) {
			return firstPart.trim();
		}

		// We are reconstructing a string field that has been split because it contains one or
		// more commas. If the iterator has no more elements, we have erroneous data.
		if (!iterator.hasNext()) {
			throw new MismatchedColumnsException();
		}

		StringBuilder building = new StringBuilder(firstPart);
		String nextPart = iterator.next();
		return appendNextPart(iterator, nextPart, building);
	}

	private String appendNextPart(final ListIterator<String> iterator, String nextPart,
			final StringBuilder builder) {

		builder.append(",");
		builder.append(nextPart);

		// There shouldn't ever be many, so a wee bit of recursion won't hurt.
		if (!nextPart.trim().endsWith("\"")) {
			nextPart = iterator.next();
			return appendNextPart(iterator, nextPart, builder);
		}

		return builder.toString();
	}

	public boolean scanAndReplace(String column, String from, String to) {

		boolean changed = false;

		// Get the index of the column
		int columnIndex = headers().indexOf(column);
		if (columnIndex < 0) {
			throw new ColumnNotFoundException(column, headers());
		}

		for (List<String> row : rows) {

			changed = updateColumn(row, columnIndex, from, to);
		}
		return changed;
	}

	private boolean updateColumn(final List<String> row, final int columnIndex,
			final String from, final String to) {

		boolean changed = false;
		if (row.get(columnIndex).equals(from)) {
			row.set(columnIndex, to);
			changed = true;
		}
		return changed;
	}

	private void checkForUnbalancedQuotes(final String line) {

		// An odd number of double-quote characters indicates faulty data.
		long noOfQuotes = line.chars().filter(ch -> ch == '"').count();
		if (noOfQuotes % 2 != 0) {

			throw new UnbalancedQuotesException(line);
		}
	}

	public Stream<List<String>> stream() {
		return rows.stream();
	}
}
