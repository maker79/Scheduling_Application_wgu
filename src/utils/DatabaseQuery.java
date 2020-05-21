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
    /*
    ************************ Appointments query methods ***************************
     */
    /*
    This method will add a new appointment to the database
     */
    public static void addNewAppointment(int customerId, String title, User currentUser, String type, LocalDateTime start, LocalDateTime end){
        int appointmentId = 0;
        User user;
        user = currentUser;
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String selectAppointmentId = "SELECT MAX(appointmentId) FROM appointment";
            DatabaseQuery.setPreparedStatement(connection, selectAppointmentId);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet appointmentResultSet = preparedStatement.getResultSet();
            if(appointmentResultSet.next()){
                appointmentId = appointmentResultSet.getInt(1);
                appointmentId++;
            }
            String addAppointmentStatement = "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, " +
                    "type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, 'not needed', 'not needed', ?, ?, " +
                    "'not needed', ?, ?, CURRENT_TIMESTAMP, 'not needed', CURRENT_TIMESTAMP, 'not needed')";
            DatabaseQuery.setPreparedStatement(connection, addAppointmentStatement);
            PreparedStatement preparedStatement1 = DatabaseQuery.getPreparedStatement();
            preparedStatement1.setInt(1, appointmentId);
            preparedStatement1.setInt(2, customerId);
            preparedStatement1.setInt(3, currentUser.getUserId());
            preparedStatement1. setString(4, title);
            preparedStatement1.setObject(5, currentUser.getUserName());
            preparedStatement1.setString(6, type);
            preparedStatement1.setTimestamp(7, Timestamp.valueOf(start));
            preparedStatement1.setTimestamp(8, Timestamp.valueOf(end));
            preparedStatement1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    This method will modify an existing appointment
     */
    public static void modifyExistingAppointment(String title, String type, LocalDateTime start, LocalDateTime end, int id){
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String updateAppointment = "UPDATE appointment SET title=?, type=?, start=?, end=?, lastUpdate=CURRENT_TIMESTAMP " +
                    "WHERE appointmentId=?";
            DatabaseQuery.setPreparedStatement(connection, updateAppointment);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, type);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(end));
            preparedStatement.setInt(5, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    This method will delete appointment from the database
     */
    public static void deleteAppointment(Appointment appointment){
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String deleteAppointment = "DELETE FROM appointment WHERE appointmentId=?";
            DatabaseQuery.setPreparedStatement(connection, deleteAppointment);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, appointment.getAppointmentId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    This method will get appointment for the current month
     */
    public static ObservableList<Appointment> getAppointmentsCurrentMonth(int customerId){
        ObservableList<Appointment> appointmentsCurrentMonth = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate beginning = LocalDate.now();
        LocalDate ending = LocalDate.now().plusMonths(1);
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String currentMonthQuery = "SELECT * FROM appointment WHERE customerId=? AND start >= ? AND start <= ?";
            DatabaseQuery.setPreparedStatement(connection, currentMonthQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(beginning));
            preparedStatement.setDate(3, Date.valueOf(ending));
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                appointment = new Appointment(resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                );
                appointmentsCurrentMonth.add(appointment);
            }
            return appointmentsCurrentMonth;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    This method will get appointments for the current week
     */
    public static ObservableList<Appointment> getAppointmentsCurrentWeek(int customerId){
        ObservableList<Appointment> appointmentsCurrentWeek = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate beginning = LocalDate.now();
        LocalDate ending = LocalDate.now().plusWeeks(1);
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String currentWeekQuery = "SELECT * FROM appointment WHERE customerId=? AND start >= ? AND start <= ?";
            DatabaseQuery.setPreparedStatement(connection, currentWeekQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(beginning));
            preparedStatement.setDate(3, Date.valueOf(ending));
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                appointment = new Appointment(resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                );
                appointmentsCurrentWeek.add(appointment);
            }
            return appointmentsCurrentWeek;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    This method will get all appointments for selected customer
     */
    public static ObservableList<Appointment> allAppointmentsForSelectedCustomer(int customerId){
        ObservableList<Appointment> appointmentsAllForCustomer = FXCollections.observableArrayList();
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String appointmentsQuery = "SELECT * FROM appointment WHERE customerId=?";
            DatabaseQuery.setPreparedStatement(connection, appointmentsQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, customerId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                Appointment appointment = new Appointment(resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                );
                appointmentsAllForCustomer.add(appointment);
            }
            return appointmentsAllForCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
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
            String sqlQuery = "SELECT appointment.title, appointment.type, appointment.start, appointment.end, customer.customerName " +
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
//                System.out.println("All appointments: " + allAppointments);
            }
            return allAppointments;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

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
