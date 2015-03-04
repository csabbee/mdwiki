package io.github.kicsikrumpli.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String[] EMPTY_ARRAY = new String[]{};
    private static final String EMPTY_STRING = "";
    
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
    public Optional<String> readFile(String... pathElements) {
        String result;
        try {
            result = readFileContents(pathElements); 
        } catch (IOException e) {
            result = null;
        }
        return Optional.fromNullable(result);
    }

    private String readFileContents(String... pathElements) throws IOException {
        Path path = getPath(pathElements);
        List<String> lines = readAllLines(path);
        return Joiner.on(defaultLineSeparator).join(lines);
    }

    @VisibleForTesting
    List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path, defaultCharset);
    }

    private Path getPath(String... pathElements) {
        return Paths.get(head(pathElements).or(EMPTY_STRING), tail(pathElements).toArray(EMPTY_ARRAY));
    }

    private <T> Optional<T> head(T[] array) {
        Optional<T> head;
        if (array.length > 0) {
            head = Optional.fromNullable(array[0]);
        } else {
            head = Optional.absent();
        }
        return head;
    }

    private <T> List<T> tail(T[] array) {
        List<T> tail;
        if (array.length > 1) {
            tail = Arrays.asList(array).subList(1, array.length);
        } else {
            tail = Collections.<T>emptyList();
        }
        return tail;
    }
}
