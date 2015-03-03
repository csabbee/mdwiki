package io.github.kicsikrumpli.dao;

import org.springframework.stereotype.Component;

/**
 * Replaces ~ in path with home directory.
 * @author daniel
 *
 */
@Component
public class HomeDirectoryResolver {
    
    /**
     * Resolves home directory from user.home system property;
     * @param path
     * @return
     */
    public String resolveHome(String path) {
        String home;
        try {
            home = System.getProperty("user.home");
        } catch(IllegalArgumentException e) {
            home = "";
        }
        return path.replaceFirst("~", home );
    }
}
