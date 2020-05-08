package model;

import java.time.LocalDateTime;

public class Appointment {

    private String type;
    private  String name;
    private int appointmentId;
    private int customerId;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String user;

    public Appointment(int appointmentId, int customerId, String title, String description, LocalDateTime start, LocalDateTime end, String user) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.user = user;
    }

    public Appointment(String title, String type, LocalDateTime start, LocalDateTime end, String name) {
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.valueOf(appointmentId);
    }
}
