package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;

import com.google.common.base.Optional;

/**
 * Handles storage and retrieval of documents.
 * @author daniel
 *
 */
public interface DocumentStore<DocumentType> {
	
	/**
	 * Retireves document from document store.
	 * @param getDocument identifies document
	 * Throws DocumentNotFoundException when document is not found
	 * @return document
	 */
	Optional<DocumentType> retrieveDocument(DocumentStoreRequest getDocument);
	
	/**
	 * Stores a document.
	 * @param document
	 */
	void storeDocument(DocumentType document);
}
