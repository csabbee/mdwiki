package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts get request data into request domain object {@link DocumentStoreRequest}. 
 * @author daniel
 *
 */
@Component
public class DocumentStoreRequestConverter {
    @Autowired
    private ObjectFactory<DocumentStoreRequest.Builder> getDocumentBuilderFactory;
    
    /**
     * Convert controller parameters into request domain object.
     * @param documentName
     * @return
     */
    public DocumentStoreRequest convert(String documentName) {
        return getDocumentBuilderFactory.getObject()
                .withDocumentName(documentName)
                .build();
    }
}
