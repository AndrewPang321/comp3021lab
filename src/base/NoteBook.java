package base;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NoteBook implements Serializable{
	// Data member
	private List<Folder> folders;
	private static final long serialVersionUID = 1L;
	
	// Member functions
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	// Constructor of an object NoteBook from an object serialization on disk
	// @param file, the path of the file for loading the object serialization
	public NoteBook(String file) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook) in.readObject();
			in.close();
			this.folders = n.folders;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	// Method to save the NoteBook instance to file
	// @param file, the path of the file where to save the object serialization
	// @return true if save on file is successful, false otherwise
	public boolean save(String file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**
	 * Add a new folder to the notebook
	 */
	public void addFolder(String folderName) {
		Folder folder = new Folder(folderName);
		folders.add(folder);
	}
}
