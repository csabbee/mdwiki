package io.github.kicsikrumpli.service.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DocumentStoreCreateRequest {
	
	private DocumentStoreCreateRequest(Builder builder) {
		
	}
	
	@Component
	@Scope("prototype")
	public static class Builder {
		
		public DocumentStoreCreateRequest build() {
			return new DocumentStoreCreateRequest(this);
		}
	}

}
