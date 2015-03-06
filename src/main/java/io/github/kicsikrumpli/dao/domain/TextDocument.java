package io.github.kicsikrumpli.dao.domain;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
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
    
    TextDocument(Builder builder) {
        author = builder.author;
        lines = ImmutableList.copyOf(builder.lines);
        name = builder.name;
        encoding = builder.encoding.or(builder.defaultCharset);
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
        @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
        private Charset defaultCharset;

        private String author;
        private List<String> lines = new ArrayList<String>();
        private String name;
        Optional<Charset> encoding = Optional.absent();
        
        public Builder withEncoding(Charset encoding) {
            this.encoding = Optional.fromNullable(encoding);
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
