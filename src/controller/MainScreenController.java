package controller;

import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utils.AppointmentQuery;
import utils.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button customersBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button logOffBtn;

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
    void handleLogOff(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to logoff?");
        alert.showAndWait();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleReportsBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//            Appointment appointment = AppointmentQuery.appointmentInFifteenMinutes();
//            if(appointment != null){
//                String textToDisplay = String.format("You have an appointment with %s at %s", appointment.getCustomerName(), appointment.getStart());
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Appointment remainder");
//                alert.setContentText(textToDisplay);
//                alert.showAndWait();
//            }

    }
}
