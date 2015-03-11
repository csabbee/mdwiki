package io.github.kicsikrumpli.dao;

import io.github.kicsikrumpli.dao.domain.TextDocument;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;

/**
 * Handles file access.
 * @author daniel
 *
 */
@Component
public class TextFileDao {
    @Value("${DEFAULT_AUTHOR}")
    private String defaultAuthor;
    @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
    private Charset defaultCharset;
    @Autowired
    private ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;

    /**
     * Reads contents of a file into a string.
     * Throws IOException wrapped in runtime exception.
     * @param path of file to read relative to default root set by configuration.
     * @return optional of file contents or absent if file does not exist
     */
    public Optional<TextDocument> readFile(Path path) {
        Optional<List<String>> lines = Optional.fromNullable(readAllLines(path));
        return buildTextDocument(lines, path);
    }
    
    /**
     * Writes text document to file.
     * @param document to write
     * @throws IOException when write fails
     */
	public void createFile(TextDocument document) throws IOException {
		Files.write(document.getPath(), document.getLines(), Optional.fromNullable(document.getEncoding()).or(defaultCharset));
	}

    @VisibleForTesting
    List<String> readAllLines(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path, defaultCharset);
        } catch (IOException e) {
            lines = null;
        }
        return lines;
    }

    private Optional<TextDocument> buildTextDocument(Optional<List<String>> lines, Path path) {
        Optional<TextDocument> textDocument;
        if (lines.isPresent()) {
            textDocument = Optional.of(doBuild(lines.get(), path));
        } else {
            textDocument = Optional.absent();
        }
        return textDocument;
    }

    private TextDocument doBuild(List<String> lines, Path path) {
        return textDocumentBuilderFactory.getObject()
                .withLines(lines)
                .withAuthor(defaultAuthor)
                .withEncoding(defaultCharset)
                .withPath(path)
                .build();
    }
    
	void setDefaultCharset(Charset defaultCharset) {
		this.defaultCharset = defaultCharset;
	}

    void setDefaultAuthor(String defaultAuthor) {
        this.defaultAuthor = defaultAuthor;
    }

    void setTextDocumentBuilderFactory(ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory) {
        this.textDocumentBuilderFactory = textDocumentBuilderFactory;
    }
}
