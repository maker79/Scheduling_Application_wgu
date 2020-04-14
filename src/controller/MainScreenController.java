package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    Stage stage;
    Parent scene;

    @FXML
    private Button customersBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button logFilesBtn;

    @FXML
    void handleAppointmentsBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleCustomersBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleLogFilesBtn(ActionEvent event) {

    }

    @FXML
    void handleReportsBtn(ActionEvent event) {

    }

}
