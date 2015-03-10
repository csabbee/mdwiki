package io.github.kicsikrumpli.service.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Domain object for markdown document.
 * @author daniel
 *
 */
public class MarkdownDocument {
    private String name;
    private String author;
	private String content;

	private MarkdownDocument(Builder builder) {
	    this.content = builder.content;
	    this.author = builder.author;
	    this.name = builder.name;
	}

	public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

	public String getContent() {
		return content;
	}

    @Component
    @Scope("prototype")
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
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MarkdownDocument other = (MarkdownDocument) obj;
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!content.equals(other.content)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
