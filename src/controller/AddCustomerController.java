package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.City;
import model.Customer;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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



    @FXML
    void onActionCancelAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveAddCustomer(ActionEvent event) {

        String customerName = nameTxt.getText();
        String address = addressTxt.getText();
        String city = String.valueOf(cityComboBox.getSelectionModel().getSelectedItem());
        if(city == "Ney York".toLowerCase() || city == "Los Angels".toLowerCase() || city == "Phoenix".toLowerCase()){
            countryTxt.setText("USA");
        } else if(city == "Toronto".toLowerCase() || city == "Vancouver".toLowerCase()){
            countryTxt.setText("Canada");
        } else if(city == "Oslo".toLowerCase()){
            countryTxt.setText("Norway");
        } else
            countryTxt.setText("UK");
        String country = countryTxt.getText();
        String zipCode = zipCodeTxt.getText();
        String phone = phoneNumberTxt.getText();

        Customer newCustomer = new Customer(customerName, address, city, country, zipCode, phone);

    }

    @FXML
    private void onActionSetCity(ActionEvent actionEvent) {

    }

    // This method will get all the cities from database
    public ObservableList<City> getAllCities(){
        ObservableList<City> allCities = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT city FROM city";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                // getting data from result set
                City city = new City(resultSet.getString("city"));
                allCities.add(city);
            }
            return allCities;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cityComboBox.setItems(getAllCities());
        cityComboBox.setPromptText("Choose city");
        countryTxt.setDisable(true);
        countryTxt.setText("auto-populated");
    }
}
