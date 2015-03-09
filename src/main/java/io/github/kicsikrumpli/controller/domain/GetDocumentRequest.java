package io.github.kicsikrumpli.controller.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Domain object holding request data.
 * @author daniel
 *
 */
public class GetDocumentRequest {
    private String documentName;
    
    private GetDocumentRequest(Builder builder) {
        documentName = builder.documentName;
    }

    public String getDocumentName() {
        return documentName;
    }

    /**
     * Builder bean for {@link GetDocumentRequest} to construct object.
     * @author daniel
     *
     */
    @Component
    @Scope("prototype")
    public static class Builder {
        private String documentName;

        /**
         * Adds name parameter.
         * @param documentName name of document
         * @return builder instance
         */
        public Builder withDocumentName(String documentName) {
            this.documentName = documentName;
            return this;
        }
        
        /**
         * Builds {@link GetDocumentRequest}.
         * @return
         */
        public GetDocumentRequest build() {
            return new GetDocumentRequest(this);
        }
    }
}
