package io.github.kicsikrumpli.controller;

import io.github.kicsikrumpli.controller.converter.CreateDocumentFormConverter;
import io.github.kicsikrumpli.controller.converter.DocumentRequestConverter;
import io.github.kicsikrumpli.controller.domain.CreateDocumentForm;
import io.github.kicsikrumpli.service.DocumentStore;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Optional;

@Controller
public class MarkdownJsonController {
	private static final Logger logger = LoggerFactory.getLogger(MarkdownJsonController.class);

	@Autowired
	private DocumentStore<MarkdownDocument> documentStore;
	@Autowired
	private CreateDocumentFormConverter postRequestConverter;
	@Autowired
	private DocumentRequestConverter getRequestConverter;
	
	@RequestMapping(value = "/markdown/{document}.json", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MarkdownDocument getMarkdownDocument(@PathVariable("document") String documentName) {
		logger.info("retrieving document: {}", documentName);
		Optional<MarkdownDocument> markdownDocument = documentStore.retrieveDocument(getRequestConverter.convert(documentName));
		if (! markdownDocument.isPresent()) {
		    throw new DocumentNotFoundException("No document by the name: " + documentName);
		}
        return markdownDocument.get();
	}
	
	@RequestMapping(value = "/markdown",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> postMarkdownDocument(@RequestBody CreateDocumentForm document) {
		logger.info("creating document: {}", document);
		try {
			documentStore.storeDocument(postRequestConverter.convert(document));
		} catch (UnsupportedOperationException e) {
			logger.info("not implemented");
		}
		return Collections.singletonMap("status", "ok");
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(DocumentNotFoundException.class)
	@ResponseBody
	public Map<String, String> handleDocumentNotFound(DocumentNotFoundException e) {
	    return Collections.singletonMap("error", e.getMessage());
	}
}
