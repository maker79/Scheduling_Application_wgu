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
import model.Appointment;
import model.Customer;
import utils.AppointmentQuery;
import utils.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customersTbl;
    @FXML
    private TableColumn<Customer, Integer> customerIdTblColumn;
    @FXML
    private TableColumn<Customer, String> customerNameTblColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneNumTblColumn;
    @FXML
    private TableView<Appointment> appointmentsTbl;
    @FXML
    private TableColumn<Appointment, String> appCustomerTblColumn;
    @FXML
    private TableColumn<Appointment, String> appTitleTblColumn;
    @FXML
    private TableColumn<Appointment, String> appTypeTblColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartTblColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appEndTblColumn;
    @FXML
    private RadioButton currentMonthBtn;
    @FXML
    private ToggleGroup appointmentsBtns;
    @FXML
    private RadioButton currentWeekBtn;
    @FXML
    private RadioButton allAppointmentsBtn;
    @FXML
    private Button deleteAppointmentBtn;
    @FXML
    private Button modifyAppointmentBtn;
    @FXML
    private Button addAppointmentBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button showCustomerBtn;

    private Customer selectedCustomer;
    private static int indexOfSelectedCustomer;
    private Appointment selectedAppointment;
    private int indexOfSelectedAppointment;

    // ********** Customer side of the screen *******************
    /*
    This method will clear customer selection and set all appointments to the appointment table
     */
    @FXML
    private void handleClearCustomerTable(ActionEvent actionEvent) {

        customersTbl.getSelectionModel().clearSelection();
        appointmentsTbl.setItems(AppointmentQuery.getAllAppointments());
        appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        allAppointmentsBtn.setSelected(true);
    }

    /*
    This method will show all the appointments for selected customer from customer table
     */
    @FXML
    private void showAppointmentsForSelectedCustomer(ActionEvent actionEvent) {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            appointmentsTbl.setItems(AppointmentQuery.allAppointmentsForSelectedCustomer(selectedCustomer.getCustomerId()));
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        } else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Please select customer to show!");
            alert1.showAndWait();
        }

    }

    // ****************** Appointments side of the screen ************
    /*
    This method will show appointments for current month when current month button is selected
     */
    @FXML
    private void onActionCurrentMonthBtnSelected(ActionEvent actionEvent) {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            appointmentsTbl.setItems(AppointmentQuery.getAppointmentsCurrentMonth(selectedCustomer.getCustomerId()));
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        } else{
            appointmentsTbl.setItems(AppointmentQuery.getAllAppointmentsForCurrentMonth());
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        }
    }

    /*
    This method will show appointments for current week when current week button is selected
     */
    @FXML
    private void onActionCurrentWeekBtnSelected(ActionEvent actionEvent) {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            appointmentsTbl.setItems(AppointmentQuery.getAppointmentsCurrentWeek(selectedCustomer.getCustomerId()));
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        } else{
            appointmentsTbl.setItems(AppointmentQuery.getAllAppointmentsForCurrentWeek());
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        }
    }

    /*
    This method will show all of the appointments form database when all button is selected
     */
    @FXML
    private void onActionAllBtnSelected(ActionEvent actionEvent) {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            appointmentsTbl.setItems(AppointmentQuery.allAppointmentsForSelectedCustomer(selectedCustomer.getCustomerId()));
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        } else {
            appointmentsTbl.setItems(AppointmentQuery.getAllAppointments());
            appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        }

    }

    /*
    This method will switch to add new appointment screen if no customer is selected in customer table,
    otherwise, if customer is selected it will take that customer, transfer the customer name to the
    add appointment screen giving user option to create an appointment for existing customer
     */
    @FXML
    void handleAddNewAppointment(ActionEvent event) throws IOException {

        selectedCustomer = customersTbl.getSelectionModel().getSelectedItem();
        indexOfSelectedCustomer = CustomerQuery.getAllCustomers().indexOf(selectedCustomer);
        if(selectedCustomer == null){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddAppointment.fxml"));
            Parent addAppointmentStage = loader.load();
            Scene addAppointmentScene = new Scene(addAppointmentStage);
            // Access the modify customer controller and call a method
            AddAppointmentController controller = loader.getController();
            controller.showSelectedCustomer(customersTbl.getSelectionModel().getSelectedItem());

            Stage applicationStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            applicationStage.setScene(addAppointmentScene);
            applicationStage.show();

        }


    }
    /*
    This method will delete selected appointment from database.
    Also utilizes a lambda expression
     */
    @FXML
    void handleDeleteAppointment(ActionEvent event) {

        Appointment selectedAppointment = appointmentsTbl.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Please confirm that you want to delete selected appointment!");
            // Lambda expression to help maximize efficiency while deleting an appointment from database and displaying an alert
            alert.showAndWait().ifPresent((response -> {
                if(response == ButtonType.OK){
                    try{
                        AppointmentQuery.deleteAppointment(selectedAppointment);
                        appointmentsTbl.setItems(AppointmentQuery.getAllAppointments());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }));
        }
    }

    /*
    This method will navigate user back to the main screen
     */
    @FXML
    void handleGoBackToMainScreen(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /*
    This method will pick selected appointment and open up Modify appointment screen
     */
    @FXML
    void handleModifyAppointment(ActionEvent event) throws IOException {

        selectedAppointment = appointmentsTbl.getSelectionModel().getSelectedItem();
        indexOfSelectedAppointment = AppointmentQuery.getAllAppointments().indexOf(selectedAppointment);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            Parent modifyAppointmentStage = loader.load();
            Scene modifyAppointmentScene = new Scene(modifyAppointmentStage);
            // Access the modify appointment controller and call a method
            ModifyAppointmentController controller = loader.getController();
            controller.showAppointmentToModify(appointmentsTbl.getSelectionModel().getSelectedItem());

            Stage applicationStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            applicationStage.setScene(modifyAppointmentScene);
            applicationStage.show();

        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an appointment to modify!");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customersTbl.setItems(CustomerQuery.getAllCustomers());
        customerIdTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneNumTblColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        appointmentsTbl.setItems(AppointmentQuery.getAllAppointments());
        appCustomerTblColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appTitleTblColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeTblColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartTblColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndTblColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

    }

}
