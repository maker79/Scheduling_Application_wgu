package utils;

import javafx.scene.control.Alert;
import model.Appointment;

import java.time.LocalDateTime;

public class ErrorChecker {

    /*
    This method will check if there are any overlapping appointments in the database
     */
    public static boolean overlappingAppointment(LocalDateTime newAppStart, LocalDateTime newAppEnd){

            for(Appointment apOverlap : AppointmentQuery.getAllAppointments()) {

                LocalDateTime start = apOverlap.getStart();
                LocalDateTime end = apOverlap.getEnd();
            try {
                if (newAppStart.isBefore(end) || (newAppEnd.isAfter(start))) {

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
    public static boolean checkCustomerFields(String name, String address, String city, String zipCode, String phone){

        StringBuilder errors = new StringBuilder();

        if(name.isEmpty())
            errors.append("*Fill up the name field.\n");

        if(address.isEmpty())
            errors.append("*Fill up the address field.\n");

        if(city.isEmpty())
            errors.append("*Please pick a city.\n");

        if(zipCode.isEmpty() || !(zipCode.matches("[0-9]")))
            errors.append("*Zip code field cannot be empty and it can contain only numbers\n");

        if(phone.isEmpty() || !(phone.matches("[0-9]\\+\\-")))
            errors.append("*Phone number field cannot be empty an it can contain only numbers\n");

        if(errors.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Required field(s) empty or contain(s) invalid data!");
            alert.setContentText(errors.toString());
            alert.showAndWait();

            return false;
        }
        return true;
    }
}
