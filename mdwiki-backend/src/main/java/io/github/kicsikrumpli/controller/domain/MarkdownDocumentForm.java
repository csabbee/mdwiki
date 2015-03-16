package io.github.kicsikrumpli.controller.domain;

/**
 * Form for creating new markdown document.
 * @author kicsikrumpli1
 *
 */
public class MarkdownDocumentForm {
	private String author;
	private String name;
	private String content;
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public MarkdownDocumentForm withAuthor(String author) {
	    setAuthor(author);
	    return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarkdownDocumentForm withName(String name) {
	    setName(name);
	    return this;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MarkdownDocumentForm withContent(String content) {
	    setContent(content);
	    return this;
	}
	
	@Override
	public String toString() {
		return "MarkdownDocumentForm [author=" + author + ", name=" + name
				+ ", content=" + content + "]";
	}
}
