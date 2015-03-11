package io.github.kicsikrumpli.service.strategy;

import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;

import java.nio.file.Path;

/**
 * Resolves Path to a requested document.
 * @author daniel
 *
 */
public interface PathResolverStrategy {

    /**
     * Resolves {@link Path} from {@link DocumentStoreFindRequest}
     * @param documentRequest request object
     * @return resolved path
     */
    Path resolvePath(DocumentStoreFindRequest documentRequest);
}