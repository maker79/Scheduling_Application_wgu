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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.CubicCurveTo;
import javafx.stage.Stage;
import model.Address;
import model.Customer;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customersTbl;

    @FXML
    private TableColumn<Customer, Integer> customerIdTblColumn;

    @FXML
    private TableColumn<Customer, String> nameTblColumn;

    @FXML
    private TableColumn<Address, String> addressTblColumn;

    @FXML
    private TableColumn<Address, String> phoneNumberTblColumn;

    @FXML
    void handleAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void handleDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void handleExitToMainScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void handleModifyCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    // This method will get all the customers that are in our database
    public ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allOfTheCustomers = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT customer.customerId, customer.customerName, address.address, address.phone" +
                    " FROM customer INNER JOIN address ON customer.addressId = address.addressId";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                // getting data from result set
                Customer customer = new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("customerName"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );
                allOfTheCustomers.add(customer);

                // to check that customer is being retrieved before populate the Customer table
                System.out.println("Customer: " + resultSet.getInt("customerId") +
                                    " | " + resultSet.getString("customerName") +
                                    " | " + resultSet.getString("address") +
                                    " | " + resultSet.getString("phone"));
            }
            preparedStatement.close();
            return allOfTheCustomers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Customers table
        customersTbl.setItems(getAllCustomers());
        customerIdTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTblColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberTblColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }
}
