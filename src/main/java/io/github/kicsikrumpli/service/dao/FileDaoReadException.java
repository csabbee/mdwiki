package io.github.kicsikrumpli.service.dao;

/**
 * Runtime exception thrown when file access fails.
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
