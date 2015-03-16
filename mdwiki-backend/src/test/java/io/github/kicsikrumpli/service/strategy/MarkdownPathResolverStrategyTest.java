package io.github.kicsikrumpli.service.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.service.strategy.builder.PathBuilder;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    private MarkdownPathResolverStrategy underTest;
    @Mock
    private ObjectFactory<PathBuilder> mockPathBuilderFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest.setDefaultExtension(MOCK_EXTENSION);
        underTest.setDefaultRoot(MOCK_ROOT);
        given(mockPathBuilderFactory.getObject()).willReturn(new PathBuilder());
    }
    
    @Test
    public void testResolveShouldUseDefaultRoot() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.resolvePath(MOCK_DOCUMENT_NAME);
        
        // THEN
        assertThat(path.getName(0).toString(), is(MOCK_ROOT));
    }

    @Test
    public void testResolveShouldAppendExtensionToFileName() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.resolvePath(MOCK_DOCUMENT_NAME);
        
        // THEN
        assertThat(path.getFileName().toString(), is(MOCK_DOCUMENT_NAME_WITH_EXTENSION));
    }
}
