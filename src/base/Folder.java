package base;

import java.util.List;
import java.util.ArrayList;

public class Folder {
	// data members
	private List<Note> notes;
	private String name;
	
	// member functions
	public Folder(String folderName) {
		notes = new ArrayList<Note>();
		name = folderName;
	}
	public void addNote(Note a) {
		notes.add(a);
	}
	public String getName() {
		return name;
	}
	public List<Note> getNotes() {
		return notes;
	}
//	@Override
//	public boolean equals(Object obj) {
//		// traverse all the notes to determine if the note is already existed
//		for (Note n1 : notes) {
//			if (obj instanceof Note)
//				if (n1.getTitle() == ((Note)obj).title) {
//					return true;
//				}
//		}
//		return false;
//	}
	public String toString() {
		//TODO: Count the number of textnote and imagenote in a folder
		int tNote = 0, iNote = 0;
		// traverse all the notes to identify textnote and imagenote
		for (Note n1 : notes) {
			if (n1 instanceof TextNote)
				tNote++;
			else
				iNote++;
		}
		return(this.name + ":" + tNote + ":" + iNote);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
