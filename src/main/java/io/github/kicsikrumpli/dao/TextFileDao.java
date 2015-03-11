package io.github.kicsikrumpli.dao;

import io.github.kicsikrumpli.dao.domain.TextDocument;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(TextFileDao.class);
	
    @Value("${DEFAULT_AUTHOR}")
    private String defaultAuthor;
    @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
    private Charset defaultCharset;
    @Autowired
    private ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;

    /**
     * Reads contents of a file into a string.
     * Throws IOException wrapped in runtime exception.
     * @param fileName name of file to read relative to default root set by configuration.
     * @return optional of file contents or absent if file does not exist
     */
    public Optional<TextDocument> readFile(Path path) {
        Optional<List<String>> lines = Optional.fromNullable(readAllLines(path));
        return createTextDocument(lines, path.getFileName().toString());
    }
    
	public void createFile(Path path) {
		logger.info("create file with path: {}", path);
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

    private Optional<TextDocument> createTextDocument(Optional<List<String>> lines, String fileName) {
        Optional<TextDocument> textDocument;
        if (lines.isPresent()) {
            textDocument = Optional.of(doCreate(lines.get(), fileName));
        } else {
            textDocument = Optional.absent();
        }
        return textDocument;
    }

    private TextDocument doCreate(List<String> lines, String fileName) {
        return textDocumentBuilderFactory.getObject()
                .withLines(lines)
                .withAuthor(defaultAuthor)
                .withEncoding(defaultCharset)
                .withName(fileName)
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
