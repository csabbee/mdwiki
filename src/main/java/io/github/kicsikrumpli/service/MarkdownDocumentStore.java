package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.dao.FileDao;
import io.github.kicsikrumpli.domain.MarkdownDocument;

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

	@Override
	public Optional<MarkdownDocument> retrieveDocument(String documentName) {
	    Optional<String> content = fileDao.readFile(documentName + ".md");
	    MarkdownDocument document = null;
	    if (content.isPresent()) {
	        document = createSimpleDocument(documentName, content.get());
	    }
		return Optional.fromNullable(document);
	}

    @Override
	public void storeDocument(MarkdownDocument document) {
		// TODO Auto-generated method stub
	}

    private MarkdownDocument createSimpleDocument(String documentName, String content) {
        return new MarkdownDocument.Builder()
            .withName(documentName)
            .withAuthor(defaultAuthor)
            .withContent(content)
            .build();
    }
    
}
