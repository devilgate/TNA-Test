package org.devilgate.tna.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvData implements DelimitedData {

	private final BufferedReader reader;
	private List<String> columnNames;
	private List<Map<String, String>> rows = new LinkedList<>();


	CsvData(Reader reader) {

		this.reader = new BufferedReader(reader);
	}

	@Override
	public void populate() throws IOException {

		String headerLine = reader.readLine();

		// Assumption: the header names are not delimited with quotes.
		columnNames = Arrays.asList(headerLine.split(","));

		String line = reader.readLine();
		while (line != null) {

			// We'll store the fields and values in a LinkedHashMap to keep the columns in the
			// received order
			Map<String, String> lineMap = new LinkedHashMap<>();

			checkForUnbalancedQuotes(line);

			// Check for the data containing commas within quoted text.
			List<String> splitLine = Arrays.asList(line.split(","));
			if (splitLine.size() != columnCount()) {

				// Looks like there are commas in the quoted data. Need to go deeper.
				splitLine = rebuildLine(splitLine);
			}

			// If at this point the number of data points does not match the number of
			// headers, we als have an invalid file.
			checkForWrongNumberOfColumns(splitLine);

			for (int i = 0; i < columnNames.size(); i++) {
				lineMap.put(columnNames.get(i), splitLine.get(i).trim().replaceAll("\"", ""));
			}

			rows.add(lineMap);

			line = reader.readLine();
		}
	}

	@Override
	public String asString() {

		return String.join(",", headers())
		       + "\n"
		       + rows.stream().map(
		       		r -> r.entrySet().stream().map(
		       				(entry) -> {

						        if (entry.getValue().contains(" ") || entry.getValue()
								                                              .contains(",")) {
							        entry.setValue("\"" + entry.getValue() + "\"");
						        }
						        return entry.getValue();
		       				}).collect(Collectors.joining(", "))
			        ).collect(Collectors.joining("\n"));
	}

	public List<String> headers() {
		return columnNames;
	}

	private void checkForUnbalancedQuotes(final String line) {

			// An odd number of double-quote characters indicates faulty data.
			long noOfQuotes = line.chars().filter(ch -> ch == '"').count();
			if (noOfQuotes % 2 != 0) {

				throw new UnbalancedQuotesException(line);
			}
		}

		private void checkForWrongNumberOfColumns(final List<String> splitLine) {

			if (splitLine.size() != columnCount()) {

				throw new MismatchedColumnsException(splitLine, headers());
			}
		}

		private List<String> rebuildLine(final List<String> splitLine) {

			final List<String> newSplitLine = new LinkedList<>();

			// Find the fields with quotes
			for (int i = 0; i < splitLine.size(); i++) {

				// There's some danger of an index out of bounds here, but it shouldn't
				// be possible because of the earlier checks.
				if (splitLine.get(i).trim().startsWith("\"")) {

					// The field could contain multiple commas, so we loop until we find a
					// closing quote.
					StringBuffer buffer = new StringBuffer(splitLine.get(i));
					for (int j = i + 1; j < splitLine.size(); j++) {
						i++;
						buffer.append(",").append(splitLine.get(j));
						if (splitLine.get(j).endsWith("\"")) {
							j = splitLine.size() + 1;
						}
					}

					newSplitLine.add(buffer.toString());

				} else {
					newSplitLine.add(splitLine.get(i));
				}

			}
			return newSplitLine;
		}

	private int columnCount() {

		return columnNames.size();
	}
}
