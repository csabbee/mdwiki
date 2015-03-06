package io.github.kicsikrumpli.controller;

import java.nio.charset.Charset;

import io.github.kicsikrumpli.dao.domain.TextDocument;

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
    private ObjectFactory<TextDocument.Builder> textDocumentBuilderFactory;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/sandbox", method = RequestMethod.GET)
	public String home() {
        TextDocument document1 = textDocumentBuilderFactory.getObject()
                .withEncoding(Charset.forName("ISO-8859-1"))
                .withAuthor("author1")
                .withName("name1")
                .build();
	    TextDocument document2 = textDocumentBuilderFactory.getObject()
	            .withAuthor("author2")
	            .build();
	    logger.info("textdocument1:[{}] textdocument2:[{}] ", document1, document2);
		return "home";
	}
	
}
