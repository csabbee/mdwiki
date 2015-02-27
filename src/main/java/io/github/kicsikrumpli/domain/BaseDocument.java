package io.github.kicsikrumpli.domain;

/**
 * Abstract base class containing document properties. 
 * @author daniel
 *
 */
public abstract class BaseDocument {
    private String name;
    private String author;
    
    public String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    protected void setAuthor(String author) {
        this.author = author;
    }
   
}
