import models.Reservations;
import models.Spaces;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    public static void saveSpacesData(ArrayList<Spaces> spaces, String spacesDataFile) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(spacesDataFile))) {
            out.writeObject(spaces);
            out.flush();
            System.out.println("Spaces data saved successfully!\n");
        } catch (IOException e) {
            System.err.println("Cannot save spaces data: " + e.getMessage());
        }
    }

    public static ArrayList<Spaces> loadSpacesData(String spacesDataFile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(spacesDataFile))) {
            return (ArrayList<Spaces>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannoat load spaces data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveReservationsData(ArrayList<Reservations> reservations, String reservationsDataFile) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(reservationsDataFile))) {
            out.writeObject(reservations);
            out.flush();
            System.out.println("Reservations data saved successfully!\n");
        } catch (IOException e) {
            System.err.println("Cannot save reservations data: " + e.getMessage());
        }
    }

    public static ArrayList<Reservations> loadReservaionsData(String reservationsDataFile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(reservationsDataFile))) {
            return (ArrayList<Reservations>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannoat load reservations data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
