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
import model.User;
import utils.AppointmentQuery;
import utils.CustomerQuery;
import utils.DatabaseQuery;
import utils.ErrorChecker;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<LocalTime> startComboBox;
    @FXML
    private ComboBox<LocalTime> endComboBox;

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
    void handleSaveAddedAppointment(ActionEvent event) throws IOException {
        // Getting input from user
        Customer customer = (Customer) customerComboBox.getSelectionModel().getSelectedItem();
        String title = titleTxt.getText();
        String type = (String) typeComboBox.getSelectionModel().getSelectedItem();
        LocalDate localDate = dateDatePicker.getValue();
        LocalTime start = (LocalTime) startComboBox.getValue();
        LocalTime end = (LocalTime) endComboBox.getValue();
        LocalDateTime localDateTimeStart = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(),
                start.getHour(), start.getMinute());
        LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(),
                end.getHour(), end.getMinute());

        if(ErrorChecker.overlappingAppointment(localDateTimeStart, localDateTimeEnd)){

            AppointmentQuery.addNewAppointment(customer.getCustomerId(), title, LoginScreenController.validateUser, type, localDateTimeStart, localDateTimeEnd);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /*
    The fallowing method accepts a customer from the customer table to initialize a view
    in add appointment screen to add appointment if an existing customer is selected
     */
    public void showSelectedCustomer(Customer customer){

        selectedCustomer = customer;
        id = customer.getCustomerId();
        customerComboBox.setItems(CustomerQuery.getAllCustomers());
        for(Customer c : customerComboBox.getItems()){
            if(c.getCustomerId() == id){
                customerComboBox.setValue(c);
                break;
            }
        }
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

        customerComboBox.setItems(CustomerQuery.getAllCustomers());
        customerComboBox.setPromptText("Choose customer");
        typeComboBox.setItems(APPOINTMENT_TYPES);
        startComboBox.getSelectionModel().select(LocalTime.of(8,0));
        startComboBox.setVisibleRowCount(6);
        endComboBox.getSelectionModel().select(LocalTime.of(8, 30));
        endComboBox.setVisibleRowCount(6);

        dateDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
                if(date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #b1c3c4;");
                }
            }
        });
    }
}
