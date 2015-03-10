package io.github.kicsikrumpli.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.controller.converter.DocumentStoreRequestConverter;
import io.github.kicsikrumpli.service.DocumentStore;
import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;

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
    private DocumentStoreRequest mockDocumentStoreRequest;
	private MarkdownDocument mockMarkdownDocument;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockDocumentName = MOCK_DOCUMENT_NAME;
        mockDocumentStoreRequest = createMockDocumentStoreRequest();
        mockMarkdownDocument = createMockMarkdownDocument();
    }

    @Test
    public void testGetMarkdownDocumentShouldConvertDocumentNameToRequestObject() {
        // GIVEN
        given(mockDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreRequest)).willReturn(Optional.of(mockMarkdownDocument));
        
        // WHEN
        underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        Mockito.verify(mockDocumentRequestConverter).convert(mockDocumentName);
    }
    
    @Test(expected = DocumentNotFoundException.class)
    public void testGetMarkdownDocumentShouldThrowDocumentNotFoundExceptionWhenFileIsNotPresent() {
        // GIVEN
        given(mockDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreRequest)).willReturn(Optional.<MarkdownDocument>absent());
        
        // WHEN
        underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        Mockito.verify(mockDocumentRequestConverter).convert(mockDocumentName);
    }
    
    @Test
    public void testGetMarkdownDocumentShouldReturnDocumentWhenFileIsPresent() {
        // GIVEN
        given(mockDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreRequest)).willReturn(Optional.of(mockMarkdownDocument));
        
        // WHEN
        MarkdownDocument request = underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        assertThat(request, Matchers.is(mockMarkdownDocument));
    }

	private MarkdownDocument createMockMarkdownDocument() {
		return new MarkdownDocument.Builder().build();
	}

    private DocumentStoreRequest createMockDocumentStoreRequest() {
        return new DocumentStoreRequest.Builder().withDocumentName(MOCK_DOCUMENT_NAME).build();
    }
}
