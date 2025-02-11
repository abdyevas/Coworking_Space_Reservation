package models;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "spaces")
public class Spaces implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int spaceID;

    private String type;
    private double price;
    private boolean isAvailable;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservations> reservations;

    public Spaces() {}

    public Spaces(String type, double price, boolean isAvailable) {
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public void setSpaceID(int spaceID) {
        this.spaceID = spaceID;
    }

    public int getSpaceID() {
        return spaceID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public List<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservations> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "\nID: " + spaceID + "\nType: " + type + "\nPrice: " + price + " AZN"  + 
        "\nAvailability: " + (isAvailable ? "Available\n" : "Not available\n");
    }
}
