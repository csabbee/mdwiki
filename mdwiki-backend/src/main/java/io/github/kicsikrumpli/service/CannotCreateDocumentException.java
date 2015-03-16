package io.github.kicsikrumpli.service;

/**
 * Thrown when document cannot be written.
 * @author kicsikrumpli1
 *
 */
public class CannotCreateDocumentException extends RuntimeException {
	public CannotCreateDocumentException(Throwable cause) {
		super(cause);
	}
}
