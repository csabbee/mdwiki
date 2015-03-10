package io.github.kicsikrumpli.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.domain.TextDocument;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    private static final String MOCK_PATH = "mock-path";
    private static final String MOCK_FILE = "mock-file";
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String MOCK_DEFAULT_AUTHOR = "mock-default-author";

    @Spy
    @InjectMocks
    private TextFileDao underTest;
    @Mock
    private ObjectFactory<TextDocument.Builder> mockTextDocumentBuilderFactory;
    @Mock
    private TextDocument.Builder mockTextDocumentBuilder;

    private Path mockPath = createMockPath();
    private TextDocument mockTextDocument = createMockTextDocument();
    
    @Captor
    private ArgumentCaptor<List<String>> fileContentCaptor;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockBuilderBehavior();
        underTest.setDefaultAuthor(MOCK_DEFAULT_AUTHOR);
        underTest.setDefaultCharset(DEFAULT_CHARSET);
    }

    @Test
    public void testReadFileShouldCreateTextDocumentWhenFileRead() {
        // GIVEN
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));
        given(mockTextDocumentBuilder.build()).willReturn(mockTextDocument);
        
        // WHEN
        Optional<TextDocument> textDocument = underTest.readFile(mockPath);
        
        // THEN
        assertThat(textDocument.isPresent(), is(true));
    }
    
    @Test
    public void testReadFileShouldCreateTextDocumentWithLinesReadFromFile() {
        // GIVEN
        willReturn(Arrays.asList("first line", "second line", "third line")).given(underTest).readAllLines(Mockito.any(Path.class));
        given(mockTextDocumentBuilder.build()).willReturn(mockTextDocument);

        // WHEN
        underTest.readFile(mockPath);
        
        // THEN
        verify(mockTextDocumentBuilder).withLines(fileContentCaptor.capture());
        assertThat(fileContentCaptor.getValue(), hasItems("first line", "second line", "third line"));
    }
    @Test
    public void testReadFileShouldCreateTextDocumentWithDefaultAuthor() {
        // GIVEN
        ArgumentCaptor<String> authorCaptor = ArgumentCaptor.forClass(String.class);
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));
        given(mockTextDocumentBuilder.build()).willReturn(mockTextDocument);

        // WHEN
        underTest.readFile(mockPath);
        
        // THEN
        verify(mockTextDocumentBuilder).withAuthor(authorCaptor.capture());
        assertThat(authorCaptor.getValue(), is(MOCK_DEFAULT_AUTHOR));
    }
    @Test
    public void testReadFileShouldCreateTextDocumentWithDefaultCharset() {
        // GIVEN
        ArgumentCaptor<Charset> charsetCaptor = ArgumentCaptor.forClass(Charset.class);
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));
        given(mockTextDocumentBuilder.build()).willReturn(mockTextDocument);

        // WHEN
        underTest.readFile(mockPath);
        
        // THEN DEFAULT_CHARSET
        verify(mockTextDocumentBuilder).withEncoding(charsetCaptor.capture());
        assertThat(charsetCaptor.getValue(), is(DEFAULT_CHARSET));
    }
    @Test
    public void testReadFileShouldCreateTextDocumentWithFilename() {
        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);
        willReturn(Collections.emptyList()).given(underTest).readAllLines(Mockito.any(Path.class));
        given(mockTextDocumentBuilder.build()).willReturn(mockTextDocument);

        // WHEN
        underTest.readFile(mockPath);
        
        // THEN
        verify(mockTextDocumentBuilder).withName(fileNameCaptor.capture());
        assertThat(fileNameCaptor.getValue(), is(MOCK_FILE));
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

    private Path createMockPath() {
        return Paths.get(MOCK_PATH, MOCK_FILE);
    }

    private TextDocument createMockTextDocument() {
        return new TextDocument.Builder().build();
    }

    private void mockBuilderBehavior() {
        given(mockTextDocumentBuilderFactory.getObject()).willReturn(mockTextDocumentBuilder);
        given(mockTextDocumentBuilder.withAuthor(Mockito.anyString())).willReturn(mockTextDocumentBuilder);
        given(mockTextDocumentBuilder.withEncoding(Mockito.any(Charset.class))).willReturn(mockTextDocumentBuilder);
        given(mockTextDocumentBuilder.withLine(Mockito.anyString())).willReturn(mockTextDocumentBuilder);
        given(mockTextDocumentBuilder.withLines(Mockito.anyListOf(String.class))).willReturn(mockTextDocumentBuilder);
        given(mockTextDocumentBuilder.withName(Mockito.anyString())).willReturn(mockTextDocumentBuilder);
    }
}
