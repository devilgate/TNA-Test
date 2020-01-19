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
	                          + "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6";;
	private String oneEmbeddedComma = "filename, origin, metadata, hash\n"
	                                  + "file1, London, \"a string, containing one comma\", "
	                                  + "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6";
	private String twoEmbeddedCommas = "filename, origin, metadata, hash\n"
	                                  + "file1, London, \"a string, containing two commas,\", "
	                                  + "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6";
	private String columnNumberMismatch = "col1,col2,col3\n"
	                                     + "data1,data2,data3,data4";

	private CsvData classUnderTest;

	@Test
	void when_headers_then_return_headers() throws IOException {
		
		classUnderTest = new CsvData(new StringReader(goodData));
		classUnderTest.populate();
		List<String> heads = Arrays.asList("filename, origin, metadata, hash".split(","));
		assertEquals(heads, classUnderTest.headers());
	}

	@Test
	void when_oneEmbeddedComma_then_loadCorrectly() throws IOException {

		classUnderTest = new CsvData(new StringReader(oneEmbeddedComma));
		classUnderTest.populate();
		assertEquals(4,classUnderTest.headers().size());
	}

	@Test
	void when_twoEmbeddedCommas_then_loadCorrectly() throws IOException {

		classUnderTest = new CsvData(new StringReader(twoEmbeddedCommas));
		classUnderTest.populate();
		assertEquals(4,classUnderTest.headers().size());
		assertEquals(twoEmbeddedCommas, classUnderTest.asString());
	}

	@Test
	void when_columnNumbersMismatched_then_exception() throws IOException {

		classUnderTest = new CsvData(new StringReader(columnNumberMismatch));
		assertThrows(MismatchedColumnsException.class, () -> classUnderTest.populate());
	}

}
