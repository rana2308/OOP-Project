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
public class Admin implements Initializable {



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
    private ComboBox<?> purchase_type;
    
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
    
    public void homeChart(){
        dashboard_chart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM customer_info"
                + " GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        
        connect = database.connectDb();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            dashboard_chart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeAM(){
        
        String sql = "SELECT COUNT(id) FROM medicine WHERE status = 'Available'";
        
        connect = database.connectDb();
        int countAM = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAM = result.getInt("COUNT(id)");
            }
            
            dashboard_availableMed.setText(String.valueOf(countAM));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeTI(){
        String sql = "SELECT SUM(total) FROM customer_info";
        
        connect = database.connectDb();
        double totalDisplay = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                totalDisplay = result.getDouble("SUM(total)");
            }
            
            dashboard_totalIncome.setText("$" + String.valueOf(totalDisplay));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    
    	
    
    
    public void homeTC(){
        
        String sql = "SELECT COUNT(id) FROM customer_info";
        
        connect = database.connectDb();
        int countTC = 0;
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            
            dashboard_totalCustomers.setText(String.valueOf(countTC));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    
    
    public void addMedicinesAdd(){
        
        String sql = "INSERT INTO event ( category) "
                + "VALUES(?)";
        
        connect = database.connectDb();
        
        try{
            
            Alert alert;
            
            if(
                  addMedicines_brand.getText().isEmpty()
                    ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else{
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_brand.getText());
                  

                
                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    addMedicineShowListData();
                    addMedicineReset();
                    
                }
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void addMedicineUpdate(){
        
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");
        
        String sql = "UPDATE event SET category = '"
                +addMedicines_brand.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if(addMedicines_brand.getText().isEmpty()
                    ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    
                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    AttendeeC e = new AttendeeC();
    
    public void addMedicineDelete(){
        
        String sql = "DELETE FROM event WHERE event_ID = '"+addMedicines_medicineID.getText()+"'";
        
        connect = database.connectDb();
        
        try{
            Alert alert;
            
            if( addMedicines_brand.getText().isEmpty()
                  ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void addMedicineReset(){
    	
    	e.addMedicineReset();
  
        
    }
    
    
    
    
    private String[] addMedicineListT = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};

    
    
    
    private String[] addMedicineStatus = {"Available", "Not Available"};
    public void addMedicineListStatus(){
        List<String> listS = new ArrayList<>();
        
        for(String data: addMedicineStatus){
            listS.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listS);

    }
    
    public void addMedicineImportImage(){
        
        FileChooser open = new FileChooser();
        open.setTitle("Import Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));
        
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        
        if(file != null){
            image = new Image(file.toURI().toString(), 118, 147, false, true);
            
            addMedicines_imageView.setImage(image);
            
            getData.path = file.getAbsolutePath();
        }
        
    }
    
    public ObservableList<Event> addMedicinesListData(){
        
        String sql = "SELECT * FROM event";
        
        ObservableList<Event> listData = FXCollections.observableArrayList();
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            Event medData;
            
            while(result.next()){
                medData = new Event(result.getString("category")
                       );
                
                listData.add(medData);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    
    
    
    private ObservableList<Event> addMedicineList;
    public void addMedicineShowListData(){
        addMedicineList = addMedicinesListData();
        

        addMedicines_col_brand.setCellValueFactory(new PropertyValueFactory<>("category"));
      
        
        addMedicines_tableView.setItems(addMedicineList);
        
    }
    
    public void addMedicineSearch(){
        
        FilteredList<Event> filter = new FilteredList<>(addMedicineList, e-> true);
        
        addMedicines_search.textProperty().addListener((Observable, oldValue, newValue) ->{
            
            filter.setPredicate(predicateMedicineData ->{
                
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateMedicineData.getEventID().toString().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getEventName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(String.valueOf(predicateMedicineData.getProductName()).contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getCategory().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getStatus().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getPrice().toString().contains(searchKey)){
                    return true;
                }else if(predicateMedicineData.getDate().toString().contains(searchKey)){
                    return true;
                }else return false;
            });
        });
        
        SortedList<Event> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(addMedicines_tableView.comparatorProperty());
        addMedicines_tableView.setItems(sortList);
        
    }
    
    public void addMedicineSelect(){
        Event medData = addMedicines_tableView.getSelectionModel().getSelectedItem();
        int num = addMedicines_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < - 1){return;}
        
        addMedicines_medicineID.setText(String.valueOf(medData.getEventID()));
        addMedicines_brand.setText(medData.getEventName());
        addMedicines_productName.setText(String.valueOf(medData.getProductName()));

        addMedicines_price.setText(String.valueOf(medData.getPrice()));
        
        String uri = "file:" + medData.getImage();
        
        image = new Image(uri, 118, 147, false, true);
        addMedicines_imageView.setImage(image);
        
        getData.path = medData.getImage();
        
    }
    
   
    
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(false);
            
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            addMed_btn.setStyle("-fx-background-color:transparent");
            pruchase_btn.setStyle("-fx-background-color:transparent");
            
            homeChart();
            homeAM();
            homeTI();
            homeTC();
            
        }else if(event.getSource() == addMed_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);
            
            addMed_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            pruchase_btn.setStyle("-fx-background-color:transparent");
            
            addMedicineShowListData();
            addMedicineListStatus();
            addMedicineSearch();
            
        }else if(event.getSource() == pruchase_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(true);
            
            pruchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            addMed_btn.setStyle("-fx-background-color:transparent");
            
   
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
  
 
        homeAM();
        homeTI();
        homeTC();
        homeChart();
        
        addMedicineShowListData();


        
    }

}