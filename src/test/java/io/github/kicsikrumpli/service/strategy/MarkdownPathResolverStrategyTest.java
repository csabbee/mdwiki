package io.github.kicsikrumpli.service.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;
import io.github.kicsikrumpli.service.strategy.builder.PathBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

/**
 * Test class for {@link MarkdownPathResolverStrategy}.
 * @author daniel
 *
 */
public class MarkdownPathResolverStrategyTest {
    private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
    private static final String MOCK_EXTENSION = "mock-extension";
    private static final String MOCK_DOCUMENT_NAME_WITH_EXTENSION = "mock-document-name.mock-extension";
    private static final String MOCK_ROOT = "mock-root";
    private static final Path MOCK_PATH = Paths.get("mock", "path");
    @InjectMocks
    private MarkdownPathResolverStrategy underTest;
    @Mock
    private ObjectFactory<PathBuilder> mockPathBuilderFactory;
    @Mock
    private PathBuilder mockPathBuilder;
    
    private DocumentStoreRequest mockDocumentRequest;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest.setDefaultExtension(MOCK_EXTENSION);
        underTest.setDefaultRoot(MOCK_ROOT);
        mockDocumentRequest = new DocumentStoreRequest.Builder()
            .withDocumentName(MOCK_DOCUMENT_NAME)
            .build();
        given(mockPathBuilderFactory.getObject()).willReturn(mockPathBuilder);
    }
    
    @Test
    public void testResolveShouldUseDefaultRoot() {
        // GIVEN
        ArgumentCaptor<String> pathElement = ArgumentCaptor.forClass(String.class);
        given(mockPathBuilder.withPathElement(Mockito.anyString())).willReturn(mockPathBuilder);
        given(mockPathBuilder.build()).willReturn(MOCK_PATH);
        
        // WHEN
        underTest.resolvePath(mockDocumentRequest);
        
        // THEN
        verify(mockPathBuilder, times(2)).withPathElement(pathElement.capture());
        assertThat(pathElement.getAllValues().get(0), is(MOCK_ROOT));
    }

    @Test
    public void testResolveShouldAppendExtensionToFileName() {
        // GIVEN
        ArgumentCaptor<String> pathElement = ArgumentCaptor.forClass(String.class);
        given(mockPathBuilder.withPathElement(Mockito.anyString())).willReturn(mockPathBuilder);
        given(mockPathBuilder.build()).willReturn(MOCK_PATH);
        
        // WHEN
        underTest.resolvePath(mockDocumentRequest);
        
        // THEN
        verify(mockPathBuilder, times(2)).withPathElement(pathElement.capture());
        assertThat(pathElement.getAllValues().get(1), is(MOCK_DOCUMENT_NAME_WITH_EXTENSION));
    }

}
