package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.controller.domain.GetDocumentRequest;
import io.github.kicsikrumpli.dao.FileDao;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

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
    private ObjectFactory<PathBuilder> pathBuilderFactory;
    @Autowired
    private ObjectFactory<MarkdownDocument.Builder> markdownDocuementBuilderFactory;
    @Autowired
    private MarkdownPathResolver markdownPathResolver;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(GetDocumentRequest documentRequest) {
	    Optional<String> content = fileDao.readFile(markdownPathResolver.resolvePath(documentRequest));
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
        return markdownDocuementBuilderFactory.getObject()
            .withName(documentName)
            .withAuthor(defaultAuthor)
            .withContent(content)
            .build();
    }

    void setDefaultAuthor(String defaultAuthor) {
		this.defaultAuthor = defaultAuthor;
	}
}
