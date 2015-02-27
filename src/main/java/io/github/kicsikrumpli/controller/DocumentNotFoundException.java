package io.github.kicsikrumpli.controller;

/**
 * Thrown when requested document cannot be found.
 * @author daniel
 *
 */
public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
