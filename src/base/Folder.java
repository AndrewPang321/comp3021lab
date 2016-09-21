package base;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Folder implements Comparable<Folder>{
	// data members
	private List<Note> notes;
	private String name;
	
	// member functions
	public Folder(String name) {
		notes = new ArrayList<Note>();
		this.name = name;
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
	@Override
	public String toString() {
		//TODO: Count the number of textnote and imagenote in a folder
		int nText = 0, nImage = 0;
		// traverse all the notes to identify textnote and imagenote
		for (Note note : notes) {
			if (note instanceof TextNote)
				nText++;
			else
				nImage++;
		}
		return (name + ":" + nText + ":" + nImage);
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
	@Override
	public int compareTo(Folder o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.name);
	}
	public void sortNotes() {
		Collections.sort(notes);
	}
	public List<Note> searchNotes(String keywords) {
		
		List<Note> result = new ArrayList<Note>();
		List<Boolean> bool = new ArrayList<Boolean>();
		String[] parts = keywords.split(" ", 0);
		int index;
		
		for (Note n1 : notes) {	
			// Clear the Boolean ListArray
			bool.clear(); index = 0;						
			
			while (index != parts.length) {
				// A. If the next keyword is a "or", we check the current word[index] and jump to index+2
				if (parts[index+1].equalsIgnoreCase("or")) {
					// 1. ImageNote
					if (n1 instanceof ImageNote) {
						if (n1.getTitle().toLowerCase().contains(parts[index].toLowerCase()) || n1.getTitle().toLowerCase().contains(parts[index+2].toLowerCase()))  {
							bool.add(true);
						}
						else
							bool.add(false);
					}
					// 2. TextNote
					else if (n1 instanceof TextNote) {
						if (n1.getTitle().toLowerCase().contains(parts[index].toLowerCase()) || ((TextNote)n1).getContent().toLowerCase().contains(parts[index].toLowerCase())
								|| n1.getTitle().toLowerCase().contains(parts[index+2].toLowerCase()) || ((TextNote)n1).getContent().toLowerCase().contains(parts[index+2].toLowerCase()))
							bool.add(true);
						else
							bool.add(false);
					}
					// advance the index value
					index = index+3;
				} else { // B. The next keyword is not a "or"
					// 1. ImageNote
					if (n1 instanceof ImageNote) {
						if (n1.getTitle().toLowerCase().contains(parts[index].toLowerCase())) 
							bool.add(true);
						else
							bool.add(false);
					}
					// 2. TextNote
					else if (n1 instanceof TextNote) {
						if (n1.getTitle().toLowerCase().contains(parts[index].toLowerCase()) || ((TextNote)n1).getContent().toLowerCase().contains(parts[index].toLowerCase()))
							bool.add(true);
						else
							bool.add(false);
					}
					// advance the index value
					index++;
				}
			}
			boolean x = true;
			for (Boolean b : bool) {
				if (b.booleanValue() == false) {
					x = false; break;
				}
			}
			if (x == true)
				result.add(n1);
		}
		return result;
	}
}
