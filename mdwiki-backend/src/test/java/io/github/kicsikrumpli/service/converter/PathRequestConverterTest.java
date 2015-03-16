package io.github.kicsikrumpli.service.converter;

import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test class for {@link PathRequestConverter}.
 * @author daniel
 *
 */
public class PathRequestConverterTest {
    @InjectMocks
    private PathRequestConverter underTest;
    @Mock
    private MarkdownPathResolverStrategy mockPathResolver;
    private Path resolvedPath = Paths.get("resolved-root", "mock-file-name");
    private DocumentStoreFindRequest mockFindDocumentRequest = createMockFindDocumentRequest();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        BDDMockito.given(mockPathResolver.resolvePath(Mockito.anyString())).willReturn(resolvedPath);
    }
    
    @Test
    public void testConvertShouldResolvePathForDocumentName() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.convert(mockFindDocumentRequest);
        
        // THEN
        MatcherAssert.assertThat(path, Matchers.is(resolvedPath));
    }

    private DocumentStoreFindRequest createMockFindDocumentRequest() {
        return new DocumentStoreFindRequest.Builder()
            .withDocumentName("mock-file-name")
            .build();
    }
}
