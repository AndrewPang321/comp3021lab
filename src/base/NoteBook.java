package base;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class NoteBook {
	// Data member
	private List<Folder> folders;
	
	// Member functions
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	// Overloading method createTextNote
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	private boolean insertNote(String folderName, Note note) {
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
			f.addNote(note);
			return true;
		}
		for (Note n : f.getNotes()) {
			if (n.getTitle().equals(note.getTitle())) {
				System.out.println("Creating note " + n.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
	}
	public List<Folder> getFolders() {
		return folders;
	}
	public void statistics() {
		for (Folder f1 : folders) {
			System.out.println(f1.toString());
		}
	}
	public void sortFolders() {
		// 1. sort the notes of each folders first
		for (Folder f1 : folders)
			f1.sortNotes();
		// 2. sort the folders
		Collections.sort(folders);
	}
	public List<Note> searchNotes(String keywords) {
		List<Note> sn = new ArrayList<Note>();
		for (Folder f1 : folders)
			sn.addAll(f1.searchNotes(keywords));
		return sn;
	}
}
