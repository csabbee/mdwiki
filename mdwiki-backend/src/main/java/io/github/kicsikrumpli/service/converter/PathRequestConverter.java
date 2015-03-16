package io.github.kicsikrumpli.service.converter;

import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts request object to path. 
 * @author kicsikrumpli1
 *
 */
@Component
public class PathRequestConverter {
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
	
    /**
     * Converts request object to path.
     * @param request object for finding file.
     * @return path
     */
	public Path convert(DocumentStoreFindRequest request) {
		return pathResolver.resolvePath(request.getDocumentName());
	}
}
