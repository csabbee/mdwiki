package io.github.kicsikrumpli.dao.domain;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TextDocumentBuilder {
    @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
    private Charset defaultCharset;

    String author;
    List<String> lines = new ArrayList<String>();
    String name;
    Charset encoding = defaultCharset;
    
    public TextDocumentBuilder withEncoding(Charset encoding) {
        this.encoding = encoding;
        return this;
    }
    
    public TextDocumentBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public TextDocumentBuilder withLine(String line) {
        lines.add(line);
        return this;
    }
    
    public TextDocumentBuilder withLines(List<String> lines) {
        this.lines.addAll(lines);
        return this;
    }
    
    public TextDocumentBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }
    
    public TextDocument build() {
        return new TextDocument(this);
    }
}
