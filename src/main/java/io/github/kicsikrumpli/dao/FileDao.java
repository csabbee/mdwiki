package io.github.kicsikrumpli.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

@Component
public class FileDao {
    @Value("${WIKI_ROOT}")
    private String defaultRoot;

    public Optional<String> readFile(String fileName) {
        String result = null;
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(defaultRoot, fileName));
            result = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.fromNullable(result);
    }

}
