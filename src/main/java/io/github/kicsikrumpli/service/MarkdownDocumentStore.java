package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.dao.FileDao;
import io.github.kicsikrumpli.dao.PathBuilder;
import io.github.kicsikrumpli.domain.MarkdownDocument;

import java.nio.file.Path;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;

@Component
public class MarkdownDocumentStore implements DocumentStore<MarkdownDocument> {
    @Value("#{homeDirectoryResolver.resolveHome('${WIKI_ROOT}')}")
    private String defaultRoot;
    @Value("${DEFAULT_AUTHOR}")
    private String defaultAuthor;
    @Value("#{'${MARKDOWN_EXTENSION:md}'.trim()}")
    private String defaultExtension;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ObjectFactory<PathBuilder> pathBuilderFactory;

	@Override
	public Optional<MarkdownDocument> retrieveDocument(String documentName) {
	    Optional<String> content = fileDao.readFile(createPath(documentName));
	    MarkdownDocument document = null;
	    if (content.isPresent()) {
	        document = createSimpleDocument(documentName, content.get());
	    }
		return Optional.fromNullable(document);
	}

    private Path createPath(String documentName) {
    	return pathBuilderFactory.getObject()
    			.withPathElement(defaultRoot)
    			.withPathElement(Joiner.on(".").join(documentName, defaultExtension))
    			.build();
    }

    @Override
	public void storeDocument(MarkdownDocument document) {
		throw new UnsupportedOperationException("store operation not yet implemented");
	}
    
    private MarkdownDocument createSimpleDocument(String documentName, String content) {
        return new MarkdownDocument.Builder()
            .withName(documentName)
            .withAuthor(defaultAuthor)
            .withContent(content)
            .build();
    }
    
	void setDefaultRoot(String defaultRoot) {
		this.defaultRoot = defaultRoot;
	}

	void setDefaultAuthor(String defaultAuthor) {
		this.defaultAuthor = defaultAuthor;
	}

	void setDefaultExtension(String defaultExtension) {
		this.defaultExtension = defaultExtension;
	}
}
