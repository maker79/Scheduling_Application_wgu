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
    private static int indexOfSelectedCustomer;

    @FXML
    void handleAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // This method will delete a customer from the database
    @FXML
    void handleDeleteCustomer(ActionEvent event) throws SQLException {

        Customer selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Please confirm that you want to delete selected customer!");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                CustomerQuery.deleteCustomerFromDatabase(selectedCustomer);
                customersTbl.setItems(CustomerQuery.getAllCustomers());
            }
        } else{
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("Please select a customer from the list!");
            alert1.showAndWait();
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

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        indexOfSelectedCustomer = CustomerQuery.getAllCustomers().indexOf(selectedCustomer);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent modifyCustomerStage = loader.load();
            Scene modifyCustomerScene = new Scene(modifyCustomerStage);
            // Access the modify customer controller and call a method
            ModifyCustomerController controller = loader.getController();
            controller.showCustomerToModify(customersTbl.getSelectionModel().getSelectedItem());

            Stage applicationStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            applicationStage.setScene(modifyCustomerScene);
            applicationStage.show();

        } catch (NullPointerException e){
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
