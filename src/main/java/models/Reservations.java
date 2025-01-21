package models;

import java.io.Serializable;

public class Reservations implements Serializable{
    private int reservationID;
    private String customerName; 
    private int spaceID;
    private String date; 
    private String startTime; 
    private String endTime;

    public Reservations(int id, String name, int spaceID, String date, String startTime, String endTime) {
        this.reservationID = id;
        this.customerName = name;
        this.spaceID = spaceID;
        this.date = date;
        this.startTime =startTime;
        this.endTime = endTime;
    }

    public int getReservationID() {
        return reservationID;
    }

    public int getSpaceID() {
        return spaceID;
    }

    @Override 
    public String toString() {
        return "\nID: " + reservationID + "\nCustomer: " + customerName + "\nSpace ID: " + spaceID + 
        "\nDate: " + date + "\nTime: " + startTime + " to " + endTime  + "\n";
    }
}
