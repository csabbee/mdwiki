package io.github.kicsikrumpli.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;

/**
 * Handles file access.
 * @author daniel
 *
 */
@Component
public class FileDao {    
    @Value("#{T(java.nio.charset.Charset).forName('${DEFAULT_ENCODING}')}")
    private Charset defaultCharset;
    @Value("${LINE_SEPARATOR:\n}")
    private String defaultLineSeparator;

    /**
     * Reads contents of a file into a string.
     * Throws IOException wrapped in runtime exception.
     * @param fileName name of file to read relative to default root set by configuration.
     * @return optional of file cotents or absent if file does not exist
     */
    public Optional<String> readFile(Path path) {
        String result;
        try {
            result = Joiner.on(defaultLineSeparator).join(readAllLines(path)); 
        } catch (IOException e) {
            result = null;
        }
        return Optional.fromNullable(result);
    }

    @VisibleForTesting
    List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path, defaultCharset);
    }

	void setDefaultCharset(Charset defaultCharset) {
		this.defaultCharset = defaultCharset;
	}

	void setDefaultLineSeparator(String defaultLineSeparator) {
		this.defaultLineSeparator = defaultLineSeparator;
	}
}
