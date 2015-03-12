package io.github.kicsikrumpli.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.controller.converter.DocumentStoreCreateRequestConverter;
import io.github.kicsikrumpli.controller.converter.DocumentStoreFindRequestConverter;
import io.github.kicsikrumpli.controller.domain.MarkdownDocumentForm;
import io.github.kicsikrumpli.service.CannotCreateDocumentException;
import io.github.kicsikrumpli.service.DocumentStore;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.io.IOException;

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
    private static final String MOCK_AUTHOR = "mock-author";
    private static final String MOCK_CONTENT = "mock-content";
    private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
    
    @InjectMocks
    private MarkdownJsonController underTest;
    @Mock
    private DocumentStore<MarkdownDocument> mockDocumentStore;
    @Mock
    private DocumentStoreFindRequestConverter mockFindDocumentRequestConverter;
    @Mock
    private DocumentStoreCreateRequestConverter mockCreateDocumentRequestConveter;

    private String mockDocumentName;
    private DocumentStoreFindRequest mockDocumentStoreFindRequest;
	private MarkdownDocument mockMarkdownDocument;
    private MarkdownDocumentForm mockMarkdownDocumentForm;
    private DocumentStoreCreateRequest mockDocumentStoreCreateRequest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockDocumentName = MOCK_DOCUMENT_NAME;
        mockDocumentStoreFindRequest = createMockDocumentStoreFindRequest();
        mockDocumentStoreCreateRequest = createMockDocumentStoreCreateRequest();
        mockMarkdownDocumentForm = createMockMarkdownDocumentForm();
        mockMarkdownDocument = createMockMarkdownDocument();
    }

    @Test
    public void testGetMarkdownDocumentShouldConvertDocumentNameToRequestObject() {
        // GIVEN
        given(mockFindDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreFindRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreFindRequest)).willReturn(Optional.of(mockMarkdownDocument));
        
        // WHEN
        underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        Mockito.verify(mockFindDocumentRequestConverter).convert(mockDocumentName);
    }
    
    @Test(expected = DocumentNotFoundException.class)
    public void testGetMarkdownDocumentShouldThrowDocumentNotFoundExceptionWhenFileIsNotPresent() {
        // GIVEN
        given(mockFindDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreFindRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreFindRequest)).willReturn(Optional.<MarkdownDocument>absent());
        
        // WHEN
        underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        Mockito.verify(mockFindDocumentRequestConverter).convert(mockDocumentName);
    }
    
    @Test
    public void testGetMarkdownDocumentShouldReturnDocumentWhenFileIsPresent() {
        // GIVEN
        given(mockFindDocumentRequestConverter.convert(mockDocumentName)).willReturn(mockDocumentStoreFindRequest);
        given(mockDocumentStore.retrieveDocument(mockDocumentStoreFindRequest)).willReturn(Optional.of(mockMarkdownDocument));
        
        // WHEN
        MarkdownDocument request = underTest.getMarkdownDocument(mockDocumentName);
        
        // THEN
        assertThat(request, Matchers.is(mockMarkdownDocument));
    }
    
    @Test
    public void testCreateMarkdownDocumentShouldConvertForm() {
        // GIVEN
        given(mockCreateDocumentRequestConveter.convert(mockMarkdownDocumentForm)).willReturn(mockDocumentStoreCreateRequest);
        willDoNothing().given(mockDocumentStore).storeDocument(mockDocumentStoreCreateRequest);
        
        // WHEN
        underTest.createMarkdownDocument(mockMarkdownDocumentForm);
        
        // THEN
        verify(mockCreateDocumentRequestConveter).convert(mockMarkdownDocumentForm);
    }
    
    @Test
    public void testCreateMarkdownDocumentShouldCreateNewDocument() {
        // GIVEN
        given(mockCreateDocumentRequestConveter.convert(mockMarkdownDocumentForm)).willReturn(mockDocumentStoreCreateRequest);
        willDoNothing().given(mockDocumentStore).storeDocument(mockDocumentStoreCreateRequest);
        
        // WHEN
        underTest.createMarkdownDocument(mockMarkdownDocumentForm);
        
        // THEN
        verify(mockDocumentStore).storeDocument(mockDocumentStoreCreateRequest);
    }
    
    @Test
    public void testCreateMarkdownDocumentShouldReturnOkStatusWhenDocumentCreationSucceeds() {
        // GIVEN
        given(mockCreateDocumentRequestConveter.convert(mockMarkdownDocumentForm)).willReturn(mockDocumentStoreCreateRequest);
        willDoNothing().given(mockDocumentStore).storeDocument(mockDocumentStoreCreateRequest);
        
        // WHEN
        underTest.createMarkdownDocument(mockMarkdownDocumentForm);
        
        // THEN should pass
    }
    
    @Test(expected = CannotCreateDocumentException.class)
    public void testCreateMarkdowDocumentShouldThrowWhenDocumentCreationFails() {
        // GIVEN
        given(mockCreateDocumentRequestConveter.convert(mockMarkdownDocumentForm)).willReturn(mockDocumentStoreCreateRequest);
        willThrow(new CannotCreateDocumentException(new IOException())).given(mockDocumentStore).storeDocument(mockDocumentStoreCreateRequest);
        
        // WHEN
        underTest.createMarkdownDocument(mockMarkdownDocumentForm);
        
        // THEN
    }

    private MarkdownDocumentForm createMockMarkdownDocumentForm() {
        return new MarkdownDocumentForm()
            .withAuthor(MOCK_AUTHOR)
            .withContent(MOCK_CONTENT)
            .withName(MOCK_DOCUMENT_NAME);
    }
    
	private MarkdownDocument createMockMarkdownDocument() {
		return new MarkdownDocument.Builder().build();
	}

    private DocumentStoreFindRequest createMockDocumentStoreFindRequest() {
        return new DocumentStoreFindRequest.Builder()
            .withDocumentName(MOCK_DOCUMENT_NAME)
            .build();
    }
    
    private DocumentStoreCreateRequest createMockDocumentStoreCreateRequest() {
        return new DocumentStoreCreateRequest.Builder()
            .withAuthor(MOCK_AUTHOR)
            .withContent(MOCK_CONTENT)
            .withDocumentName(MOCK_DOCUMENT_NAME)
            .build();
    }
}
