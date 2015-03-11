package io.github.kicsikrumpli.service;

import java.io.IOException;

import io.github.kicsikrumpli.dao.TextFileDao;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.converter.PathRequestConverter;
import io.github.kicsikrumpli.service.converter.TextDocumentRequestConverter;
import io.github.kicsikrumpli.service.converter.TextDocumentResponseConverter;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

/**
 * Markdown document store - find, crate.
 * @author kicsikrumpli1
 *
 */
@Component
public class MarkdownDocumentStore implements DocumentStore<MarkdownDocument> {
    @Autowired
    private TextFileDao fileDao;
    @Autowired
    private PathRequestConverter pathRequestConverter; 
    @Autowired
    private TextDocumentResponseConverter textDocumentResponseConverter;
    @Autowired
    private TextDocumentRequestConverter textDocumentRequestConverter;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(DocumentStoreFindRequest documentRequest) {
	    Optional<TextDocument> textFile = fileDao.readFile(pathRequestConverter.convert(documentRequest));
	    Optional<MarkdownDocument> document = textDocumentResponseConverter.convert(textFile);
		return document;
	}

    @Override
	public void storeDocument(DocumentStoreCreateRequest documentRequest) {
    	try {
			fileDao.createFile(textDocumentRequestConverter.convert(documentRequest));
		} catch (IOException e) {
			throw new CannotWriteDocumentException(e);
		}
	}
}
