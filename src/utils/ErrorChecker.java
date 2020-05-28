package utils;

import controller.LoginScreenController;
import javafx.scene.control.Alert;
import model.Appointment;

public class ErrorChecker {

    /*
    This method will check if there are any overlapping appointments in the database
     */
//    public static Appointment overlappingAppointment(){
//        Appointment appointment;
//        String contact = LoginScreenController.validateUser.getUserName();
//    }

    /*
    This method will check for invalid customer data
     */
    public static boolean checkCustomerFields(String name, String address, String city, String country, String zipCode, String phone){

        StringBuilder errors = new StringBuilder();

        if(name.isEmpty())
            errors.append("*Fill up the name field.\n");

        if(address.isEmpty())
            errors.append("*Fill up the address field.\n");

        if(city.isEmpty())
            errors.append("*Please pick a city.\n");

        if(country.isEmpty())
            errors.append("*Please pick a city and country will be automatically selected.\n");

        if(zipCode.isEmpty())
            errors.append("*Zip code field cannot be empty and it can contain only numbers\n");

        if(phone.isEmpty())
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
