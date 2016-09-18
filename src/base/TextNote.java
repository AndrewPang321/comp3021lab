package base;

public class TextNote extends Note{
	// data member
	private String content;
	
	// member function
	public TextNote(String noteContent) {
		super(noteContent);
		setContent(noteContent);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String text) {
		content = text;
	}
}
