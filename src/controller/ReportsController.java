package controller;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton appointmentsByMonth;
    @FXML
    private ToggleGroup reportsToggleGroup;
    @FXML
    private RadioButton eachConsultantSchedule;
    @FXML
    private RadioButton numOfCustomers;
    @FXML
    private TextArea displayTextArea;
    @FXML
    private Button backButton;
    @FXML
    private Label displayLabel;

    @FXML
    void onActionHandleBackToMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /*
        This method will handle number of appointment types by month
     */
    @FXML
    void showNumAppointmentTypesByMonth(ActionEvent event) {
        displayLabel.setText("Number of appointment types by month");

        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String appTypesQuery = "SELECT type as 'Type', MONTHNAME(start) as 'Month', COUNT(*) as 'Total of App. Types'" +
                    "FROM appointment GROUP BY type, MONTH(start)" +
                    "ORDER BY 'MONTH'";
            DatabaseQuery.setPreparedStatement(connection, appTypesQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%1$-50s %2$-35s %3$s \n", "Type", "Month", "Total of App. Types"));
            stringBuilder.append(String.join(" ", Collections.nCopies(44, "#")));
            stringBuilder.append("\n");
            while(resultSet.next()){
                stringBuilder.append(String.format("%1$-45s %2$-50s %3$s \n",
                        resultSet.getString("Type"), resultSet.getString("Month"),
                        resultSet.getString("Total of App. Types")));
            }
            displayTextArea.setText(stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
        This method will handle reporting customers and their appointments
     */
    @FXML
    void showNumberOfCustomers(ActionEvent event) {
        displayLabel.setText("Total number of customers");
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
//            String customerQuery = "SELECT customer.customerName AS 'Customer Name, appointment.type AS 'Type' " +
//                    "FROM customer JOIN appointment ON customer.customerId = appointment.customerId " +
//                    "ORDER BY 'Customer Name'";
            String customerQuery = "select customer.customerName as 'Customer Name' , appointment.type as 'Type'\n" +
                    "from customer join appointment ON customer.customerId = appointment.customerId\n" +
                    "order by 'Customer Name'";
            DatabaseQuery.setPreparedStatement(connection, customerQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%1$-45s %2$s \n", "Customer Name", "Type"));
            stringBuilder.append(String.join(" ", Collections.nCopies(30, "#")));
            stringBuilder.append("\n");
            while (resultSet.next()) {
                stringBuilder.append(String.format("%1$-45s %2$s \n",
                        resultSet.getString("Customer Name"), resultSet.getString("Type")));
            }
            displayTextArea.setText(stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
       This method will handle the schedule for each consultant
    */
    @FXML
    void showScheduleForEachConsultant(ActionEvent event) {
        displayLabel.setText("Schedule for each consultant");
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String scheduleQuery = "SELECT appointment.contact AS 'Consultant', appointment.type AS 'Type', appointment.start AS 'Start', " +
                    "appointment.end AS 'End', customer.customerName AS 'Customer Name' " +
                    "FROM appointment JOIN customer ON customer.customerId = appointment.customerId " +
                    "GROUP BY appointment.contact, MONTH(start), start " +
                    "ORDER BY Consultant";
            DatabaseQuery.setPreparedStatement(connection, scheduleQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%1$-25s %2$-25s %3$-40s %4$-30s %5$s \n", "Consultant", "Type", "Start", "End", "Customer Name"));
            stringBuilder.append(String.join(" ", Collections.nCopies(80, "#")));
            stringBuilder.append("\n");
            while (resultSet.next()){
                stringBuilder.append(String.format("%1$-25s %2$-20s %3$-25s %4$-25s %5$s \n",
                        resultSet.getString("Consultant"), resultSet.getString("Type"),
                        resultSet.getString("Start"), resultSet.getString("End"),
                        resultSet.getString("Customer Name")));
            }
            displayTextArea.setText(stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
