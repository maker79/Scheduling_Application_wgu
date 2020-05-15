package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import utils.DatabaseQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ModifyCustomerController {

    Stage stage;
    Parent scene;

    @FXML
    private Label modifyCustomerLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label countryLbl;

    @FXML
    private Label cityLbl;

    @FXML
    private Label addressLbl;

    @FXML
    private Label zipCodeLbl;

    @FXML
    private Label phoneNumberLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField zipCodeTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private ComboBox<?> countryChoiceBox;

    @FXML
    private Button cancelModifyCustomerBtn;

    @FXML
    private Button saveModifyCustomerBtn;
    @FXML
    private TextField countryTxt;
    @FXML
    private ComboBox cityComboBox;
    @FXML
    private Label addCustomerLbl;

    private Customer selectedCustomer;

    private int id;
    private int addressId;

    @FXML
    void onActionCancelModifyCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSaveModifyCustomer(ActionEvent event) {
        try{
            // This will get input from a user
            String customerName = nameTxt.getText();
            String address = addressTxt.getText();
            int city = cityComboBox.getSelectionModel().getSelectedIndex();
            String zipCode = zipCodeTxt.getText();
            String phone = phoneNumberTxt.getText();

            DatabaseQuery.modifyExistingCustomer(id, customerName, address, city, zipCode, phone, addressId);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirm that you want to modify this customer.");
            Optional<ButtonType> choice = alert.showAndWait();

            if (choice.get() == ButtonType.OK) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onActionSetCountry(ActionEvent actionEvent) {

        String currentCity = cityComboBox.getSelectionModel().getSelectedItem().toString();
        if (currentCity.equals("New York") || currentCity.equals("Los Angeles") || currentCity.equals("Phoenix")) {
            countryTxt.setText("USA");
        } else if (currentCity.equals("Toronto") || currentCity.equals("Vancouver")) {
            countryTxt.setText("Canada");
        } else if (currentCity.equals("Oslo")) {
            countryTxt.setText("Norway");
        } else {
            countryTxt.setText("UK");
        }
    }

    /*
    The fallowing method accepts a customer from the customer table to initialize a view
    in modify customer screen
     */
    public void showCustomerToModify(Customer customer){
        selectedCustomer = customer;
        id = customer.getCustomerId(); // also need address id here
        addressId = customer.getAddressId();
        nameTxt.setText(selectedCustomer.getCustomerName());
        addressTxt.setText(selectedCustomer.getAddress());
        cityComboBox.setItems(DatabaseQuery.getAllCities());
        // need a loop after this to match cityId
//        countryTxt.setText(selectedCustomer.getCountry());
        countryTxt.setDisable(true);
        zipCodeTxt.setText(selectedCustomer.getPostalCode());
        phoneNumberTxt.setText(selectedCustomer.getPhone());

    }
}
