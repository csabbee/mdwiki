package io.github.kicsikrumpli.service.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Domain object holding request data.
 * @author daniel
 *
 */
public class DocumentRequest {
    private String documentName;
    
    private DocumentRequest(Builder builder) {
        documentName = builder.documentName;
    }

    public String getDocumentName() {
        return documentName;
    }

    /**
     * Builder bean for {@link DocumentRequest} to construct object.
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
         * Builds {@link DocumentRequest}.
         * @return
         */
        public DocumentRequest build() {
            return new DocumentRequest(this);
        }
    }
}
