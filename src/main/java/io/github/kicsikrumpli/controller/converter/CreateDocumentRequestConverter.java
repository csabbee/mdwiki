package io.github.kicsikrumpli.controller.converter;

import io.github.kicsikrumpli.controller.domain.CreateDocumentRequest;
import io.github.kicsikrumpli.service.domain.MarkdownDocument;

import org.springframework.stereotype.Component;

/**
 * Converts request object to domain object.
 * @author kicsikrumpli1
 *
 */
@Component
public class CreateDocumentRequestConverter {
	
	/**
	 * converts {@link CreateDocumentRequest} object into {@link MarkdownDocument} domain object.
	 * @param postDocument request object
	 * @return domain object
	 */
	public MarkdownDocument convert(CreateDocumentRequest postDocument) {
		return new MarkdownDocument.Builder()
			.withAuthor(postDocument.getAuthor())
			.withName(postDocument.getName())
			.withContent(postDocument.getContent())
			.build();
	}
}
