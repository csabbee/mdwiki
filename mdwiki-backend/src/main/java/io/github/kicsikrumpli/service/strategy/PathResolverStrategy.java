package io.github.kicsikrumpli.service.strategy;

import java.nio.file.Path;

/**
 * Resolves Path to a requested document.
 * @author daniel
 *
 */
public interface PathResolverStrategy {

    /**
     * Resolves {@link Path} for file name.
     * @param filaName name of file
     * @return resolved path
     */
    Path resolvePath(String filaName);
}