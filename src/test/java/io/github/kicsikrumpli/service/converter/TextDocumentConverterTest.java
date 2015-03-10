package io.github.kicsikrumpli.service.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

import com.google.common.base.Optional;

/**
 * Test class for {@link TextDocumentConverter}.
 * @author daniel
 *
 */
public class TextDocumentConverterTest {
    @InjectMocks
    private TextDocumentConverter underTest;
    @Mock
    private ObjectFactory<MarkdownDocument.Builder> mockMarkdownDocumentBuilderFactory;
    @Mock
    private MarkdownDocument.Builder mockMarkdownDocumentBuilder;
    private Optional<TextDocument> mockTextDocument;
    private MarkdownDocument mockMarkdownDocument;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockMarkdownDocumentBuilderFactory.getObject()).willReturn(mockMarkdownDocumentBuilder);
        given(mockMarkdownDocumentBuilder.withAuthor(anyString())).willReturn(mockMarkdownDocumentBuilder);
        given(mockMarkdownDocumentBuilder.withContent(anyString())).willReturn(mockMarkdownDocumentBuilder);
        given(mockMarkdownDocumentBuilder.withName(anyString())).willReturn(mockMarkdownDocumentBuilder);
        underTest.setDefaultLineSeparator("@");
        mockTextDocument = Optional.of(createMockTextDocument());
        mockMarkdownDocument = new MarkdownDocument.Builder().build();
    }
    
    @Test
    public void testConvertShouldCreateAbsentWhenTextDocumentIsNotPresent() {
        // GIVEN
        mockTextDocument = Optional.absent();
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(mockTextDocument);
        
        // THEN
        assertThat(convertedMarkdownDocument.isPresent(), is(false));
    }

    @Test
    public void testConvertShouldAddConvertedMarkDownDocumentWhenTextDocumentIsPresent() {
        // GIVEN
        given(mockMarkdownDocumentBuilder.build()).willReturn(mockMarkdownDocument);
        
        // WHEN
        Optional<MarkdownDocument> convertedMarkdownDocument = underTest.convert(mockTextDocument);
        
        // THEN
        assertThat(convertedMarkdownDocument.isPresent(), is(true));
    }
    
    @Test
    public void testConvertShouldJoinTextDocumentLinesWithDefaultLineSeparator() {
        // GIVEN
        given(mockMarkdownDocumentBuilder.build()).willReturn(mockMarkdownDocument);
        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);
        
        // WHEN
        underTest.convert(mockTextDocument);
        
        // THEN
        verify(mockMarkdownDocumentBuilder).withContent(contentCaptor.capture());
        assertThat(contentCaptor.getValue(), is("first-line@second-line@more@lines"));
    }
    
    private TextDocument createMockTextDocument() {
        return new TextDocument.Builder()
            .withAuthor("mock-author")
            .withEncoding(Charset.forName("UTF-8"))
            .withLine("first-line")
            .withLine("second-line")
            .withLines(Arrays.asList(new String[]{"more","lines"}))
            .withName("mock-name")
            .build();
    }
}
