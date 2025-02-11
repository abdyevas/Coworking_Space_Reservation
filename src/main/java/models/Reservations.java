package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservations implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationID;

    private String customerName; 

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Spaces space;

    private LocalDate date; 
    private LocalTime startTime; 
    private LocalTime endTime;

    public Reservations() {}

    public Reservations(String name, Spaces space, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.customerName = name;
        this.space = space;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getReservationID() {
        return reservationID;
    }

    public Spaces getSpace() {
        return space;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override 
    public String toString() {
        return "\nID: " + reservationID + "\nCustomer: " + customerName + "\nSpace ID: " + space.getSpaceID() + 
        "\nDate: " + date + "\nTime: " + startTime + " to " + endTime  + "\n";
    }
}
