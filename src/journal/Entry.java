package journal;

import java.io.Serializable;
import java.util.UUID;

public class Entry implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private UUID id;
	private String entryName;
	private String content;
	
	public Entry(String entryName, String content) {
		this.id = UUID.randomUUID();
		this.entryName = entryName;
		this.content = content;
	}

	public UUID getId() {
        return id;
    }
	
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override 
	public String toString() {
		return "Entry name: " + this.entryName + "\nContent:\n" + this.content;
	}
}
