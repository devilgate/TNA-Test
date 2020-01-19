package org.devilgate.tna.file;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvDataTest {

	private String goodData = "filename, origin, metadata, hash\n"
	                          + "file1, London, \"a file about London\", "
	                          + "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6"
	                          + "\n" +
	                          "file2, Surrey, \"a file about The National Archives\", "
	                          + "a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564967";
	private String goodDataRecapitalised = "filename, origin, metadata, hash\n"
	                                       + "file1, London, \"A file about London\", "
	                                       +
	                                       "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6"
	                                       + "\n" +
	                                       "file2, Surrey, \"A file about The National "
	                                       + "Archives\", "
	                                       +
	                                       "a4bf0d05d8805f8c35b633ee67dc10efd6efe1cb8dfc0ecdba1040b551564967";
	private String oneEmbeddedComma = "filename, origin, metadata\n"
	                                  + "file1, London, \"a string, containing one comma\"";
	private String twoEmbeddedCommas = "filename, origin, metadata\n"
	                                   + "file1, London, \"a string, containing two commas,\"";
	private String columnNumberMismatch = "col1,col2,col3\n"
	                                      + "data1,data2,data3,data4";
	private String unbalancedQuotes = "c1, c2, c3\n"
	                                  + "val1, \"a badly-terminated string', val3";

	private CsvData classUnderTest;

	@Test
	void when_headers_then_return_headers() throws IOException {

		classUnderTest = new CsvData(new StringReader(goodData));
		classUnderTest.populate();
		List<String> heads = Arrays.asList("filename, origin, metadata, hash".split("\\s*,\\s*"));
		assertEquals(heads, classUnderTest.headers());
	}

	@Test
	void when_correctData_then_outputMatchesInput() throws IOException {

		classUnderTest = new CsvData(new StringReader(goodData));
		classUnderTest.populate();
		assertEquals(goodData, classUnderTest.asString());
	}

	@Test
	void when_oneEmbeddedComma_then_loadCorrectly() throws IOException {

		classUnderTest = new CsvData(new StringReader(oneEmbeddedComma));
		classUnderTest.populate();
		assertEquals(3, classUnderTest.headers().size());
	}

	@Test
	void when_twoEmbeddedCommas_then_loadCorrectly() throws IOException {

		classUnderTest = new CsvData(new StringReader(twoEmbeddedCommas));
		classUnderTest.populate();
		assertEquals(3, classUnderTest.headers().size());
		assertEquals(twoEmbeddedCommas, classUnderTest.asString());
	}

	@Test
	void when_columnNumbersMismatched_then_exception() {

		classUnderTest = new CsvData(new StringReader(columnNumberMismatch));
		assertThrows(MismatchedColumnsException.class, () -> classUnderTest.populate());
	}

	@Test
	void when_unbalancedQuotes_then_exception() {

		classUnderTest = new CsvData(new StringReader(unbalancedQuotes));
		assertThrows(UnbalancedQuotesException.class, () -> classUnderTest.populate());
	}

	@Test
	void when_scanAndReplace_then_replacedValueCorrect() throws IOException {

		classUnderTest = new CsvData(new StringReader(goodData));
		classUnderTest.populate();
		classUnderTest.scanAndReplace("metadata", "a file about London", "A file about London");
		classUnderTest.scanAndReplace("metadata", "a file about The National Archives", "A file "
		                                                                                + "about "
		                                                                                + "The "
		                                                                                +
		                                                                                "National "
		                                                                                +
		                                                                                "Archives");
		assertEquals(goodDataRecapitalised, classUnderTest.asString());

	}

}
