package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utils.AppointmentQuery;
import utils.DatabaseConnectionManager;
import utils.DatabaseQuery;
import utils.FileLogger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    private Label welcomeLbl;
    @FXML
    private Label userNameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Button loginBtn;
    
    ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());
    public static User validateUser = null;

    // currently only user(consultant) in the database is 'test' user with a password 'test'
    public static User validateLoginAttempt(String username, String password) {

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT * FROM user WHERE userName = ? AND password = ?";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                User currentUser = new User();
                currentUser.setUserId(resultSet.getInt("userId"));
                currentUser.setUserName(resultSet.getString("userName"));

                return currentUser;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {

        String username = userNameTxt.getText();
        String password = passwordTxt.getText();
        validateUser = validateLoginAttempt(username, password);

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorTitle"));
            alert.setContentText(resourceBundle.getString("errorMessage"));
            alert.showAndWait();
        } else if (validateUser != null) {
            FileLogger.handleLog(username, true);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            FileLogger.handleLog(username, false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(resourceBundle.getString("warningTitle"));
            alert.setContentText(resourceBundle.getString("warningMessage"));
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        welcomeLbl.setText(resourceBundle.getString("welcome"));
        userNameLbl.setText(resourceBundle.getString("username"));
        passwordLbl.setText(resourceBundle.getString("password"));
        loginBtn.setText(resourceBundle.getString("login"));

    }

}
