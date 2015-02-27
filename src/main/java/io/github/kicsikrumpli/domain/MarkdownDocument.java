package io.github.kicsikrumpli.domain;

/**
 * Domain object for markdown document.
 * @author daniel
 *
 */
public class MarkdownDocument extends BaseDocument {

	private String content;

	private MarkdownDocument(Builder builder) {
	    this.content = builder.content;
	    setAuthor(builder.author);
	    setName(builder.name);
	}
	
	public String getContent() {
		return content;
	}
	
	public static class Builder {
	    private String name;
	    private String author;
	    private String content;
	    
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }
	    
	    public MarkdownDocument build() {
	        return new MarkdownDocument(this);
	    }
	}
}
