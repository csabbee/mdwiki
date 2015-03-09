package io.github.kicsikrumpli.service.strategy;

import io.github.kicsikrumpli.service.domain.DocumentRequest;
import io.github.kicsikrumpli.service.strategy.builder.PathBuilder;

import java.nio.file.Path;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component
public class MarkdownPathResolutionStrategy {
    @Value("#{homeDirectoryResolver.resolveHome('${WIKI_ROOT}')}")
    private String defaultRoot;
    @Value("#{'${MARKDOWN_EXTENSION:md}'.trim()}")
    private String defaultExtension;
    @Autowired
    private ObjectFactory<PathBuilder> pathBuilderFactory;

    public Path resolvePath(DocumentRequest documentRequest) {
        return pathBuilderFactory.getObject()
                .withPathElement(defaultRoot)
                .withPathElement(Joiner.on(".").join(documentRequest.getDocumentName(), defaultExtension))
                .build();
    }
}
