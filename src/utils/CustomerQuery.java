package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQuery {


    /*
    This method will return a Customer from a database
     */
    public static Customer getCustomer(int id){
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String getCustomerQuery = "SELECT * FROM customer WHERE customerId=?";
            DatabaseQuery.setPreparedStatement(connection, getCustomerQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setCustomerName(resultSet.getString("customerName"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     *This method will get all the customers that are in the database
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allOfTheCustomers = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT customer.customerId, customer.customerName, customer.addressId, address.address, address.address2, " +
                    "address.cityId, address.postalCode, address.phone" +
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
                        resultSet.getInt("addressId"),
                        resultSet.getString("address"),
                        resultSet.getString("address2"),
                        resultSet.getInt("cityId"),
                        resultSet.getString("postalCode"),
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
    public static void deleteCustomerFromDatabase(Customer customer) throws SQLException {

        Connection connection = DatabaseConnectionManager.getConnection();
        String deleteAppointment = "DELETE FROM appointment WHERE customerId = ?";
        DatabaseQuery.setPreparedStatement(connection, deleteAppointment);
        PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
        preparedStatement.setInt(1, customer.getCustomerId());
        preparedStatement.execute();
        int appointmentTableUpdate = preparedStatement.getUpdateCount();
        if(appointmentTableUpdate == 1){
            String deleteCustomer = "DELETE FROM customer WHERE customerId = ?";
            DatabaseQuery.setPreparedStatement(connection, deleteCustomer);
            PreparedStatement preparedStatement1 = DatabaseQuery.getPreparedStatement();
            preparedStatement1.setInt(1, customer.getCustomerId());
            preparedStatement1.execute();
        }
    }

    /*
     *This method will save a new customer to the database
     */
    public static boolean addCustomerToDatabase(String customerName, String address, int cityId, String zipCode, String phone){
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

    /*
    This method will update or modify an existing customer
     */
    public static boolean modifyExistingCustomer(int id, String customerName, String address, int cityId, String zipCode, String phone, int addressId) throws SQLException {

        Connection connection = DatabaseConnectionManager.getConnection();
        String modifyAddress = "UPDATE address SET address=?, cityId=?, postalCode=?, phone=?, " +
                "lastUpdate=CURRENT_TIMESTAMP WHERE addressId=?";
        DatabaseQuery.setPreparedStatement(connection, modifyAddress);
        PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
        preparedStatement.setString(1, address);
        preparedStatement.setInt(2, cityId);
        preparedStatement.setString(3, zipCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, addressId); // need addressId here, not customer
        preparedStatement.execute();

        int addressTableUpdate = preparedStatement.getUpdateCount();
        if(addressTableUpdate == 1){
            String updateCustomer = "UPDATE customer set customerName=?, addressId=?, lastUpdate=CURRENT_TIMESTAMP " +
                    "WHERE customerId=?";
            DatabaseQuery.setPreparedStatement(connection, updateCustomer);
            PreparedStatement preparedStatement1 = DatabaseQuery.getPreparedStatement();
            preparedStatement1.setString(1, customerName);
            preparedStatement1.setInt(2, addressId); // do need addressId
            preparedStatement1.setInt(3, id);
            preparedStatement1.execute();

            int customerTableUpdate = preparedStatement1.getUpdateCount();
            if(customerTableUpdate == 1)
                return true;
        }
        return false;
    }

}
