package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.dao.TextFileDao;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.converter.TextDocumentConverter;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class MarkdownDocumentStore implements DocumentStore<MarkdownDocument> {
    @Autowired
    private TextFileDao fileDao;
    @Autowired
    private MarkdownPathResolverStrategy pathResolver;
    @Autowired
    private TextDocumentConverter textDocumentConverter;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(DocumentStoreFindRequest documentRequest) {
	    Optional<TextDocument> textFile = fileDao.readFile(pathResolver.resolvePath(documentRequest));
	    Optional<MarkdownDocument> document = textDocumentConverter.convert(textFile);
		return document;
	}

    @Override
	public void storeDocument(DocumentStoreCreateRequest documentRequest) {
	}
}
