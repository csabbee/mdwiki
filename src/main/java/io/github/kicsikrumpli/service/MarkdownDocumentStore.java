package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.dao.FileDao;
import io.github.kicsikrumpli.service.domain.DocumentRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolutionStrategy;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class MarkdownDocumentStore implements DocumentStore<MarkdownDocument> {
    @Value("${DEFAULT_AUTHOR}")
    private String defaultAuthor;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ObjectFactory<MarkdownDocument.Builder> docuementBuilderFactory;
    @Autowired
    private MarkdownPathResolutionStrategy pathResolver;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(DocumentRequest documentRequest) {
	    Optional<String> content = fileDao.readFile(pathResolver.resolvePath(documentRequest));
	    MarkdownDocument document = null;
	    if (content.isPresent()) {
	        document = createSimpleDocument(documentRequest.getDocumentName(), content.get());
	    }
		return Optional.fromNullable(document);
	}

    @Override
	public void storeDocument(MarkdownDocument document) {
		throw new UnsupportedOperationException("store operation not yet implemented");
	}
    
    private MarkdownDocument createSimpleDocument(String documentName, String content) {
        return docuementBuilderFactory.getObject()
            .withName(documentName)
            .withAuthor(defaultAuthor)
            .withContent(content)
            .build();
    }

    void setDefaultAuthor(String defaultAuthor) {
		this.defaultAuthor = defaultAuthor;
	}
}
