package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.controller.domain.CreateDocumentForm;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import org.springframework.stereotype.Component;

/**
 * Converts request object to domain object.
 * @author kicsikrumpli1
 *
 */
@Component
public class CreateDocumentFormConverter {
	
	/**
	 * converts {@link CreateDocumentForm} object into {@link MarkdownDocument} domain object.
	 * @param postDocument request object
	 * @return domain object
	 */
	public MarkdownDocument convert(CreateDocumentForm postDocument) {
		return new MarkdownDocument.Builder()
			.withAuthor(postDocument.getAuthor())
			.withName(postDocument.getName())
			.withContent(postDocument.getContent())
			.build();
	}
}
