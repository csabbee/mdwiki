package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.controller.domain.MarkdownDocumentForm;
import io.github.kicsikrumpli.service.domain.DocumentStoreCreateRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts controller form into document store request object.
 * @author kicsikrumpli1
 *
 */
@Component
public class DocumentStoreCreateRequestConverter {
	@Autowired
	private ObjectFactory<DocumentStoreCreateRequest.Builder> requestBuilderFactory;
	
	public DocumentStoreCreateRequest convert(MarkdownDocumentForm form) {
		return requestBuilderFactory.getObject()
				.withAuthor(form.getAuthor())
				.withDocumentName(form.getName())
				.withContent(form.getContent())
				.build();
	}
}
