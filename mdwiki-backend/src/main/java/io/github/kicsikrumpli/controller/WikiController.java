package io.github.kicsikrumpli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class WikiController {
    private static final Logger logger = LoggerFactory.getLogger(WikiController.class);

    @RequestMapping(value = "/wiki/{page}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String getPageWithEmbeddedMarkdown() {
        logger.info("Rendering view");
        return "mdview";
    }
}
