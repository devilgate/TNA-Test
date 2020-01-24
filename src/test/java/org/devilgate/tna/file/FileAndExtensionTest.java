package org.devilgate.tna.file;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAndExtensionTest {

	@Test
	void when_filenameHasExtension_then_correctSplit() {

		String filename = "/Users/martin/file.dat";
		Path path = Paths.get(filename);

		FileAndExtension classUnderTest = new FileAndExtension(path);
		assertEquals("file", classUnderTest.name());
		assertEquals("dat", classUnderTest.extension());
	}

}
