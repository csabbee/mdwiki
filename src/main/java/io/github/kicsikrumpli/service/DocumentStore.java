package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.service.domain.BaseDocument;
import io.github.kicsikrumpli.service.domain.DocumentRequest;

import com.google.common.base.Optional;

/**
 * Handles storage and retrieval of documents.
 * @author daniel
 *
 */
public interface DocumentStore<DocumentType extends BaseDocument> {
	
	/**
	 * Retireves document from document store.
	 * @param getDocument identifies document
	 * Throws DocumentNotFoundException when document is not found
	 * @return document
	 */
	Optional<DocumentType> retrieveDocument(DocumentRequest getDocument);
	
	/**
	 * Stores a document.
	 * @param document
	 */
	void storeDocument(DocumentType document);
}
