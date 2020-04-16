package schedulingApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DatabaseConnectionManager;

import java.sql.SQLException;

public class SchedulingApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"));
        primaryStage.setTitle("Scheduling App -Sign In");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // make a connection to a database
        DatabaseConnectionManager.makeConnection();

        launch(args);
        // closing a connection
        DatabaseConnectionManager.closeConnection();



    }

}
