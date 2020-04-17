package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyAppointmentController {

    Stage stage;
    Parent scene;
    
    @FXML
    private TextField titleTxt;
    @FXML
    private ChoiceBox<?> typeChoiceBox;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Button cancelModAppointmentBtn;
    @FXML
    private Button saveModAppointmentBtn;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox startComboBox;
    @FXML
    private ComboBox endComboBox;

    @FXML
    void handleCancelModAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleSaveModAppointment(ActionEvent event) {

    }

}
