package base;

import java.util.List;
import java.util.ArrayList;

public class NoteBook {
	// Data member
	private List<Folder> folders;
	
	// Member functions
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	public boolean createTextNote(String folderName, String content) {
		TextNote note = new TextNote(content);
		return insertNote(folderName, note);
	}
	public boolean createImageNote(String folderName, String image) {
		ImageNote note = new ImageNote(image);
		return insertNote(folderName, note);
	}
	private boolean insertNote(String folderName, Note obj) {
		Folder f = null;
		for (Folder f1 : folders) {
			if (f1.getName().equals(folderName)) {
				f = f1;
				break;
			}
		}
		if (f == null) {
			// Folder not found, need to create a new folder and add it the the list folders
			f = new Folder(folderName);
			folders.add(f);
			// 2b. The new folder shouldn't have any notes, so insert the note immediately
			f.addNote(obj);
			return true;
		}
		for (Note n : f.getNotes()) {
			if (n.getTitle().equals(obj.getTitle())) {
				System.out.println("Creating note " + n.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		f.addNote(obj);
		return true;
	}
	public void statistics() {
		for (Folder f1 : folders) {
			System.out.println(f1.toString());
		}
	}
}
