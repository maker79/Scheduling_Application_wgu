package utils;

import controller.LoginScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import model.Appointment;
import model.Customer;
import model.User;
import sun.util.locale.provider.JRELocaleConstants;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AppointmentQuery {


    /*
     ************************ Appointments query methods ***************************
     */
    /*
    This method will add a new appointment to the database
     */
    public static void addNewAppointment(int customerId, String title, User currentUser, String type, LocalDateTime start, LocalDateTime end){
        int appointmentId = 0;
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
            preparedStatement1.setString(5, currentUser.getUserName());
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
    public static void modifyExistingAppointment(int customerId, String title, String type, LocalDateTime start, LocalDateTime end, int id){
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String updateAppointment = "UPDATE appointment SET customerId=?, title=?, type=?, start=?, end=?, lastUpdate=CURRENT_TIMESTAMP " +
                    "WHERE appointmentId=?";
            DatabaseQuery.setPreparedStatement(connection, updateAppointment);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, type);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(end));
            preparedStatement.setInt(6, id);
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
                appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                        //resultSet.getString("customerName")
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
                appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                        //resultSet.getString("customerName")
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
                Appointment appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime()
                        //resultSet.getString("customerName")
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
            String sqlQuery = "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.contact, " +
                    "appointment.type, appointment.start, appointment.end, customer.customerName " +
                    "FROM appointment INNER JOIN customer ON appointment.customerId=customer.customerId";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                Appointment appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
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
    This method will get all appointments for current month
     */
    public static ObservableList<Appointment> getAllAppointmentsForCurrentMonth(){

        ObservableList<Appointment> allAppointmentsCurrentMonth = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate beginning = LocalDate.now();
        LocalDate ending = LocalDate.now().plusMonths(1);
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT appointment.appointmentId, appointment.customerId,appointment.title, appointment.description, " +
                    "appointment.contact, appointment.type, appointment.start, appointment.end, customer.customerName " +
                    "FROM appointment INNER JOIN customer ON appointment.customerId=customer.customerId " +
                    "WHERE start >= ? AND start <= ?";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setDate(1, Date.valueOf(beginning));
            preparedStatement.setDate(2, Date.valueOf(ending));
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                        appointment = new Appointment(
                                resultSet.getInt("appointmentId"),
                                resultSet.getInt("customerId"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("contact"),
                                resultSet.getString("type"),
                                resultSet.getTimestamp("start").toLocalDateTime(),
                                resultSet.getTimestamp("end").toLocalDateTime(),
                                resultSet.getString("customerName")
                );
                allAppointmentsCurrentMonth.add(appointment);
            }
            return allAppointmentsCurrentMonth;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    This method will get all appointments for current week
     */
    public static ObservableList<Appointment> getAllAppointmentsForCurrentWeek(){

        ObservableList<Appointment> allAppointmentsCurrentWeek = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate beginning = LocalDate.now();
        LocalDate ending = LocalDate.now().plusWeeks(1);
        try{
            Connection connection = DatabaseConnectionManager.getConnection();
            String sqlQuery = "SELECT appointmentId, appointment.customerId, appointment.title, description, contact, appointment.type, " +
                    "appointment.start, appointment.end, customer.customerName " +
                    "FROM appointment INNER JOIN customer ON appointment.customerId=customer.customerId " +
                    "WHERE start >= ? AND start <= ?";
            DatabaseQuery.setPreparedStatement(connection, sqlQuery);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setDate(1, Date.valueOf(beginning));
            preparedStatement.setDate(2, Date.valueOf(ending));
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime(),
                        resultSet.getString("customerName")
                );
                allAppointmentsCurrentWeek.add(appointment);
            }
            return allAppointmentsCurrentWeek;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
    This method will check if there is an appointment within 15 min
    of user's log in.
     */
    public static Appointment appointmentInFifteenMinutes(){
        Appointment appointment;
        String contact = LoginScreenController.validateUser.getUserName();

        try {
            Connection connection = DatabaseConnectionManager.getConnection();
            String appointment15min = "SELECT appointmentId, appointment.customerId, customer.customerName, title, description, contact, type, start, end " +
                    "FROM appointment, customer WHERE start BETWEEN NOW() AND NOW()+ INTERVAL 15 MINUTE AND contact=? " +
                    "AND customer.customerId=appointment.customerId";
            DatabaseQuery.setPreparedStatement(connection, appointment15min);
            PreparedStatement preparedStatement = DatabaseQuery.getPreparedStatement();
            preparedStatement.setString(1, contact);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                        appointment = new Appointment(
                        resultSet.getInt("appointmentId"),
                        resultSet.getInt("customerId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("contact"),
                        resultSet.getString("type"),
                        resultSet.getTimestamp("start").toLocalDateTime(),
                        resultSet.getTimestamp("end").toLocalDateTime(),
                        resultSet.getString("customerName")
                );
                return appointment;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
