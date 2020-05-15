package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import utils.DatabaseQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addAppointmentTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Button cancelAddAppointmentBtn;
    @FXML
    private Button saveAddAppointmentBtn;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox startComboBox;
    @FXML
    private ComboBox endComboBox;

    private Customer selectedCustomer;
    private int id;
    private final ObservableList<String> APPOINTMENT_TYPES = FXCollections.observableArrayList("Presentation", "Scrum", "Consultation");

    @FXML
    void handleCancelAddingAppointment(ActionEvent event) throws IOException {

        customerComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
        startComboBox.getSelectionModel().clearSelection();
        endComboBox.getSelectionModel().clearSelection();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleSaveAddedAppointment(ActionEvent event) {
        // Getting input from user
        int customer = customerComboBox.getSelectionModel().getSelectedIndex();
        String title = titleTxt.getText();

    }

    /*
    The fallowing method accepts a customer from the customer table to initialize a view
    in add appointment screen to add appointment if an existing customer is selected
     */
    public void showSelectedCustomer(Customer customer){
        selectedCustomer = customer;
        id = customer.getCustomerId();
        customerComboBox.setValue(customer.getCustomerName());
        customerComboBox.setItems(DatabaseQuery.getAllCustomers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(17, 0);

        while(start.isBefore(end.plusSeconds(1))){
            startComboBox.getItems().add(start);
            endComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }

        customerComboBox.setItems(DatabaseQuery.getAllCustomers());
        customerComboBox.setPromptText("Choose customer");
        typeComboBox.setItems(APPOINTMENT_TYPES);
        startComboBox.getSelectionModel().select(LocalTime.of(8,0));
        startComboBox.setVisibleRowCount(6);
        endComboBox.getSelectionModel().select(LocalTime.of(8, 30));
        endComboBox.setVisibleRowCount(6);
    }
}
