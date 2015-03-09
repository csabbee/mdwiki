package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.controller.domain.GetDocumentRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts get request data into request domain object {@link GetDocumentRequest}. 
 * @author daniel
 *
 */
@Component
public class GetDocumentRequestConverter {
    @Autowired
    private ObjectFactory<GetDocumentRequest.Builder> getDocumentBuilderFactory;
    
    /**
     * Convert controller parameters into request domain object.
     * @param documentName
     * @return
     */
    public GetDocumentRequest convert(String documentName) {
        return getDocumentBuilderFactory.getObject()
                .withDocumentName(documentName)
                .build();
    }
}
