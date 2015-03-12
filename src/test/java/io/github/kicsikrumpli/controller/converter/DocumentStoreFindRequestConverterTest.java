package io.github.kicsikrumpli.controller.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

/**
 * Test class for {@link DocumentStoreFindRequestConverter}.
 * @author kicsikrumpli1
 *
 */
public class DocumentStoreFindRequestConverterTest {
	private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
	
	@InjectMocks
	private DocumentStoreFindRequestConverter underTest;
	@Mock
    private ObjectFactory<DocumentStoreFindRequest.Builder> mockDocumentBuilderFactory;

	private String mockDocumentName = MOCK_DOCUMENT_NAME;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		given(mockDocumentBuilderFactory.getObject()).willReturn(new DocumentStoreFindRequest.Builder());
	}
	
	@Test
	public void testConvertShouldCreateRequestObject() {
		// GIVEN in setUp
		
		// WHEN
		DocumentStoreFindRequest request = underTest.convert(mockDocumentName);
		
		// THEN
		assertThat(request, is(DocumentStoreFindRequest.class));
	}
	
	@Test
	public void testConvertShouldCopyDocumentNameIntoRequestObject() {
		// GIVEN in setUp
		
		// WHEN
        DocumentStoreFindRequest request = underTest.convert(mockDocumentName);
		
		// THEN
		assertThat(request.getDocumentName(), is(MOCK_DOCUMENT_NAME));
	}
}
