package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Address;
import model.Customer;
import utils.CustomerQuery;
import utils.DatabaseQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    private static int indexOfSelectedCustomer;
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
    private Customer selectedCustomer;

    /*
    This method will switch to Add Customer screen
     */
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

        Customer selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Please confirm that you want to delete selected customer!");
            // lambda expression to improve readability and efficiency while deleting customer from database
            alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    try {
                        CustomerQuery.deleteCustomerFromDatabase(selectedCustomer);
                        customersTbl.setItems(CustomerQuery.getAllCustomers());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }));

        }
    }

    /*
    This method will navigate user back to the Main Screen
     */
    @FXML
    void handleExitToMainScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /*
    This method will get a customer that is selected in the customer table and
    open up Modify customer screen
     */
    @FXML
    void handleModifyCustomer(ActionEvent event) throws IOException {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        indexOfSelectedCustomer = CustomerQuery.getAllCustomers().indexOf(selectedCustomer);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent modifyCustomerStage = loader.load();
            Scene modifyCustomerScene = new Scene(modifyCustomerStage);
            // Access the modify customer controller and call a method
            ModifyCustomerController controller = loader.getController();
            controller.showCustomerToModify(customersTbl.getSelectionModel().getSelectedItem());

            Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            applicationStage.setScene(modifyCustomerScene);
            applicationStage.show();

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a customer to modify!");
            alert.showAndWait();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Customers table
        customersTbl.setItems(CustomerQuery.getAllCustomers());
        customerIdTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTblColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberTblColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }
}
