package io.github.kicsikrumpli.service.converter;

import java.nio.file.Path;

import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextDocumentRequestConverter {
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
    @Autowired ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;

	public TextDocument convert(DocumentStoreCreateRequest request) {
		Path path = pathResolver.resolvePath(request.getDocumentName());
		return textDocumentBuilderFactory.getObject()
				.withAuthor(request.getAuthor())
				.withPath(path)
				.withLine(request.getContent())
				.build();
	}
}
