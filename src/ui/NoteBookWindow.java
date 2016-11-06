package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	/**
	 * current note selected by user
	 */
	String currentNote = "";
	/**
	 * the stage of this display
	 */
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
//		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);
		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO load a Notebook from file
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose A File Which Contains A Notebook Object");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null) {
					loadNoteBook(file);
					// Update the display foldersComboBox
						// Clear the original contents in the comboBox first
					foldersComboBox.getItems().clear();
					foldersComboBox.setValue("-----");
					updateListView();
					if (noteBook != null) {
						List<Folder> folders = noteBook.getFolders();
						for (Folder f : folders)
							foldersComboBox.getItems().addAll(f.getName());
					}
				}
			}
		});
		Button buttonSave = new Button("Save to File");
//		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO save a Notebook to file
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose A File To Save The Content");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null) {
					// Using the method in class NoteBook to save the contents to the file path specified
					Boolean save = noteBook.save(file.getPath());
					if (save) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successfully saved");
						alert.setContentText("Your file has been saved to file " + file.getName());
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK)
								System.out.println("Pressed OK.");
						});
					}
				}
			}
		});
		
		// TODO :
		Label label = new Label("Search : ");
		TextField textSearch = new TextField();
		Button buttonSearch = new Button("Search");
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = textSearch.getText();
				textAreaNote.setText("");
				updateListView();
			}
		});
		Button buttonRemove = new Button("Clear Search");
		buttonRemove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = "";
				textSearch.setText("");
				textAreaNote.setText("");
				updateListView();
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave, label, textSearch, buttonSearch, buttonRemove);

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		HBox hbox = new HBox();
		hbox.setSpacing(10); // Gap between nodes
		
		Button addFolder = new Button("Add a Folder");
		addFolder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");
				
				// Traditional way to get the responsive value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					// TODO
					List<Folder> folders = noteBook.getFolders();
					boolean duplicate = false;
					for (Folder f : folders)
						if (f.getName().equals(result.get())) {
							duplicate = true;
							break;
						}
					
					if (result.get().equals("")) {
						Alert emptyString = new Alert(AlertType.WARNING);
						emptyString.setContentText("Please input an valid folder name");
						emptyString.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK)
								System.out.println("Pressed OK.");
						});
					}
					else if (duplicate) {
						Alert collision = new Alert(AlertType.WARNING);
						collision.setContentText("You already have a folder named with " + result.get());
						collision.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK)
								System.out.println("Pressed OK.");
						});
					}
					else {
						noteBook.addFolder(result.get());
						// Update the display foldersComboBox
						foldersComboBox.getItems().add(result.get());
					}
				}
			}
		});
		
		Button addNote = new Button("Add a Note");
		addNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				if (currentFolder.equals("-----") || currentFolder.equals("")) {
					Alert folderNotSelected = new Alert(AlertType.WARNING);
					folderNotSelected.setContentText("Please choose a folder first!");
					folderNotSelected.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK)
							System.out.println("Pressed OK.");
					});
				}
				else {
					TextInputDialog dialog1 = new TextInputDialog("Add a Note");
					dialog1.setTitle("Input");
					dialog1.setHeaderText("Add a new note to current folder");
					dialog1.setContentText("Please enter the name of your note:");
					
					// Traditional way to get the responsive value.
					Optional<String> result1 = dialog1.showAndWait();
					if (result1.isPresent()) {
						// Add a new Note now
						boolean success = noteBook.createTextNote(currentFolder, result1.get());
						if (success) {
							// Update the list view
							updateListView();
							Alert inserted = new Alert(AlertType.INFORMATION);
							inserted.setTitle("Successful!");
							inserted.setContentText("Insert note " + result1.get() + " to folder " + currentFolder + " successfully!");
							inserted.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK)
									System.out.println("Pressed OK.");
							});
						}
					}
				}
			}
		});
		
//		foldersComboBox.getItems().addAll("FOLDER NAME 1", "FOLDER NAME 2", "FOLDER NAME 3");
		// TODO
		if (noteBook != null) {
			List<Folder> folders = noteBook.getFolders();
			for (Folder f : folders)
				foldersComboBox.getItems().addAll(f.getName());
		}

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();
			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				currentNote = title;
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreaNote
				String content = "";

				int index1 = 0, index2 = 0;
				boolean foundFolder = false, foundNote = false;
				List<Folder> folders = noteBook.getFolders();
				for (int i = 0; i < folders.size(); i++)
					if (folders.get(i).getName().equals(currentFolder)) {
						index1 = i;
						foundFolder = true;
						break;
					}
				if (foundFolder) {
					List<Note> notes = folders.get(index1).getNotes();
					for (int j = 0; j < notes.size(); j++)
						if (notes.get(j).getTitle().equals(title)) {
							index2 = j;
							foundNote = true;
							break;
						}
					if (foundNote) {
						if (notes.get(index2) instanceof TextNote)
							content = ((TextNote)notes.get(index2)).getContent();
					}
				}
				textAreaNote.setText(content);

			}
		});
		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(addFolder);
		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(addNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		int index = 0; boolean found = false;
		List<Folder> folders = noteBook.getFolders();
		for (int i = 0; i < folders.size(); i++)
			if (folders.get(i).getName().equals(currentFolder)) {
				index = i;
				found = true;
				break;
			}
		if (found) {
			for (Note n : folders.get(index).getNotes())
				if (n instanceof TextNote)
					list.add(n.getTitle());
		}
		
		// The case when SEARCH function is being used
		if (currentSearch != "") {
			// Clear the original content in the list
			list.clear();
			List<Note> notes = folders.get(index).searchNotes(currentSearch);
			for (Note n : notes)
				list.add(n.getTitle());
		}
		
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		ImageView deleteView = new ImageView(new Image(new File("delete.png").toURI().toString()));
		deleteView.setFitHeight(18);
		deleteView.setFitWidth(18);
		deleteView.setPreserveRatio(true);
		
		Button save = new Button("Save Note");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				if (currentFolder.equals("-----") || currentFolder.equals("") || currentNote.equals("")) {
					Alert notSelected = new Alert(AlertType.WARNING);
					notSelected.setContentText("Please select a folder and a note");
					notSelected.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK)
							System.out.println("Pressed OK.");
					});
				}
				else {
					Folder tempFolder = null;
					Note tempNote = null;
					for (Folder f : noteBook.getFolders())
						if (f.getName().equals(currentFolder)) {
							tempFolder = f;
							break;
						}
					if (tempFolder != null) {
						for (Note n : tempFolder.getNotes())
							if (n.getTitle().equals(currentNote)) {
								tempNote = n;
								break;
							}
					}
					if (tempNote != null) {
						if (tempNote instanceof TextNote)
							((TextNote)tempNote).setContent(textAreaNote.getText());
					}
				}
			}
		});
		Button delete = new Button("Delete Note");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				if (currentFolder.equals("-----") || currentFolder.equals("") || currentNote.equals("")) {
					Alert notSelected1 = new Alert(AlertType.WARNING);
					notSelected1.setContentText("Please select a folder and a note");
					notSelected1.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK)
							System.out.println("Pressed OK.");
					});
				}
				else {
					Folder tempFolder1 = null;
					for (Folder f : noteBook.getFolders())
						if (f.getName().equals(currentFolder)) {
							tempFolder1 = f;
							break;
						}
					if (tempFolder1 != null) {
						boolean deleted = tempFolder1.removeNote(currentNote);
						if (deleted) {
							// Update the list view
							updateListView();
							Alert deletedSuccess = new Alert(AlertType.CONFIRMATION);
							deletedSuccess.setTitle("Succeed!");
							deletedSuccess.setContentText("Your note has been successfully removed");
							deletedSuccess.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK)
									System.out.println("Pressed OK.");
								if (rs == ButtonType.CANCEL)
									System.out.println("Pressed CANCEL");
							});
						}
					}
				}
			}
		});
		
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 1, 4, 1);		// column span == 4
		grid.add(saveView, 0, 0);
		grid.add(save, 1, 0);
		grid.add(deleteView, 2, 0);
		grid.add(delete, 3, 0);

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	
	private void loadNoteBook(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			NoteBook nb = (NoteBook)ois.readObject();
			noteBook = nb;
			ois.close();
		} catch (IOException|ClassNotFoundException|NullPointerException ex) {
			System.out.println("Exeception: File Not Found");
			ex.printStackTrace();
		}
		
	}

}
