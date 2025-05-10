package pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class FXMLDocumentController implements Initializable {

    @FXML
    private Button SignUp;

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> title;

    @FXML
    private TextField username;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private double x= 0;
    private double y= 0;



    private String[] rolesListT = {"Organizer", "Attendee","Admin"
	  };

    public void addrolesList() {
        List<String> listT = new ArrayList<>();

        for (String data : rolesListT) {
            listT.add(data);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listT);
        title.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addrolesList();
    }

    @FXML
    public void goToRegister(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            // Optional: Add drag functionality like you did in login
            root.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent e) -> {
                stage.setX(e.getScreenX() - x);
                stage.setY(e.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            // Close current window (optional)
            SignUp.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
                        root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
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
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
