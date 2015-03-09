package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.controller.domain.FetchDocumentRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts get request data into request domain object {@link FetchDocumentRequest}. 
 * @author daniel
 *
 */
@Component
public class FetchDocumentRequestConverter {
    @Autowired
    private ObjectFactory<FetchDocumentRequest.Builder> getDocumentBuilderFactory;
    
    /**
     * Convert controller parameters into request domain object.
     * @param documentName
     * @return
     */
    public FetchDocumentRequest convert(String documentName) {
        return getDocumentBuilderFactory.getObject()
                .withDocumentName(documentName)
                .build();
    }
}
