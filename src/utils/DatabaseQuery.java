package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.City;
import model.Customer;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseQuery {

    private static PreparedStatement statement;

    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement(){
        return statement;
    }

     /*
    ********* These methods will handle queries *************
      */


    /*
    This method will get all the cities from database
     */
    public static ObservableList<City> getAllCities(){
        ObservableList<City> allCities = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT cityId, city, country FROM city, country WHERE city.countryId=country.countryId";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                // getting data from result set
                City city = new City(resultSet.getInt("cityId"),
                        resultSet.getString("city"),
                        resultSet.getString("country"));
                allCities.add(city);
            }
            return allCities;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
