package io.github.kicsikrumpli.controller.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.controller.domain.MarkdownDocumentForm;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

/**
 * Test class for {@link DocumentStoreCreateRequestConverter}.
 * @author daniel
 *
 */
public class DocumentStoreCreateRequestConverterTest {
    private static final String MOCK_NAME = "mock-name";
    private static final String MOCK_CONTENT = "mock-content";
    private static final String MOCK_AUTHOR = "mock-author";
    @InjectMocks
    private DocumentStoreCreateRequestConverter underTest;
    @Mock
    private ObjectFactory<DocumentStoreCreateRequest.Builder> mockRequestBuilderFactory;
    private MarkdownDocumentForm mockMarkdownDocumentForm;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockRequestBuilderFactory.getObject()).willReturn(new DocumentStoreCreateRequest.Builder());
        mockMarkdownDocumentForm = createMockMarkdownDocumentForm();
    }
    
    @Test
    public void testConvertShouldBuildDocumentStoreCreateRequest() {
        // GIVEN in setUp
        
        // WHEN
        DocumentStoreCreateRequest convertedRequest = underTest.convert(mockMarkdownDocumentForm);
        
        // THEN
        assertThat(convertedRequest, is(DocumentStoreCreateRequest.class));
    }
    
    @Test
    public void testConvertShouldCopyProperties() {
        // GIVEN in setUp
        
        // WHEN
        DocumentStoreCreateRequest convertedRequest = underTest.convert(mockMarkdownDocumentForm);
        
        // THEN
        assertThat(convertedRequest.getAuthor(), is(MOCK_AUTHOR));
        assertThat(convertedRequest.getContent(), is(MOCK_CONTENT));
        assertThat(convertedRequest.getDocumentName(), is(MOCK_NAME));
    }

    private MarkdownDocumentForm createMockMarkdownDocumentForm() {
        return new MarkdownDocumentForm()
            .withAuthor(MOCK_AUTHOR)
            .withContent(MOCK_CONTENT)
            .withName(MOCK_NAME);
    }
}
