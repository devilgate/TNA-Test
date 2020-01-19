package org.devilgate.tna.file;

import java.nio.file.Path;

class FileAndExtension {

	private final Path path;
	private String name;
	private String extension;

	FileAndExtension(final Path path) {
		this.path = path;
	}

	void split() {
		String fullName = path.getFileName().toString();
		if (fullName.contains(".")) {
			int separatorIndex = fullName.lastIndexOf(".");
			name = fullName.substring(0, separatorIndex);
			extension = fullName.substring(separatorIndex + 1);
		} else {
			name = fullName;
		}
	}

	String name() {
		if (name == null) {
			split();
		}
		return name;
	}

	String extension() {
		if (extension == null) {
			split();
		}
		return extension;
	}
}
