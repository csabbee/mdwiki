package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.domain.BaseDocument;

import com.google.common.base.Optional;

/**
 * Handles storage and retrieval of documents.
 * @author daniel
 *
 */
public interface DocumentStore<DocumentType extends BaseDocument> {
	
	/**
	 * Retireves document from document store.
	 * @param documentName identifies document
	 * Throws DocumentNotFoundException when document is not found
	 * @return document
	 */
	Optional<DocumentType> retrieveDocument(String documentName);
	
	/**
	 * Stores a document.
	 * @param document
	 */
	void storeDocument(DocumentType document);
}
