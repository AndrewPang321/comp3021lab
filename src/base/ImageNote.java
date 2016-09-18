package base;

import java.io.File;

public class ImageNote extends Note{
	// data member
	private File image;
	
	// member functions
	public ImageNote(String noteContent) {
		super(noteContent);
		// Create a File object with the string noteContent (Conversion constructor)
		File file = new File(noteContent);
		setImage(file);
	}
	public File getIamge() {
		return image;
	}
	public void setImage(File a) {
		image = a;
	}
}
