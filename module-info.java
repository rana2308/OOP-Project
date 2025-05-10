module EventManagmentSystem {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	
	opens pharmacymanagementsystem to javafx.graphics, javafx.fxml;
}
