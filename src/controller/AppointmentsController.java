package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsController {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<?> customersTbl;

    @FXML
    private TableColumn<?, ?> customerIdTblColumn;

    @FXML
    private TableColumn<?, ?> customerNameTblColumn;

    @FXML
    private TableColumn<?, ?> customerAddressTblColumn;

    @FXML
    private TableColumn<?, ?> customerPhoneNumTblColumn;

    @FXML
    private TableView<?> appointmentsTbl;

    @FXML
    private TableColumn<?, ?> appCustomerTblColumn;

    @FXML
    private TableColumn<?, ?> appTitleTblColumn;

    @FXML
    private TableColumn<?, ?> appTypeTblColumn;

    @FXML
    private TableColumn<?, ?> appStartTblColumn;

    @FXML
    private TableColumn<?, ?> appEndTblColumn;

    @FXML
    private RadioButton upcomingMonthBtn;

    @FXML
    private ToggleGroup appointmentsBtns;

    @FXML
    private RadioButton upcomingWeekBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button backBtn;

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
}
