package io.github.kicsikrumpli.dao;

import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

/**
 * Replaces ~ in path with home directory.
 * @author daniel
 *
 */
@Component
public class HomeDirectoryResolver {
    private static final String USER_HOME = "user.home";
	private static final String EMPTY = "";
	private static final String BEGINS_WITH_TILDE = "^~";

	/**
     * Resolves home directory from user.home system property;
     * @param path with home to be resolved
     * @return path with resolved home
     */
    public String resolveHome(String path) {
        String home;
        try {
            home = getUserHome();
        } catch(IllegalArgumentException e) {
            home = EMPTY;
        }
        return path.replaceFirst(BEGINS_WITH_TILDE, home);
    }

    @VisibleForTesting
	String getUserHome() {
		return System.getProperty(USER_HOME);
	}
}
