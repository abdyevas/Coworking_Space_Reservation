import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    public static <T> void saveData(ArrayList<T> data, String dataFile) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            out.writeObject(data);
            out.flush();
            System.out.println("Data saved successfully to " + dataFile + "!\n");
        } catch (IOException e) {
            System.err.println("Cannot save data to " + dataFile + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> loadData(String dataFile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile))) {
            return (ArrayList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannoat load data from " + dataFile + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
