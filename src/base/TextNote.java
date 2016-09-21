package base;

public class TextNote extends Note{
	// data member
	private String content;
	
	// member function
	public TextNote(String title) {
		super(title);
	}
	public TextNote(String title, String noteContent) {
		super(title);
		setContent(noteContent);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String text) {
		content = text;
	}
}
