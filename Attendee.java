/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagementsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author WINDOWS 10
 */

public class Attendee  implements Initializable {
	

    
    
	@FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button addMed_btn;

    @FXML
    private Button pruchase_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private Label dashboard_availableMed;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Label dashboard_totalCustomers;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_brand;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private ComboBox<?> addMedicines_type;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private ImageView addMedicines_imageView;

    @FXML
    private Button addMedicines_importBtn;

    @FXML
    private Button addMedicines_addBtn;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button addMedicines_clearBtn;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private TableView<Event> addMedicines_tableView;
    
    @FXML
    private TableColumn<Event, String> addMedicines_col_medicineID;

    @FXML
    private TableColumn<Event, String> addMedicines_col_brand;

    @FXML
    private TableColumn<Event, String> addMedicines_col_productName;

    @FXML
    private TableColumn<Event, String> addMedicines_col_type;

    @FXML
    private TableColumn<Event, String> addMedicines_col_price;

    @FXML
    private TableColumn<Event, String> addMedicines_col_status;

    @FXML
    private TableColumn<Event, String> addMedicines_col_date;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<String> purchase_type;
    
    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private ComboBox<?> purchase_medicineID;

    @FXML
    private ComboBox<?> purchase_brand;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private Label purchase_total;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private TableView<AttendeeC> purchase_tableView;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_medicineId;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_brand;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_productName;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_type;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_qty;

    @FXML
    private TableColumn<AttendeeC, String> purchase_col_price;

//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Image image;
    

    
    
 
    private double totalP;
    public void purchaseAdd() {
        purchaseCustomerId();

        String sql = "INSERT INTO customer (customer_id, type, event_ID, brand, AvaliableTickets, quantity, price, date) "
                   + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (purchase_type.getSelectionModel().getSelectedItem() == null
                    || purchase_medicineID.getSelectionModel().getSelectedItem() == null
                    || purchase_brand.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, (String) purchase_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) purchase_medicineID.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) purchase_brand.getSelectionModel().getSelectedItem());

                // Set a constant value for productName if not selected
                String productName = (String) purchase_productName.getSelectionModel().getSelectedItem();
                if (productName == null) {
                    productName = "N/A"; // Use your desired constant value here
                }
                prepare.setString(5, productName);

                prepare.setString(6, String.valueOf(qty));

                String checkData = "SELECT price FROM event WHERE event_ID = '"
                        + purchase_medicineID.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
                double priceD = 0;
                if (result.next()) {
                    priceD = result.getDouble("price");
                }

                totalP = (priceD * qty);
                prepare.setString(7, String.valueOf(totalP));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();

                purchaseShowListData();
                purchaseDisplayTotal();
            }

            prepare = connect.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double totalPriceD;
    public void purchaseDisplayTotal() {
        // Reset totalPriceD to 0 at the beginning of the method
        totalPriceD = 0; // Reset the total
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '" + customerId + "'";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                totalPriceD = result.getDouble("SUM(price)");
            }
            
            // Update the purchase_total label with the new total
            purchase_total.setText("$" + String.valueOf(totalPriceD));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private double balance;
    private double amount;
    public void purchaseAmount(){
        
        if(purchase_amount.getText().isEmpty() || totalPriceD == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(purchase_amount.getText());
            if(totalPriceD > amount){
                purchase_amount.setText("");
            }else{
                balance = (amount - totalPriceD);
                
                purchase_balance.setText("$"+String.valueOf(balance));
            }
        }
        
    }
    
    public void purchasePay() {
        purchaseCustomerId();
        String sql = "INSERT INTO customer_info (customer_id, total, date) "
                + "VALUES(?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            // Check if totalPriceD is 0
            if (totalPriceD == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            } else {
                // Check if purchase_amount is less than totalPriceD
                double amountEntered = 0;
                if (!purchase_amount.getText().isEmpty()) {
                    amountEntered = Double.parseDouble(purchase_amount.getText());
                }

                if (amountEntered < totalPriceD) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Insufficient amount! Please enter an amount greater than or equal to the total.");
                    alert.showAndWait();
                    return; // Exit the method if the amount is insufficient
                }

                // Proceed with confirmation if the amount is sufficient
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalPriceD));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    // Reset fields after successful payment
                    purchase_amount.setText("");
                    purchase_balance.setText("$0.0");
                    totalPriceD = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private SpinnerValueFactory<Integer> spinner;
    public void purchaseShowValue(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        purchase_quantity.setValueFactory(spinner);
    }
    
    private int qty;
    public void purchaseQuantity(){
        qty = purchase_quantity.getValue();
    }
    
    public ObservableList<AttendeeC> purchaseListData(){
        purchaseCustomerId();
        
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";
        
        ObservableList<AttendeeC> listData = FXCollections.observableArrayList();
        
        connect = database.connectDb();
        
        try{
            AttendeeC customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                customerD = new AttendeeC(result.getInt("customer_id")
                        , result.getString("type"), result.getInt("event_ID")
                        , result.getString("brand"), result.getString("AvaliableTickets")
                        , result.getInt("quantity"), result.getDouble("price")
                        , result.getDate("date"));
                
                listData.add(customerD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    private ObservableList<AttendeeC> purchaseList;
    public void purchaseShowListData(){
        purchaseList = purchaseListData();
        
        purchase_col_medicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("AvaliableTickets"));
        purchase_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        purchase_col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        purchase_tableView.setItems(purchaseList);
        
    }
    
    private int customerId;
    public void purchaseCustomerId(){
        
        String sql = "SELECT customer_id FROM customer";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                customerId = result.getInt("customer_id");
            }
            int cID = 0;
            String checkData = "SELECT customer_id FROM customer_info";
            
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            
            while(result.next()){
                cID = result.getInt("customer_id");
            }
            
            if(customerId == 0){
                customerId+=1;
            }else if(cID == customerId){
                customerId = cID+1;
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void purchaseType(){
        
    	 String sql = "SELECT DISTINCT category FROM event"; // Use 'category' as the column name
         connect = database.connectDb();
         try {
             ObservableList<String> categoryList = FXCollections.observableArrayList();
             prepare = connect.prepareStatement(sql);
             result = prepare.executeQuery();
             while (result.next()) {
                 categoryList.add(result.getString("category")); // Correct column name
             }
             // Assuming addMedicines_type is your ComboBox
             purchase_type.setItems(categoryList);
         } catch (Exception e) {
             e.printStackTrace();
         }
        
    }
    
    public void purchaseMedicineId(){
        
        String sql = "SELECT * FROM event WHERE type = '"
                +purchase_type.getSelectionModel().getSelectedItem()+"'";
        
        connect = database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("event_ID"));
            }
            purchase_medicineID.setItems(listData);
            
            purchaseBrand();
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void purchaseBrand(){
        
        String sql = "SELECT * FROM event WHERE event_ID = '"
                +purchase_medicineID.getSelectionModel().getSelectedItem()+"'";
        
        connect = database.connectDb();
        
        try{
            ObservableList listData = FXCollections.observableArrayList();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                listData.add(result.getString("brand"));
            }
            purchase_brand.setItems(listData);
            
          
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
   
    
    
	

    
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(false);
            
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            addMed_btn.setStyle("-fx-background-color:transparent");
            pruchase_btn.setStyle("-fx-background-color:transparent");
            
  
            
        }else if(event.getSource() == addMed_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);
            
            addMed_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
 
        }else if(event.getSource() == pruchase_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(true);
            
            pruchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            addMed_btn.setStyle("-fx-background-color:transparent");
            
            purchaseType();
            purchaseMedicineId();
            purchaseBrand();
            purchaseShowListData();
            purchaseShowValue();
            purchaseDisplayTotal();
            
        }
        
    }
    
    public void displayUsername(){
        String user = getData.username;
                        // BIG LETTER THE FIRST LETTER THEN THE REST ARE SMALL LETTER
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
        
    }
    
    private double x = 0;
    private double y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE THE DASHBOARD FORM
                logout.getScene().getWindow().hide();
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
      



        purchaseType();
        purchaseMedicineId();
        purchaseBrand();
        purchase_type.setOnAction(e -> purchaseMedicineId());
        purchase_medicineID.setOnAction(e -> purchaseBrand());
        purchaseShowListData();
        purchaseShowValue();
        purchaseDisplayTotal();
        
    }

} 