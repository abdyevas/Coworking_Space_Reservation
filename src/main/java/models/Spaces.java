package models;

import java.io.Serializable;

public class Spaces implements Serializable {
    private int spaceID;
    private String type;
    private double price;
    private boolean isAvailable;

    public Spaces(int id, String type, double price, boolean isAvailable) {
        this.spaceID = id;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getSpaceID() {
        return spaceID;
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "\nID: " + spaceID + "\nType: " + type + "\nPrice: " + price + " AZN"  + 
        "\nAvailability: " + (isAvailable ? "Available\n" : "Not available\n");
    }
}
