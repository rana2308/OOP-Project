


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author WINDOWS 10
 */
public class register implements Initializable {
	
	

	@FXML
    private ComboBox<?> Gender;

    @FXML
    private Label Title;

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<?> title;

    @FXML
    private TextField username;
    
    @FXML
    private ComboBox<String> genderComboBox;
    
//    DATABASE TOOLS
    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;
    
    private double x= 0;
    private double y= 0;
    


    
    public void loginAdmin() {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ? AND title = ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            prepare.setString(3, (String) title.getValue()); // Make sure this field exists in your UI

            result = prepare.executeQuery();

            if (username.getText().isEmpty() || password.getText().isEmpty() || ((String) title.getValue()).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    getData.username = username.getText(); // Save logged-in username

                    String userTitle = result.getString("title"); // Get title from DB

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    // Hide login window
                    loginBtn.getScene().getWindow().hide();

                    // Load corresponding FXML
                    Parent root = null;
                    if ("Organizer".equalsIgnoreCase(userTitle)) {
                        root = FXMLLoader.load(getClass().getResource("Organizer.fxml"));
                    } else if ("Admin".equalsIgnoreCase(userTitle) ) {
                        root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    }else if ("Attendee".equalsIgnoreCase(userTitle) ) {
                            root = FXMLLoader.load(getClass().getResource("Attendee.fxml"));
                        
                    } else {
                        Alert unknownAlert = new Alert(Alert.AlertType.ERROR);
                        unknownAlert.setTitle("Login Failed");
                        unknownAlert.setHeaderText(null);
                        unknownAlert.setContentText("Unknown title: " + userTitle);
                        unknownAlert.showAndWait();
                        return;
                    }

                    // Create and show new stage
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    // Allow dragging the undecorated window
                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username / Password / Title");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private String[] genderListT = {"Male", "Female"
    		  };
    
	  public void addgenderListT(){
	  List<String> listT = new ArrayList<>();
	  
	  for(String data: genderListT){ listT.add(data); }
	  
	  ObservableList listData = FXCollections.observableArrayList(listT);
	  Gender.setItems(listData);
	  
	  }


	  private String[] rolesListT = {"Organizer", "Attendee","Admin"
	  };

	  public void addrolesList(){
		  List<String> listT = new ArrayList<>();

		  for(String data: rolesListT){ listT.add(data); }

		  ObservableList listData = FXCollections.observableArrayList(listT);
		  title.setItems(listData);

}
	  
	  
	  
	  public void registerUser(ActionEvent event) {
		    if (username.getText().isEmpty() || password.getText().isEmpty() || 
		        Gender.getValue() == null || title.getValue() == null ) {
		        
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.setContentText("Please fill in all fields.");
		        alert.showAndWait();
		        return;
		    }

		    String sql = "INSERT INTO admin (username, password, Gender, title) VALUES (?, ?, ?, ?)";

		    try {
		        connect = database.connectDb();
		        prepare = connect.prepareStatement(sql);
		        prepare.setString(1, username.getText());
		        prepare.setString(2, password.getText());
		        prepare.setString(3, (String) Gender.getValue());
		        prepare.setString(4, (String) title.getValue());
		     

		        int rowsInserted = prepare.executeUpdate();

		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Success");
		        alert.setHeaderText(null);
		        alert.setContentText("Registration successful!");
		        alert.showAndWait();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	  
	  
 public void close(){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	addgenderListT();
    	addrolesList();
    }    
    
}