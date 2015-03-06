package io.github.kicsikrumpli.dao.domain;

import java.nio.charset.Charset;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Domain object for text based documents.
 * @author daniel
 *
 */
public final class TextDocument {
    private String author;
    private List<String> lines;
    private String name;
    private Charset encoding;
    
    TextDocument(TextDocumentBuilder builder) {
        author = builder.author;
        lines = ImmutableList.copyOf(builder.lines);
        name = builder.name;
        encoding = builder.encoding;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }

    public Charset getEncoding() {
        return encoding;
    }
}
