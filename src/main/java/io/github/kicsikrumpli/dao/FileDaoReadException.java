package io.github.kicsikrumpli.dao;

/**
 * Runtime exception thrown when reading of file fails.
 * @author daniel
 *
 */
public class FileDaoReadException extends RuntimeException {
    /**
     * Instantiates exception with wrapped cause.
     * @param e
     */
    public FileDaoReadException(Exception e) {
        super(e);
    }
}
