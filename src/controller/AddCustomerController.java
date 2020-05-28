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
import model.City;
import model.Customer;
import utils.CustomerQuery;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;
import utils.ErrorChecker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private Label addCustomerLbl;
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
    private Button cancelAddCustomerBtn;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private TextField countryTxt;
    @FXML
    private ComboBox<City> cityComboBox;

    /*
    This method will cancel adding customer and navigate back to the Customer screen
     */
    @FXML
    void onActionCancelAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /*
    // This method will set Country name depending on which city has been chosen form cityComboBox
    */
    @FXML
    private void onActionSetCountry(ActionEvent actionEvent) {

        City city = cityComboBox.getSelectionModel().getSelectedItem();
        if (city != null) {
            countryTxt.setText(city.getCountryName());
        }
    }

    /*
    This method will save a newly created customer to the database
     */
    @FXML
    void onActionSaveAddCustomer(ActionEvent event) throws IOException {

        // This will get input from a user
        String customerName = nameTxt.getText();
        String address = addressTxt.getText();
        int city = cityComboBox.getSelectionModel().getSelectedItem().getCityId();
        String country = countryTxt.getText();
        String zipCode = zipCodeTxt.getText();
        String phone = phoneNumberTxt.getText();

        if (ErrorChecker.checkCustomerFields(customerName, address, cityComboBox.getSelectionModel().getSelectedItem().getCity(), zipCode, phone)) {

            CustomerQuery.addCustomerToDatabase(customerName, address, city, zipCode, phone);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cityComboBox.setItems(DatabaseQuery.getAllCities());
        cityComboBox.setPromptText("Choose city");
        countryTxt.setDisable(true);
        countryTxt.setText("auto-populated");
    }
}
