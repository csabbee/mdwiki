package io.github.kicsikrumpli.service.converter;

import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PathRequestConverter {
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
	
	public Path convert(DocumentStoreFindRequest request) {
		return pathResolver.resolvePath(request.getDocumentName());
	}
}
