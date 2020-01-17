package org.devilgate.tna.file;

class UnbalancedQuotesException extends RuntimeException
{
	UnbalancedQuotesException(final String line) {

		super("Unbalanced quote characters ('\"') found in line: " + line);
	}
}
