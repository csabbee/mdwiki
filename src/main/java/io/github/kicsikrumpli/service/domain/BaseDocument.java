package io.github.kicsikrumpli.service.domain;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDocument other = (BaseDocument) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
   
}
