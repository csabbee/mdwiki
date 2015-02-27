package io.github.kicsikrumpli.service;

import io.github.kicsikrumpli.domain.MarkdownDocument;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;

/**
 * Test class for {@link MarkdownDocumentStore}.
 * 
 * @author daniel
 *
 */
public class MarkdownDocumentStoreTest {
    @InjectMocks
    private MarkdownDocumentStore underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveDocumentShouldLoadDocumentWhenItExists() {
        // GIVEN

        // WHEN
        //Optional<MarkdownDocument> mockDocument = underTest.retrieveDocument(null);

        // THEN
    }

    @Test
    public void testRetrieveDocumentShouldThrowWhenItDoesNotExist() {
        // GIVEN

        // WHEN
        //Optional<MarkdownDocument> mockDocument = underTest.retrieveDocument(null);

        // THEN
    }

}
