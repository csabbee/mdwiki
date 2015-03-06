package io.github.kicsikrumpli.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.github.kicsikrumpli.dao.FileDao;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

import com.google.common.base.Optional;

/**
 * Test class for {@link MarkdownDocumentStore}.
 * 
 * @author daniel
 *
 */
public class MarkdownDocumentStoreTest {
    private static final String DEFAULT_ROOT = "default-root";
	private static final String DUMMY_DUCUMENT_NAME_WITH_EXTENSION = "dummy-ducument-name.ext";
	private static final String DUMMY_FILE_CONTENT = "dummy-file-content";
	private static final String DUMMY_DUCUMENT_NAME = "dummy-ducument-name";
	private static final String DEFAULT_AUTHOR = "default-author";
	@InjectMocks
    private MarkdownDocumentStore underTest;
    @Mock
    private FileDao mockFileDao;
    @Mock
    private ObjectFactory<PathBuilder> mockPathBuilderFactory;
    @Mock
    private PathBuilder mockPathBuilder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest.setDefaultAuthor(DEFAULT_AUTHOR);
        underTest.setDefaultExtension("ext");
        underTest.setDefaultRoot(DEFAULT_ROOT);
        given(mockPathBuilderFactory.getObject()).willReturn(mockPathBuilder);
        given(mockPathBuilder.withPathElement(Mockito.anyString())).willReturn(mockPathBuilder);
        given(mockPathBuilder.build()).willReturn(Paths.get("dummy", "path"));
    }

    @Test
    public void testRetrieveShouldPrependFileNameWithDefaultRoot() {
    	// GIVEN
    	ArgumentCaptor<String> pathElementCaptor = ArgumentCaptor.forClass(String.class);
    	given(mockFileDao.readFile(Mockito.any(Path.class))).willReturn(Optional.of(DUMMY_FILE_CONTENT));
    	
    	// WHEN
    	underTest.retrieveDocument(DUMMY_DUCUMENT_NAME);
    	
    	// THEN
    	verify(mockPathBuilder, times(2)).withPathElement(pathElementCaptor.capture());
    	assertThat(pathElementCaptor.getAllValues().get(0), Matchers.is(DEFAULT_ROOT));
    }
    
    @Test
    public void testRetrieveShouldAppendFileNameWithDefaultExtension() {
    	// GIVEN
    	ArgumentCaptor<String> pathElementCaptor = ArgumentCaptor.forClass(String.class);
    	given(mockFileDao.readFile(Mockito.any(Path.class))).willReturn(Optional.of(DUMMY_FILE_CONTENT));
    	
    	// WHEN
    	underTest.retrieveDocument(DUMMY_DUCUMENT_NAME);
    	
    	// THEN
    	verify(mockPathBuilder, times(2)).withPathElement(pathElementCaptor.capture());
    	assertThat(pathElementCaptor.getAllValues().get(1), Matchers.is(DUMMY_DUCUMENT_NAME_WITH_EXTENSION));
    }

    @Test
    public void testRetrieveShouldReturnAbsentWhenFileDoesNotExist() {
    	// GIVEN
    	given(mockFileDao.readFile(Mockito.any(Path.class))).willReturn(Optional.<String>absent());

    	// WHEN
    	Optional<MarkdownDocument> document = underTest.retrieveDocument(DUMMY_DUCUMENT_NAME);
    	
    	// THEN
    	assertThat(document.isPresent(), Matchers.is(false));
    }
    
    @Test
    public void testRetrieveShoudCreateDocumentWhenFileExists() {
    	// GIVEN
    	given(mockFileDao.readFile(Mockito.any(Path.class))).willReturn(Optional.of(DUMMY_FILE_CONTENT));

    	// WHEN
    	Optional<MarkdownDocument> document = underTest.retrieveDocument(DUMMY_DUCUMENT_NAME);
    	
    	// THEN
    	assertThat(document.get(), Matchers.is(createDummyMarkdownDocument()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testStoreShouldThrowUnimplementedException() {
    	// GIVEN in setUp
    	
    	// WHEN
    	underTest.storeDocument(createDummyMarkdownDocument());
    	
    	// THEN should throw
    }

    private MarkdownDocument createDummyMarkdownDocument() {
    	return new MarkdownDocument.Builder()
    	.withAuthor(DEFAULT_AUTHOR)
    	.withName(DUMMY_DUCUMENT_NAME)
    	.withContent(DUMMY_FILE_CONTENT)
    	.build();
    }
}
