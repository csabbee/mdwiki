package io.github.kicsikrumpli.service.converter;

import java.nio.file.Path;

import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts request object to text domain object.
 * @author kicsikrumpli1
 *
 */
@Component
public class TextDocumentRequestConverter {
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
    @Autowired ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;

    /**
     * Converts request object to text domain object.
     * @param request object for creating text file
     * @return text document
     */
	public TextDocument convert(DocumentStoreCreateRequest request) {
		Path path = pathResolver.resolvePath(request.getDocumentName());
		return textDocumentBuilderFactory.getObject()
				.withAuthor(request.getAuthor())
				.withPath(path)
				.withLine(request.getContent())
				.build();
	}
}
