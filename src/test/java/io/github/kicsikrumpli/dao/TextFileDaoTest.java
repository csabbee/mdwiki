package io.github.kicsikrumpli.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.domain.TextDocument;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.ObjectFactory;

import com.google.common.base.Optional;

/**
 * Test class for {@link TextFileDao}.
 * @author daniel
 *
 */
public class TextFileDaoTest {
    private static final String MOCK_FILE_CONTENT = "mock-file-content";
    private static final String MOCK_PATH = "mock-path";
    private static final String MOCK_FILE = "mock-file";
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String MOCK_DEFAULT_AUTHOR = "mock-default-author";

    @Spy
    @InjectMocks
    private TextFileDao underTest;
    @Mock
    private ObjectFactory<TextDocument.Builder> mockTextDocumentBuilderFactory;

    private Path mockPath = createMockPath();
    private TextDocument mockTextDocument;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockTextDocumentBuilderFactory.getObject()).willReturn(new TextDocument.Builder());
        underTest.setDefaultAuthor(MOCK_DEFAULT_AUTHOR);
        underTest.setDefaultCharset(DEFAULT_CHARSET);
        mockTextDocument = createMockTextDocument();
    }

    @Test
    public void testReadFileShouldCreateTextDocumentWhenFileRead() {
        // GIVEN
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));
        
        // WHEN
        Optional<TextDocument> textDocument = underTest.readFile(mockPath);
        
        // THEN
        assertThat(textDocument.isPresent(), is(true));
    }
    
    @Test
    public void testReadFileShouldCreateTextDocumentWithLinesReadFromFile() {
        // GIVEN
        willReturn(Arrays.asList("first line", "second line", "third line")).given(underTest).readAllLines(Mockito.any(Path.class));

        // WHEN
        Optional<TextDocument> document = underTest.readFile(mockPath);
        
        // THEN
        assertThat(document.get().getLines(), hasItems("first line", "second line", "third line"));
    }

    @Test
    public void testReadFileShouldCreateTextDocumentWithDefaultAuthor() {
        // GIVEN
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));

        // WHEN
        Optional<TextDocument> document = underTest.readFile(mockPath);
        
        // THEN
        assertThat(document.get().getAuthor(), is(MOCK_DEFAULT_AUTHOR));
    }

    @Test
    public void testReadFileShouldCreateTextDocumentWithDefaultCharset() {
        // GIVEN
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));

        // WHEN
        Optional<TextDocument> document = underTest.readFile(mockPath);
        
        // THEN DEFAULT_CHARSET
        assertThat(document.get().getEncoding(), is(DEFAULT_CHARSET));
    }

    @Test
    public void testReadFileShouldCreateTextDocumentWithFilename() {
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));

        // WHEN
        Optional<TextDocument> document = underTest.readFile(mockPath);
        
        // THEN
        assertThat(document.get().getPath(), is(mockPath));
    }
    
    @Test
    public void testReadFileShouldReturnAbsentWhenFileCannotBeRead() {
        // GIVEN
        willReturn(null).given(underTest).readAllLines(Mockito.any(Path.class));
        
        // WHEN
        Optional<TextDocument> textDocument = underTest.readFile(mockPath);
        
        // THEN
        assertThat(textDocument.isPresent(), is(false));
    }
    
    @Test(expected = FileDaoWriteException.class)
    public void testCreateFileShouldThrowWhenFileCreationFails() throws IOException {
        // GIVEN
        willThrow(new IOException()).given(underTest).doWrite(Mockito.any(Path.class), Mockito.anyListOf(String.class), Mockito.any(Charset.class));
        
        // WHEN
        underTest.createFile(mockTextDocument);
        
        // THEN should throw
    }
    
    @Test
    public void testCreateFileShouldWriteFile() throws IOException {
        // GIVEN
        willDoNothing().given(underTest).doWrite(Mockito.any(Path.class), Mockito.anyListOf(String.class), Mockito.any(Charset.class));
        
        // WHEN
        underTest.createFile(mockTextDocument);
        
        // THEN
        verify(underTest).doWrite(mockPath, Collections.singletonList(MOCK_FILE_CONTENT), DEFAULT_CHARSET);
    }

    private TextDocument createMockTextDocument() {
        return new TextDocument.Builder()
            .withAuthor(MOCK_DEFAULT_AUTHOR)
            .withEncoding(DEFAULT_CHARSET)
            .withLine(MOCK_FILE_CONTENT)
            .withPath(mockPath)
            .build();
    }

    private Path createMockPath() {
        return Paths.get(MOCK_PATH, MOCK_FILE);
    }
}
