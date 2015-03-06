package io.github.kicsikrumpli.controller;

import io.github.kicsikrumpli.dao.domain.TextDocument;
import io.github.kicsikrumpli.dao.domain.TextDocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SandboxController {
    private static final Logger logger = LoggerFactory.getLogger(SandboxController.class);
    
    @Autowired
    private ObjectFactory<TextDocumentBuilder> textDocumentBuilderFactory;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/sandbox", method = RequestMethod.GET)
	public String home() {
	    TextDocument document = textDocumentBuilderFactory.getObject().build();
	    logger.info("test textdocument: {}", document);
		return "home";
	}
	
}
