package org.devilgate.tna.file;

class FileException extends RuntimeException {
	FileException(final String s) {

		super(String.format("The file %s was not found", s));
	}
}
