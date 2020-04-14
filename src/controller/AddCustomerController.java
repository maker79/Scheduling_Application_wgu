package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {

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
    private ComboBox<?> countryChoiceBox;

    @FXML
    private Button cancelAddCusotmerBtn;

    @FXML
    private Button saveCustomerBtn;

    @FXML
    void onActionCancelAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveAddCustomer(ActionEvent event) {

    }
}
