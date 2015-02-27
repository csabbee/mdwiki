package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.domain.MarkdownDocument;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class MarkdownDocumentStore implements DocumentStore<MarkdownDocument> {
    @Value("${foo}")
    private String defaultRoot;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(String documentName) {
		return Optional.of(createDummyDocument());
	}

    @Override
	public void storeDocument(MarkdownDocument document) {
		// TODO Auto-generated method stub
	}

    private MarkdownDocument createDummyDocument() {
        return new MarkdownDocument.Builder()
            .withName(defaultRoot + "dummyName")
            .withAuthor("dummyAuthor")
            .withContent("dummyContent")
            .build();
    }
    
}
