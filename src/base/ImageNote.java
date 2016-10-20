package base;

import java.io.File;

public class ImageNote extends Note {
	// data member
	private File image;
	private static final long serialVersionUID = 1L;

	
	// member functions
	public ImageNote(String title) {
		super(title);
	}
	public File getIamge() {
		return image;
	}
	public void setImage(File a) {
		image = a;
	}
}
