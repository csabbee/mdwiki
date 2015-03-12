package io.github.kicsikrumpli.service.converter;

import java.nio.charset.Charset;
import java.nio.file.Path;

import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Converts request object to text domain object.
 * @author kicsikrumpli1
 *
 */
@Component
public class TextDocumentRequestConverter {
    @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
    private Charset defaultCharset;
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
    @Autowired
    private ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;

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
				.withEncoding(defaultCharset)
				.build();
	}

    void setDefaultCharset(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }
}
