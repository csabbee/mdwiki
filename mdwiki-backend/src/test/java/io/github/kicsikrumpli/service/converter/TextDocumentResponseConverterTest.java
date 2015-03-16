package io.github.kicsikrumpli.service.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

import com.google.common.base.Optional;

/**
 * Test class for {@link TextDocumentResponseConverter}.
 * @author daniel
 *
 */
public class TextDocumentResponseConverterTest {
    private static final String MOCK_LINE_SEPARATOR = "@";
    private static final String MOCK_ROOT = "mock-root";
    private static final String MOCK_FILE_NAME = "mock-file-name";
    private static final String MOCK_AUTHOR = "mock-author";
    private static final String FIRST_LINE = "first-line";
    private static final String SECOND_LINE = "second-line";
    private static final String THIRD_LINE = "more";
    private static final String FOURTH_LINE = "lines";
    private static final Path MOCK_PATH = Paths.get(MOCK_ROOT, MOCK_FILE_NAME);
    
    @InjectMocks
    private TextDocumentResponseConverter underTest;
    @Mock
    private ObjectFactory<MarkdownDocument.Builder> mockMarkdownDocumentBuilderFactory;
    private TextDocument mockTextDocument = createMockTextDocument();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockMarkdownDocumentBuilderFactory.getObject()).willReturn(new MarkdownDocument.Builder());
        underTest.setDefaultLineSeparator(MOCK_LINE_SEPARATOR);
    }
    
    @Test
    public void testConvertShouldCreateAbsentWhenTextDocumentIsNotPresent() {
        // GIVEN in setUp
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(Optional.<TextDocument>absent());
        
        // THEN
        assertThat(convertedMarkdownDocument.isPresent(), is(false));
    }

    @Test
    public void testConvertShouldAddConvertedMarkDownDocumentWhenTextDocumentIsPresent() {
        // GIVEN in setUp
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(Optional.of(mockTextDocument));
        
        // THEN
        assertThat(convertedMarkdownDocument.isPresent(), is(true));
    }
    
    @Test
    public void testConvertShouldJoinTextDocumentLinesWithDefaultLineSeparator() {
        // GIVEN in setUp
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(Optional.of(mockTextDocument));
        
        // THEN
        assertThat(convertedMarkdownDocument.get().getContent(), is("first-line@second-line@more@lines"));
    }
    
    @Test
    public void testConvertShouldAddFileNameFromPath() {
        // GIVEN in setUp
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(Optional.of(mockTextDocument));
        
        // THEN
        assertThat(convertedMarkdownDocument.get().getName(), is(MOCK_FILE_NAME));
    }

    @Test
    public void testConvertShouldAddAuthor() {
        // GIVEN in setUp
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(Optional.of(mockTextDocument));
        
        // THEN
        assertThat(convertedMarkdownDocument.get().getAuthor(), is(MOCK_AUTHOR));
    }

    private TextDocument createMockTextDocument() {
        return new TextDocument.Builder()
            .withAuthor(MOCK_AUTHOR)
            .withLine(FIRST_LINE)
            .withLine(SECOND_LINE)
            .withLines(Arrays.asList(new String[]{THIRD_LINE, FOURTH_LINE}))
            .withPath(MOCK_PATH)
            .build();
    }
}
