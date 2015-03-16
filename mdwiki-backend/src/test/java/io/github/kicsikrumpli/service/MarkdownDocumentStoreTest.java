package io.github.kicsikrumpli.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.FileDaoWriteException;
import io.github.kicsikrumpli.dao.TextFileDao;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.converter.PathRequestConverter;
import io.github.kicsikrumpli.service.converter.TextDocumentRequestConverter;
import io.github.kicsikrumpli.service.converter.TextDocumentResponseConverter;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private TextDocumentResponseConverter mockTextDocumentResponseConverter;
    @Mock
    private PathRequestConverter mockPathRequestConverter; 
    @Mock
    private TextDocumentRequestConverter mockTextDocumentRequestConverter;
    
    private DocumentStoreFindRequest mockFindDocumentRequest = createMockFindDocumentRequest();
    private TextDocument mockTextFile = createMockTextDocument();
    private Optional<MarkdownDocument> mockMarkdownDocument = createMockMarkdownDocument();
    private DocumentStoreCreateRequest mockCreateDocumentRequest = createMockCreateDocumentRequest();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockPathRequestConverter.convert(mockFindDocumentRequest)).willReturn(MOCK_PATH);
    }

    @Test
    public void testRetrieveDocumentShouldConvertRequest() {
        // GIVEN in setUp
        
        // WHEN
        underTest.retrieveDocument(mockFindDocumentRequest);
        
        // THEN
        verify(mockPathRequestConverter).convert(mockFindDocumentRequest);
    }
    
    @Test
    public void testRetrieveDocumentShouldReadTextFile() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(Optional.of(mockTextFile));
        
        // WHEN
        underTest.retrieveDocument(mockFindDocumentRequest);
        
        // THEN
        verify(mockFileDao).readFile(MOCK_PATH);
    }
    
    @Test
    public void testRetrieveDocumentShouldConvertTextFile() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(Optional.of(mockTextFile));
        given(mockTextDocumentResponseConverter.convert(Optional.of(mockTextFile))).willReturn(mockMarkdownDocument);
        
        // WHEN
        underTest.retrieveDocument(mockFindDocumentRequest);
        
        // THEN
        verify(mockTextDocumentResponseConverter).convert(Optional.of(mockTextFile));
    }
    
    @Test
    public void testRetrieveDocumentShouldReturnAbsentWhenTextFileDoesNotExist() {
        // GIVEN
        given(mockFileDao.readFile(any(Path.class))).willReturn(Optional.<TextDocument>absent());
        given(mockTextDocumentResponseConverter.convert(Optional.<TextDocument>absent())).willReturn(Optional.<MarkdownDocument>absent());
        
        // WHEN
        Optional<MarkdownDocument> markdownDocument = underTest.retrieveDocument(mockFindDocumentRequest);
        
        // THEN
        assertThat(markdownDocument.isPresent(), is(false));
    }
    
    @Test(expected = CannotCreateDocumentException.class)
    public void testStoreShouldThrowWhenFileCreationFails() {
        // GIVEN
        willThrow(new FileDaoWriteException(new IOException())).given(mockFileDao).createFile(Mockito.any(TextDocument.class));
        
        // WHEN
        underTest.storeDocument(mockCreateDocumentRequest);
        
        // THEN will throw
    }
    
    @Test
    public void testStoreShouldCreateNewFile() {
        // GIVEN
        given(mockTextDocumentRequestConverter.convert(mockCreateDocumentRequest)).willReturn(mockTextFile);
        willDoNothing().given(mockFileDao).createFile(mockTextFile);
        
        // WHEN
        underTest.storeDocument(mockCreateDocumentRequest);
        
        // THEN
        verify(mockFileDao).createFile(mockTextFile);
    }
    
    private DocumentStoreFindRequest createMockFindDocumentRequest() {
        return new DocumentStoreFindRequest.Builder().build();
    }
    
    private TextDocument createMockTextDocument() {
        return new TextDocument.Builder().build();
    }

    private Optional<MarkdownDocument> createMockMarkdownDocument() {
        return Optional.of(new MarkdownDocument.Builder().build());
    }
    
    private DocumentStoreCreateRequest createMockCreateDocumentRequest() {
        return new DocumentStoreCreateRequest.Builder().build();
    }
}
