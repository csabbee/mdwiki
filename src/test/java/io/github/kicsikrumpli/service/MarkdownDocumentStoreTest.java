package io.github.kicsikrumpli.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.TextFileDao;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.converter.TextDocumentResponseConverter;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;
import io.github.kicsikrumpli.service.strategy.MarkdownPathResolverStrategy;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;

/**
 * Test class for {@link MarkdownDocumentStore}.
 * @author daniel
 *
 */
public class MarkdownDocumentStoreTest {
    private static final Path MOCK_PATH = Paths.get("mock", "path");

    @InjectMocks
    private MarkdownDocumentStore underTest;
    
    @Mock
    private TextFileDao mockFileDao;
    @Mock
    private MarkdownPathResolverStrategy mockPathResolver;
    @Mock
    private TextDocumentResponseConverter mockTextDocumentConverter;

    
    private DocumentStoreFindRequest mockDocumentRequest = new DocumentStoreFindRequest.Builder()
        .withDocumentName("mock-document-name")
        .build();
    private Optional<TextDocument> mockTextFile;
    private Optional<MarkdownDocument> mockMarkdownDocument;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockPathResolver.resolvePath(mockDocumentRequest)).willReturn(MOCK_PATH);
        mockTextFile = Optional.of(new TextDocument.Builder().build());
        mockMarkdownDocument = Optional.of(new MarkdownDocument.Builder().build());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testStoreShouldThrowUnsupportedOperationException() {
        // GIVEN in setUp
        
        // WHEN
        underTest.storeDocument(null);
        
        // THEN should throw
    }
    
    @Test
    public void testRetrieveDocumentShouldResolvePath() {
        // GIVEN in setUp
        
        // WHEN
        underTest.retrieveDocument(mockDocumentRequest);
        
        // THEN
        verify(mockPathResolver).resolvePath(mockDocumentRequest);
    }
    
    @Test
    public void testRetrieveDocumentShouldReadTextFile() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(mockTextFile);
        
        // WHEN
        underTest.retrieveDocument(mockDocumentRequest);
        
        // THEN
        verify(mockFileDao).readFile(MOCK_PATH);
    }
    
    @Test
    public void testRetrieveDocumentShouldConvertTextFile() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(mockTextFile);
        given(mockTextDocumentConverter.convert(mockTextFile)).willReturn(mockMarkdownDocument);
        
        // WHEN
        underTest.retrieveDocument(mockDocumentRequest);
        
        // THEN
        verify(mockTextDocumentConverter).convert(mockTextFile);
    }
    
    @Test
    public void testRetrieveDocumentShouldReturnAbsentWhenTextFileDoesNotExist() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(Optional.<TextDocument>absent());
        given(mockTextDocumentConverter.convert(Optional.<TextDocument>absent())).willReturn(Optional.<MarkdownDocument>absent());
        
        // WHEN
        Optional<MarkdownDocument> markdownDocument = underTest.retrieveDocument(mockDocumentRequest);
        
        // THEN
        assertThat(markdownDocument.isPresent(), is(false));
    }
}
