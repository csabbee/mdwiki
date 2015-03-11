package io.github.kicsikrumpli.service;

/**
 * Thrown when document cannot be written.
 * @author kicsikrumpli1
 *
 */
public class CannotWriteDocumentException extends RuntimeException {
	public CannotWriteDocumentException(Throwable cause) {
		super(cause);
	}
}
