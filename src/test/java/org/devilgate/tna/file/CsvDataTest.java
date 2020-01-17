package org.devilgate.tna.file;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvDataTest {

	private String testData1;
	private CsvData classUnderTest;

	@BeforeEach
	void setup() {

		testData1 = "filename, origin, metadata, hash\n"
		            + "file1, London, \"a file about London\", "
		            + "e737a6b0734308a08b8586720b3c299548ff77b846e3c9c89db88b63c7ea69b6";
		classUnderTest = new CsvData(new StringReader(testData1));
	}

	@org.junit.jupiter.api.Test
	void when_headers_then_return_headers() throws IOException {
		classUnderTest.populate();
		List<String> heads = Arrays.asList("filename, origin, metadata, hash".split(","));
		assertEquals(heads, classUnderTest.headers());
	}

	@org.junit.jupiter.api.Test
	void load() {
	}
}
