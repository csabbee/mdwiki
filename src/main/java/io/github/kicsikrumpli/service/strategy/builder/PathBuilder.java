package io.github.kicsikrumpli.service.strategy.builder;

import io.github.kicsikrumpli.service.strategy.resolver.HomeDirectoryResolver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;

@Component
@Scope("prototype")
public class PathBuilder {
	@Autowired
	private HomeDirectoryResolver homeDirResolver;
	private List<String> pathElements = new ArrayList<String>();
	
	/**
	 * Add directory or file name.
	 * @param pathElement directory or file name
	 * @return builder instance
	 */
	public PathBuilder withPathElement(final String pathElement) {
		String path = pathElement;
		if (pathElements.isEmpty()) {
			path = homeDirResolver.resolveHome(pathElement);
		}
		pathElements.add(path);
		return this;
	}
	
	/**
	 * Create path from parameters.
	 * @return Path
	 */
	public Path build() {
		return Paths.get(head().or(""), tail());
	}

    private Optional<String> head() {
        return FluentIterable.from(pathElements).first();
    }

    private String[] tail() {
        return FluentIterable.from(pathElements).skip(1).toArray(String.class);
    }

}
