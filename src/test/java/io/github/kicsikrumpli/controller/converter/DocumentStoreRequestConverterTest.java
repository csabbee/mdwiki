package io.github.kicsikrumpli.controller.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.service.domain.DocumentStoreFindRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;

/**
 * Test class for {@link DocumentStoreFindRequestConverter}.
 * @author kicsikrumpli1
 *
 */
public class DocumentStoreRequestConverterTest {
	private static final String MOCK_DOCUMENT_NAME = "mock-document-name";
	
	@InjectMocks
	private DocumentStoreFindRequestConverter underTest;
	@Mock
    private ObjectFactory<DocumentStoreFindRequest.Builder> mockDocumentBuilderFactory;
	@Mock
	private DocumentStoreFindRequest.Builder mockBuilder;

	private String mockDocumentName = MOCK_DOCUMENT_NAME;
	private DocumentStoreFindRequest mockDocumentStoreRequest = createMockDocumentStoreRequest();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		given(mockDocumentBuilderFactory.getObject()).willReturn(mockBuilder);
		given(mockBuilder.withDocumentName(Mockito.anyString())).willReturn(mockBuilder);
		given(mockBuilder.build()).willReturn(mockDocumentStoreRequest);
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
		// GIVEN
		ArgumentCaptor<String> documentName = ArgumentCaptor.forClass(String.class);
		
		// WHEN
		underTest.convert(mockDocumentName);
		
		// THEN
		Mockito.verify(mockBuilder).withDocumentName(documentName.capture());
		assertThat(documentName.getValue(), is(MOCK_DOCUMENT_NAME));

	}

	private DocumentStoreFindRequest createMockDocumentStoreRequest() {
		return new DocumentStoreFindRequest.Builder().withDocumentName(MOCK_DOCUMENT_NAME).build();
	};
}
