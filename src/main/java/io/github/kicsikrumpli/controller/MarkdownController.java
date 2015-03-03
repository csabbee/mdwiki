package io.github.kicsikrumpli.controller;

import io.github.kicsikrumpli.domain.MarkdownDocument;
import io.github.kicsikrumpli.service.DocumentStore;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Optional;

@Controller
public class MarkdownController {
	@Autowired
	private DocumentStore<MarkdownDocument> documentStore;
	
	@RequestMapping(value = "/wiki/{page}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String getPageWithEmbeddedMarkdown() {
		return "mdview";
	}
	
	@RequestMapping(value = "/markdown/{document}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MarkdownDocument getMarkdownDocument(@PathVariable("document") String document) {
		Optional<MarkdownDocument> markdownDocument = documentStore.retrieveDocument(document);
		if (! markdownDocument.isPresent()) {
		    throw new DocumentNotFoundException("No document by the name: " + document);
		}
        return markdownDocument.get();
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(DocumentNotFoundException.class)
	@ResponseBody
	public Map<String, String> handleDocumentNotFound(DocumentNotFoundException e) {
	    return Collections.singletonMap("error", e.getMessage());
	}
}
