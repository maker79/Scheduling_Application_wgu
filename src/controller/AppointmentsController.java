package controller;

import com.mysql.cj.xdevapi.SqlStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Customer;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customersTbl;
    @FXML
    private TableColumn<Customer, Integer> customerIdTblColumn;
    @FXML
    private TableColumn<Customer, String> customerNameTblColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneNumTblColumn;
    @FXML
    private TableView<Appointment> appointmentsTbl;
    @FXML
    private TableColumn<Customer, String> appCustomerTblColumn;
    @FXML
    private TableColumn<Appointment, String> appTitleTblColumn;
    @FXML
    private TableColumn<Appointment, String> appTypeTblColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartTblColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appEndTblColumn;
    @FXML
    private RadioButton currentMonthBtn;
    @FXML
    private ToggleGroup appointmentsBtns;
    @FXML
    private RadioButton currentWeekBtn;
    @FXML
    private RadioButton allAppointmentsBtn;
    @FXML
    private Button deleteAppointmentBtn;
    @FXML
    private Button modifyAppointmentBtn;
    @FXML
    private Button addAppointmentBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button clearBtn;


    // Customer side of the screen

    @FXML
    private void handleClearCustomerTable(ActionEvent actionEvent) {
        customersTbl.getSelectionModel().clearSelection();
    }

    // Appointments side of the screen

    @FXML
    private void onActionCurrentMonthBtnSelected(ActionEvent actionEvent) {
    }

    @FXML
    private void onActionCurrentWeekBtnSelected(ActionEvent actionEvent) {
    }

    @FXML
    private void onActionAllBtnSelected(ActionEvent actionEvent) {

            appointmentsTbl.setItems(DatabaseQuery.getAllAppointments());
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

    }

    @FXML
    void handleAddNewAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void handleGoBackToMainScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleModifyAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customersTbl.setItems(DatabaseQuery.getAllCustomers());
        customerIdTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneNumTblColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));


    }

}
