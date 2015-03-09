package io.github.kicsikrumpli.controller.domain;

public class CreateDocumentForm {
    private String name;
    private String author;
	private String content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "PostDocument [name=" + name + ", author=" + author
				+ ", content=" + content + "]";
	}
}
