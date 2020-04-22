package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import utils.DatabaseConnectionManager;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController {

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


    public static Boolean validateLoginAttempt(String username, String password) {

        try{
            String sqlQuery = "SELECT * FROM user WHERE userName ='" + username + "' AND password ='" + password + "'";
            PreparedStatement preparedStatement = DatabaseConnectionManager.getConnection().prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User currentUser = new User();
                currentUser.setUserName(resultSet.getString("userName"));
                return true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {

        String username = userNameTxt.getText();
        String password = passwordTxt.getText();
        boolean validateUser = validateLoginAttempt(username, password);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

        if(username.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorTitle"));
            alert.setContentText(resourceBundle.getString("errorMessage"));
            alert.showAndWait();
        }
        else if (validateUser) {
            welcomeLbl.setText(resourceBundle.getString("welcome"));
            userNameLbl.setText(resourceBundle.getString("username"));
            passwordLbl.setText(resourceBundle.getString("password"));
            loginBtn.setText(resourceBundle.getString("login"));

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(resourceBundle.getString("warningTitle"));
                alert.setContentText(resourceBundle.getString("warningMessage"));
                alert.showAndWait();
            }
    }


//    public static User getCurrentUser(){
//        return currentUser;
//    }
}
