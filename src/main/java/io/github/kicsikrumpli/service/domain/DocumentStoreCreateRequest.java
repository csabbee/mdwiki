package io.github.kicsikrumpli.service.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Document store request object for creating new document;
 * @author kicsikrumpli1
 *
 */
public final class DocumentStoreCreateRequest {
	private String documentName;
	private String author;
	private String content;

	private DocumentStoreCreateRequest(Builder builder) {
		documentName = builder.documentName;
		author = builder.author;
		content = builder.content;
	}
	
	public String getDocumentName() {
		return documentName;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	/**
	 * Builds document store create request object.
	 * @author kicsikrumpli1
	 *
	 */
	@Component
	@Scope("prototype")
	public static class Builder {
		private String documentName;
		private String author;
		private String content;

		/**
		 * Adds name.
		 * @param name of document
		 * @return builder instance
		 */
		public Builder withDocumentName(String name) {
			this.documentName = name;
			return this;
		}
		
		/**
		 * Adds author.
		 * @param author of document
		 * @return builder instance
		 */
		public Builder withAuthor(String author) {
			this.author = author;
			return this;
		}
		
		/**
		 * Adds content.
		 * @param content of document
		 * @return builder instance
		 */
		public Builder withContent(String content) {
			this.content = content;
			return this;
		}
		
		/**
		 * Builds request object.
		 * @return request object
		 */
		public DocumentStoreCreateRequest build() {
			return new DocumentStoreCreateRequest(this);
		}
	}
}
