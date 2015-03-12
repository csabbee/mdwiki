package io.github.kicsikrumpli.service.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

/**
 * Test class for {@link TextDocumentRequestConverter}.
 * @author daniel
 *
 */
public class TextDocumentRequestConverterTest {
    private static final String MOCK_RESOLVED_ROOT = "mock-resolved-root";
    private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
    private static final String MOCK_CONTENT = "mock-content";
    private static final String MOCK_AUTHOR = "mock-author";
    private static final Path RESOLVED_PATH = Paths.get(MOCK_RESOLVED_ROOT, MOCK_DOCUMENT_NAME);
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @InjectMocks
    private TextDocumentRequestConverter underTest;
    @Mock
    private MarkdownPathResolverStrategy mockPathResolver;
    @Mock
    private ObjectFactory<TextDocument.Builder> mockTextDocumentBuilderFactory;
    private DocumentStoreCreateRequest mockCreateDocumentRequest = createMockCreateDocumentRequest();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest.setDefaultCharset(DEFAULT_CHARSET);
        given(mockTextDocumentBuilderFactory.getObject()).willReturn(new TextDocument.Builder());
        given(mockPathResolver.resolvePath(Mockito.anyString())).willReturn(RESOLVED_PATH);
    }
    
    @Test
    public void testConvertShouldAddResolvedPath() {
        // GIVEN in setUp
        
        // WHEN
        TextDocument convertedDocument = underTest.convert(mockCreateDocumentRequest);
        
        // THEN
        assertThat(convertedDocument.getPath(), is(RESOLVED_PATH));
    }
    
    @Test
    public void testConvertShouldAddAuthor() {
        // GIVEN in setUp
        
        // WHEN
        TextDocument convertedDocument = underTest.convert(mockCreateDocumentRequest);
        
        // THEN
        assertThat(convertedDocument.getAuthor(), is(MOCK_AUTHOR));
    }
    
    @Test
    public void testConvertShouldAddContent() {
        // GIVEN in setUp
        
        // WHEN
        TextDocument convertedDocument = underTest.convert(mockCreateDocumentRequest);
        
        // THEN
        assertThat(convertedDocument.getLines(), is(Collections.singletonList(MOCK_CONTENT)));
    }
    
    @Test
    public void testConvertShouldAddDefaultEncoding() {
        // GIVEN in setUp
        
        // WHEN
        TextDocument convertedDocument = underTest.convert(mockCreateDocumentRequest);
        
        // THEN
        assertThat(convertedDocument.getEncoding(), is(DEFAULT_CHARSET));
    }

    private DocumentStoreCreateRequest createMockCreateDocumentRequest() {
        return new DocumentStoreCreateRequest.Builder()
            .withAuthor(MOCK_AUTHOR)
            .withContent(MOCK_CONTENT)
            .withDocumentName(MOCK_DOCUMENT_NAME)
            .build();
    }
}
