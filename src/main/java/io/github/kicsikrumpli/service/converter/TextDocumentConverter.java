package io.github.kicsikrumpli.service.converter;

import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

/**
 * Converts from text file dao domain object to markdown document store domain object.
 * @author daniel
 *
 */
@Component
public class TextDocumentConverter {
    @Value("${LINE_SEPARATOR:\n}")
    private String defaultLineSeparator;
    @Autowired
    private ObjectFactory<MarkdownDocument.Builder> markdownDocumentBuilderFactory;

    public Optional<MarkdownDocument> convert(Optional<TextDocument> textDocument) {
        Optional<MarkdownDocument> markdownDocument;
        if (textDocument.isPresent()) {
            markdownDocument = Optional.of(doConvert(textDocument.get())); 
        } else {
            markdownDocument = Optional.absent();
        }
        return markdownDocument;
    }

    private MarkdownDocument doConvert(TextDocument documentToConvert) {
        return markdownDocumentBuilderFactory.getObject()
                .withAuthor(documentToConvert.getAuthor())
                .withName(documentToConvert.getName())
                .withContent(convertContent(documentToConvert.getLines()))
                .build();
    }

    private String convertContent(List<String> lines) {
        return Joiner.on(defaultLineSeparator).join(lines);
    }
    
    public void setDefaultLineSeparator(String defaultLineSeparator) {
        this.defaultLineSeparator = defaultLineSeparator;
    }

    public void setMarkdownDocumentBuilderFactory(ObjectFactory<MarkdownDocument.Builder> markdownDocumentBuilderFactory) {
        this.markdownDocumentBuilderFactory = markdownDocumentBuilderFactory;
    }
}
