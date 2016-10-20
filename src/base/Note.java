package base;

import java.util.Collections;
import java.util.Date;
import java.io.Serializable;

public class Note implements Comparable<Note>, Serializable{
	// Data member
	private Date date;
	private String title;
	private static final long serialVersionUID = 1L;
	
	// Member function
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	public String getTitle() {
		return title;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public int compareTo(Note o) {
		// TODO Auto-generated method stub
		// 1. this obj's date is bigger than input obj's date
		if (this.date.before(o.date))
			return 1;
		// 2. this obj's date is smaller than input obj's date
		else if (this.date.after(o.date))
			return -1;
		// 3. otherwise, it is equal
		else
			return 0;
	}
	public String toString() {
		return date.toString() + "\t" + title;
	}
}

