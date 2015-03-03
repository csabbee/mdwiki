package io.github.kicsikrumpli.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

/**
 * Handles file access.
 * @author daniel
 *
 */
@Component
public class FileDao {
    @Value("#{homeDirectoryResolver.resolveHome('${WIKI_ROOT}')}")
    private String defaultRoot;
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
    public Optional<String> readFile(String fileName) {
        String result;
        try {
            result = readFileContents(fileName); 
        } catch (IOException e) {
            result = null;
        }
        return Optional.fromNullable(result);
    }

    private String readFileContents(String fileName) throws IOException {
        Path path = Paths.get(defaultRoot, fileName);
        List<String> lines = Files.readAllLines(path, defaultCharset);
        return Joiner.on(defaultLineSeparator).join(lines);
    }
}
