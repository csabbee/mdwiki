package io.github.kicsikrumpli.dao;

/**
 * Thrown when file dao cannot write file.
 * @author daniel
 *
 */
public class FileDaoWriteException extends RuntimeException {
    public FileDaoWriteException(Throwable cause) {
        super(cause);
    }
}
