package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {
	
	@FXML
	private MenuItem closeButton;
	
	@FXML
	private void handleCloseButton(ActionEvent event) {
		
		ContactAddController contactAddController = new ContactAddController();
		CourseWorkController courseWorkController = new CourseWorkController();
		EventController eventController = new EventController();
		NoteController noteController = new NoteController();
		
	}
	
}
