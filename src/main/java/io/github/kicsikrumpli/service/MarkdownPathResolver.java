package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.controller.domain.GetDocumentRequest;

import java.nio.file.Path;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Joiner;

public class MarkdownPathResolver {
    @Value("#{homeDirectoryResolver.resolveHome('${WIKI_ROOT}')}")
    private String defaultRoot;
    @Value("#{'${MARKDOWN_EXTENSION:md}'.trim()}")
    private String defaultExtension;
    @Autowired
    private ObjectFactory<PathBuilder> pathBuilderFactory;

    public Path resolvePath(GetDocumentRequest documentRequest) {
        return pathBuilderFactory.getObject()
                .withPathElement(defaultRoot)
                .withPathElement(Joiner.on(".").join(documentRequest.getDocumentName(), defaultExtension))
                .build();
    }
}
