package utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.time.LocalDateTime;

public class ErrorChecker {

    /*
    This method will check if there are any overlapping appointments in the database
     */
    public static boolean overlappingAppointment(LocalDateTime newAppStart, LocalDateTime newAppEnd) {

        ObservableList<Appointment> appList = AppointmentQuery.getAllAppointments();
        for (Appointment apOverlap : appList) {

            LocalDateTime start = apOverlap.getStart();
            LocalDateTime end = apOverlap.getEnd();
            try {
                boolean overlap = false;
                if ((start.isEqual(newAppStart) || start.isAfter(newAppStart)) && (start.isBefore(newAppEnd))) {
                    overlap = true;
                }
                if (end.isAfter(newAppStart) && (end.isEqual(newAppEnd) || end.isBefore(newAppEnd))) {
                    overlap = true;
                }
                if ((start.isBefore(newAppStart) || start.isEqual(newAppStart)) && (end.isEqual(newAppEnd) || end.isAfter(newAppEnd))) {
                    overlap = true;
                }
                if (overlap) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setContentText("Overlapping appointment times. Please choose another time.");
                    alert.showAndWait();

                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return true;

    }

    /*
    This method will check if there are any overlapping appointments in the database while saving modified appointment
     */
    public static boolean overlappingAppointmentModified(int id, LocalDateTime newAppStart, LocalDateTime newAppEnd) {

        ObservableList<Appointment> appList = AppointmentQuery.getAllAppointments();
        for (Appointment apOverlap : appList) {
            if(apOverlap.getAppointmentId() == id)
                break;
            LocalDateTime start = apOverlap.getStart();
            LocalDateTime end = apOverlap.getEnd();
            try {
                boolean overlap = false;
                if ((start.isEqual(newAppStart) || start.isAfter(newAppStart)) && (start.isBefore(newAppEnd))) {
                    overlap = true;
                }
                if (end.isAfter(newAppStart) && (end.isEqual(newAppEnd) || end.isBefore(newAppEnd))) {
                    overlap = true;
                }
                if ((start.isBefore(newAppStart) || start.isEqual(newAppStart)) && (end.isEqual(newAppEnd) || end.isAfter(newAppEnd))) {
                    overlap = true;
                }
                if (overlap) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setContentText("Overlapping appointment times. Please choose another time.");
                    alert.showAndWait();

                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return true;

    }

    /*
    This method will check for invalid customer data
     */
    public static boolean checkCustomerFields(String name, String address, String city, String zipCode, String phone) {

        StringBuilder errors = new StringBuilder();

        if (name.isEmpty())
            errors.append("*Fill up the name field.\n");

        if (address.isEmpty())
            errors.append("*Fill up the address field.\n");

        if (city.isEmpty())
            errors.append("*Please pick a city.\n");

        if (zipCode.isEmpty() || (!zipCode.matches("[0-9]+")))
            errors.append("*Zip code field cannot be empty or contain characters other than numeric\n");

        if (phone.isEmpty())
            errors.append("*Phone number field cannot be empty\n");

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Required field(s) empty or contain(s) invalid data!");
            alert.setContentText(errors.toString());
            alert.showAndWait();

            return false;
        }
        return true;
    }
}
