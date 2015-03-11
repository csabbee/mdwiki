package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts get request data into request domain object {@link DocumentStoreFindRequest}. 
 * @author daniel
 *
 */
@Component
public class DocumentStoreFindRequestConverter {
    @Autowired
    private ObjectFactory<DocumentStoreFindRequest.Builder> documentStoreRequestBuilderFactory;
    
    /**
     * Convert controller parameters into request domain object.
     * @param documentName
     * @return
     */
    public DocumentStoreFindRequest convert(String documentName) {
        return documentStoreRequestBuilderFactory.getObject()
                .withDocumentName(documentName)
                .build();
    }
}
