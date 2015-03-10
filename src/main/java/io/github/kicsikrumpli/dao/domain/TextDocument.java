package io.github.kicsikrumpli.dao.domain;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

/**
 * Domain object for text based documents.
 * Create builder as @Autowired private ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;
 * @author daniel
 *
 */
public final class TextDocument {
    private String author;
    private List<String> lines;
    private String name;
    private Charset encoding;
    
    TextDocument(Builder builder) {
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
    
    @Component
    @Scope("prototype")
    public static class Builder {
        private String author;
        private List<String> lines = new ArrayList<String>();
        private String name;
        Charset encoding;
        
        public Builder withEncoding(Charset encoding) {
            this.encoding = encoding;
            return this;
        }
        
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder withLine(String line) {
            lines.add(line);
            return this;
        }
        
        public Builder withLines(List<String> lines) {
            this.lines.addAll(lines);
            return this;
        }
        
        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }
        
        public TextDocument build() {
            return new TextDocument(this);
        }
    }
}
