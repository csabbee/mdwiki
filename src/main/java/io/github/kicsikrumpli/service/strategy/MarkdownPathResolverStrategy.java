package io.github.kicsikrumpli.service.strategy;

import io.github.kicsikrumpli.service.domain.DocumentStoreRequest;
import io.github.kicsikrumpli.service.strategy.builder.PathBuilder;

import java.nio.file.Path;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

/**
 * Resolves paths from document request objects based on configuration.
 * @author daniel
 *
 */
@Component
public class MarkdownPathResolverStrategy implements PathResolverStrategy {
    @Value("#{homeDirectoryResolver.resolveHome('${WIKI_ROOT}')}")
    private String defaultRoot;
    @Value("#{'${MARKDOWN_EXTENSION:md}'.trim()}")
    private String defaultExtension;
    @Autowired
    private ObjectFactory<PathBuilder> pathBuilderFactory;

    @Override
    public Path resolvePath(DocumentStoreRequest documentRequest) {
        return pathBuilderFactory.getObject()
                .withPathElement(defaultRoot)
                .withPathElement(Joiner.on(".").join(documentRequest.getDocumentName(), defaultExtension))
                .build();
    }
}
