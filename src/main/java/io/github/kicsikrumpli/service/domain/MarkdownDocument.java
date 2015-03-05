package io.github.kicsikrumpli.service.domain;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkdownDocument other = (MarkdownDocument) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}
}
