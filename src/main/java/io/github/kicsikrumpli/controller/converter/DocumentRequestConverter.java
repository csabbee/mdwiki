package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.service.domain.DocumentRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts get request data into request domain object {@link DocumentRequest}. 
 * @author daniel
 *
 */
@Component
public class DocumentRequestConverter {
    @Autowired
    private ObjectFactory<DocumentRequest.Builder> getDocumentBuilderFactory;
    
    /**
     * Convert controller parameters into request domain object.
     * @param documentName
     * @return
     */
    public DocumentRequest convert(String documentName) {
        return getDocumentBuilderFactory.getObject()
                .withDocumentName(documentName)
                .build();
    }
}
