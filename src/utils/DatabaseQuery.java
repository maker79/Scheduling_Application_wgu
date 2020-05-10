package utils;

import com.mysql.cj.xdevapi.SqlStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.City;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String sqlQuery = "SELECT city FROM city";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                // getting data from result set
                City city = new City(resultSet.getString("city"));
                allCities.add(city);
            }
            return allCities;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /*
    **This method will get all appointments that are in the database
     */
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT appointment.appointmentId, appointment.title, appointment.type, appointment.start, appointment.end, customer.customerName " +
                    "FROM appointment INNER JOIN customer ON appointment.customerId=customer.customerId";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                Appointment appointment = new Appointment(
                        resultSet.getString("title"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime(),
                        resultSet.getString("customerName")
                );
                allAppointments.add(appointment);
                System.out.println(allAppointments);
                return allAppointments;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    *This method will get all the customers that are in our database
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allOfTheCustomers = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT customer.customerId, customer.customerName, address.address, address.phone" +
                    " FROM customer INNER JOIN address ON customer.addressId = address.addressId";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                // getting data from result set
                Customer customer = new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("customerName"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );
                allOfTheCustomers.add(customer);

            }
            return allOfTheCustomers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    This method will delete a customer from the database
     */
    public static boolean deleteCustomerFromDatabase(int customerId, String address) throws SQLException {

        Connection connection = DatabaseConnectionManager.getConnection();
        String deleteFromAddress = "DELETE FROM address WHERE address=" + "'"+address+"'";
        DatabaseQuery.setPreparedStatement(connection, deleteFromAddress);
        PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
        preparedStatement.execute();
        int addressUpdate = preparedStatement.getUpdateCount();
        if(addressUpdate == 1){
            String deleteFromCustomer = "DELETE FROM customer WHERE customerId=" + customerId;
            DatabaseQuery.setPreparedStatement(connection, deleteFromCustomer);
            PreparedStatement preparedStatement1 = DatabaseQuery.getPreparedStatement();
            preparedStatement1.execute();
            int customerUpdate = preparedStatement1.getUpdateCount();
            if(customerUpdate == 1)
                return true;
        }
        return false;
    }

    /*
    *This method will save a new customer to the database
     */
    public static boolean addCustomerToDatabase(String customerName, String address, int cityId, String country, String zipCode, String phone){
        int addressId = 0;
        int customerId = 0;

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String selectAddressId = "SELECT MAX(addressId) FROM address";
            DatabaseQuery.setPreparedStatement(connection, selectAddressId);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet addressResultSet = preparedStatement.getResultSet();
            if (addressResultSet.next()){
                addressId = addressResultSet.getInt(1);
                addressId++;
            }

            String selectCustomerId = "SELECT MAX(customerId) FROM customer";
            DatabaseQuery.setPreparedStatement(connection, selectCustomerId);
            PreparedStatement preparedStatement1 = DatabaseQuery.getPreparedStatement();
            preparedStatement1.execute();
            ResultSet customerResultSet = preparedStatement.executeQuery();
            if(customerResultSet.next()){
                customerId = customerResultSet.getInt(1);
                customerId++;
            }

            String addAddress = "INSERT INTO address SET addressId = ?, address = ?, address2 = 'none', cityId = ?, " +
                    "postalCode = ?, phone = ?, createDate = NOW(), createdBy = '', lastUpdate = NOW(), lastUpdateBy = ''";
            DatabaseQuery.setPreparedStatement(connection, addAddress);
            PreparedStatement preparedStatement3 = DatabaseQuery.getPreparedStatement();
            preparedStatement3.setInt(1, addressId);
            preparedStatement3.setString(2, address);
            preparedStatement3.setInt(3, cityId);
            preparedStatement3.setString(4, zipCode);
            preparedStatement3.setString(5, phone);
            preparedStatement3.execute();

            int addressTableUpdate = preparedStatement3.getUpdateCount();
            if(addressTableUpdate == 1){
                String addCustomer = "INSERT INTO customer SET customerId = ?, customerName = ?, addressId = ?, active = 1," +
                        "createDate = NOW(), createdBy = '', lastUpdate = NOW(), lastUpdateBy = ''";
                DatabaseQuery.setPreparedStatement(connection, addCustomer);
                PreparedStatement preparedStatement4 = DatabaseQuery.getPreparedStatement();
                preparedStatement4.setInt(1, customerId);
                preparedStatement4.setString(2, customerName);
                preparedStatement4.setInt(3, addressId);
                preparedStatement4.execute();

                int customerTableUpdate = preparedStatement4.getUpdateCount();
                if(customerTableUpdate == 1){
                    return true;
            }
            }
        }
        catch (SQLException e){
            System.out.println("Unable to save customer: " + e.getMessage());
        }
        return false;
    }


}
