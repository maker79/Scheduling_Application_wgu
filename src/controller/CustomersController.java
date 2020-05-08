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

    // This method will delete a customer from the database
    @FXML
    void handleDeleteCustomer(ActionEvent event) {
        int selectedItem = customersTbl.getSelectionModel().getSelectedIndex();
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String deleteStatement = "DELETE FROM customer WHERE customerId = ?";
            DatabaseQuery.setPreparedStatement(connection, deleteStatement);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setString(1, String.valueOf(selectedItem));
            preparedStatement.execute();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Customers table
        customersTbl.setItems(DatabaseQuery.getAllCustomers());
        customerIdTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTblColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberTblColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }
}
