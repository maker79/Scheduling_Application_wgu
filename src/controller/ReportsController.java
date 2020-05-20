package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private RadioButton appointmentsByMonth;
    @FXML
    private ToggleGroup reportsToggleGroup;
    @FXML
    private RadioButton eachConsultantSchedule;
    @FXML
    private RadioButton numOfCustomers;
    @FXML
    private TextArea displayTextArea;
    @FXML
    private Button backButton;
    @FXML
    private Label displayLabel;

    @FXML
    void onActionHandleBackToMenu(ActionEvent event) {

    }

    @FXML
    void showNumAppointmentTypesByMonth(ActionEvent event) {

    }

    @FXML
    void showNumberOfCustomers(ActionEvent event) {

    }

    @FXML
    void showScheduleForEachConsultant(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
