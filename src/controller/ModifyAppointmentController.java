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
import model.Appointment;
import model.Customer;
import utils.AppointmentQuery;
import utils.DatabaseQuery;
import utils.ErrorChecker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    private final ObservableList<String> APPOINTMENT_TYPES = FXCollections.observableArrayList("Presentation", "Scrum", "Consultation");
    Stage stage;
    Parent scene;
    @FXML
    private TextField titleTxt;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Button cancelModAppointmentBtn;
    @FXML
    private Button saveModAppointmentBtn;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox<LocalTime> startComboBox;
    @FXML
    private ComboBox<LocalTime> endComboBox;
    private Appointment selectedAppointment;
    private int id;
    private int customerId;

    @FXML
    void handleCancelModAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleSaveModAppointment(ActionEvent event) throws IOException {

        Appointment appointment = selectedAppointment;
        id = appointment.getAppointmentId();
        String title = titleTxt.getText();
        String type = (String) typeComboBox.getValue();
        LocalDate date = dateDatePicker.getValue();
        LocalTime start = (LocalTime) startComboBox.getValue();
        LocalTime end = (LocalTime) endComboBox.getValue();
        LocalDateTime localDateTimeStart = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                start.getHour(), start.getMinute());
        LocalDateTime localDateTimeEnd = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                end.getHour(), end.getMinute());

//        if(ErrorChecker.overlappingAppointment(localDateTimeStart, localDateTimeEnd)){

            AppointmentQuery.modifyExistingAppointment(title, type, localDateTimeStart, localDateTimeEnd, id);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

//        }

    }

      /*
    The fallowing method accepts a customer from the customer table to initialize a view
    in modify customer screen
     */
    public void showAppointmentToModify(Appointment appointment){
        selectedAppointment = appointment;
        id = appointment.getAppointmentId();
        customerComboBox.setValue(appointment.getCustomerName());
        titleTxt.setText(appointment.getTitle());
        typeComboBox.setValue(appointment.getType());
        dateDatePicker.setValue(appointment.getStart().toLocalDate());
        startComboBox.setValue(appointment.getStart().toLocalTime());
        endComboBox.setValue(appointment.getEnd().toLocalTime());
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
