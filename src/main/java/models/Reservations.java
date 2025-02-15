package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationID;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Spaces space;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
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

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Spaces getSpace() {
        return space;
    }

    public void setSpace(Spaces space) {
        this.space = space;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "\nID: " + reservationID +
               "\nCustomer: " + customerName +
               "\nSpace ID: " + (space != null ? space.getSpaceID() : "N/A") +
               "\nDate: " + date +
               "\nTime: " + startTime + " to " + endTime + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservations that = (Reservations) o;
        return reservationID == that.reservationID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationID);
    }
}
