package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;

import com.google.common.base.Optional;

/**
 * Handles storage and retrieval of documents.
 * @author daniel
 *
 */
public interface DocumentStore<DocumentType> {
	
	/**
	 * Retrieves document from document store.
	 * @param getDocument identifies document
	 * Throws DocumentNotFoundException when document is not found
	 * @return document
	 */
	Optional<DocumentType> retrieveDocument(DocumentStoreFindRequest getDocument);
	
	/**
	 * Stores a document.
	 * @param documentRequest request object
	 */
	void storeDocument(DocumentStoreCreateRequest documentRequest);
}
