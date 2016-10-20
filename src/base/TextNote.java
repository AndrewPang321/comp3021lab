package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextNote extends Note {
	// data member
	private String content;
	private static final long serialVersionUID = 1L;

	
	// member function
	public TextNote(String title) {
		super(title);
	}
	// load a TextNote from File f
	// the title of the TextNote is the name of the file
	// the content of the TextNote is the content of the file
	// @param File f
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
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
	// get the content of a file
	// @param absolutePath of the file
	// @return the content of the file
	private String getTextFromFile(String absolutePath) {
		String result = "";
		FileInputStream fis = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(absolutePath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			result = br.readLine();
			br.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	// export text note to file
	// @param pathFolder path of the folder where to export the note
	// the file has to be named as the title of the note with extension ".txt"
	// if the title contains white spaces " " they has to be replaced with underscores "_"
	public void exportTextToFile(String pathFolder) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = new File(pathFolder + File.separator + this.getTitle().replace(" ", "_") + ".txt");
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(this.content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public int countLetters(){
		HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		String a = this.getTitle() + this.getContent();
		int b = 0;
		int num = 0;
		for (int i = 0; i < a.length(); i++) {
			Character c = a.charAt(i);
			if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') {
				if (!count.containsKey(c)) {
					count.put(c, 1);
					num++;
				} else {
					count.put(c, count.get(c) + 1);
					if (count.get(c) > b) {
						num++;
					}
				}
			}
		}
		return num;
	}
}
