package io.github.kicsikrumpli.controller;

import io.github.kicsikrumpli.controller.converter.DocumentStoreRequestConverter;
import io.github.kicsikrumpli.service.DocumentStore;
import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for {@link MarkdownJsonController}.
 * @author daniel
 *
 */
public class MarkdownJsonControllerTest {
    private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
    @InjectMocks
    private MarkdownJsonController underTest;
    @Mock
    private DocumentStore<MarkdownDocument> mockDocumentStore;
    @Mock
    private DocumentStoreRequestConverter mockDocumentRequestConverter;
    private String mockDocumentName;
    private DocumentStoreRequest mockDocumentStoreRequest = createMockDocumentStoreRequest();

    private DocumentStoreRequest createMockDocumentStoreRequest() {
        return new DocumentStoreRequest.Builder().withDocumentName(MOCK_DOCUMENT_NAME).build();
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockDocumentName = MOCK_DOCUMENT_NAME;
    }
    
    // testGetMarkdownDocumentShouldConvertDocumentNameToRequestObject()
    // testGetMarkdownDocumentShouldReturnDocumentWhenFileIsPresent()
    // testGetMarkdownDocumentShouldThrowDocumentNotFoundExceptionWhenFileIsNotPresent()
    
    @Test
    public void testGetMarkdownDocumentShouldConvertDocumentNameToRequestObject() {
        // GIVEN
        BDDMockito.given(mockDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreRequest);
        
        // WHEN
        MarkdownDocument response = underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
    }

}
