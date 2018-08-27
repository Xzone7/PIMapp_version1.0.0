package application.controller;

import javafx.application.Platform;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class EventController {

    @FXML
    private DatePicker picker;

    @FXML
    private TextArea notes;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    private boolean saveConfirm = false;
    
    private boolean deleteConfirm = false;

    private Map<LocalDate, String> data = new HashMap<>();
    
    
    public void initialize() {
    	
    	// Test
    	System.out.println("initialized() is calling");
        load();

        picker.valueProperty().addListener((o, oldDate, date) -> {
            notes.setText(data.getOrDefault(date, ""));
        });

        picker.setValue(LocalDate.now());
        
 
    } 
    
    /* This method offers the alert box when click on update button. */
    /* Problem still exist: first time launch the app you will need click twice then the alert box could show up*/
    public void alertConfirmation() {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Notes save conformation");
    	
    	alert.setHeaderText("Are you sure want to save this note?");
    	alert.setContentText("Note:( " + notes.getText() + " )" +"\n" + "For date: " + picker.getValue());
    	
    	// option button in alert box
    	Optional<ButtonType> option = alert.showAndWait();
    	
    	if(option.get() == null) {
    		
    		this.saveConfirm = false;
    	
    	}else if(option.get() == ButtonType.OK) {
    		
    		this.saveConfirm = true;
    	
    	}else if(option.get() == ButtonType.CANCEL) {
    		
    		this.saveConfirm = false;
    		
    	}
    }
    
    /* This method will show the alertbox when click delete button */
    public void alertConfirmationD() {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Notes delete conformation");
    	
    	alert.setHeaderText("Are you sure want to delete this note?");
    	alert.setContentText("Note:( " + notes.getText() + " )" +"\n" + "For date: " + picker.getValue());
    	
    	// option button in alert box
    	Optional<ButtonType> option = alert.showAndWait();
    	
    	if(option.get() == null) {
    		
    		this.deleteConfirm = false;
    	
    	}else if(option.get() == ButtonType.OK) {
    		
    		this.deleteConfirm = true;
    	
    	}else if(option.get() == ButtonType.CANCEL) {
    		
    		this.deleteConfirm = false;
    		
    	}
    }
    
    /* This method set action on update button -> set note to map */
    @FXML
    public void updateNotes(ActionEvent event) {
        //data.put(picker.getValue(), notes.getText());
    	alertConfirmation();
		
		// If click OK in alert box. Note will be write to file.
		if(saveConfirm) {
			
			data.put(picker.getValue(), notes.getText());
			save();
			
		}
   
    }
    
    /* This method set action on delete button -> delete note from map */
    @FXML
    public void deleteNotes(ActionEvent event) {
    	
    	alertConfirmationD();
		
		if(deleteConfirm) {
			
			data.remove(picker.getValue());
			save();
			notes.setText(null);
			
		}
    	
    }
    
    /* This method defines self-custom exit when click X on windows */
    public void exit() {
        save();
        Platform.exit();
    }
    
    /* This method is write the map to the disk */
    public void save() {
        try (ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("event.data")))) {
            stream.writeObject(data);
            System.out.println("Saved!");
        } catch (Exception e) {
            System.out.println("Failed to save: " + e);
        }
    }
    

    @SuppressWarnings("unchecked")
	public void load() {
        Path file = Paths.get("event.data");

        if (Files.exists(file)) {
            try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(file))) {
                data = (Map<LocalDate, String>) stream.readObject();
                System.out.println("Loaded!");
            } catch (Exception e) {
                System.out.println("Failed to load: " + e);
            }
        }
    }
}