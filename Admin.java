import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Admin {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<HashMap<String, Object>> spaces = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> reservations;
    private int lastId = 0;

    public Admin(ArrayList<HashMap<String, Object>> reservations) {
        this.reservations = reservations;
    }

    public void adminMenu() {
        while (true) {
            System.out.println("-".repeat(40));
            System.out.println("|   Admin Menu:                        |");
            System.out.println("|   1. Add a new coworking scape.      |");
            System.out.println("|   2. Remove a coworking space.       |");
            System.out.println("|   3. View all reservations.          |");
            System.out.println("|   4. Back to the Main Menu.          |");
            System.out.println("|   5. Exit.                           |");
            System.out.println("-".repeat(40));
            System.out.println("\nYour option: ");

            int optionAdmin = scanner.nextInt();

            if (optionAdmin == 1) {
                addCoworkingSpace();
            } else if (optionAdmin == 2) {
                removeCoworkingSpace();
            } else if (optionAdmin == 3) {
                viewAllReservations();
            } else if (optionAdmin == 4) {
                System.out.println("Switching back to the Main Menu...\n");
                return;
            } else if (optionAdmin == 5) {
                System.out.println("Thank you for using our system. Bye!\n");
                System.exit(0);
            } else {
                System.out.println("No such option. Please try again.\n");
            }
        }
    }

    private void addCoworkingSpace() {
        HashMap<String, Object> space = new HashMap<>();

        lastId++;
        space.put("spaceID", lastId);
        
        System.out.println("\nEnter space type: ");
        scanner.nextLine();
        space.put("type", scanner.nextLine());
        
        System.out.println("Enter price: ");
        space.put("price", scanner.nextDouble());
        
        space.put("isAvailable", true);

        spaces.add(space);

        System.out.println("Space added successfully!\n");
    }

    private void removeCoworkingSpace() {
        if (spaces.isEmpty()) {
            System.out.println("No spaces found.\n");
            return;
        }

        System.out.println("\nEnter space ID to remove: ");

        for (HashMap<String, Object> space : spaces) {
            System.out.println("Available space ID: " + space.get("spaceID"));
        }
        
        int id = scanner.nextInt();
        boolean isRemoved = false;

        for (HashMap<String, Object> space : spaces) {
            if ((int) space.get("spaceID") == id) {
                spaces.remove(space);
                isRemoved = true;
                System.out.println("Space removed successfully!\n");
                break;
            }
        }

        if (!isRemoved) {
            System.out.println("ID not found.\n");
        }
    }

    private void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("All reservations: ");

            for (HashMap<String, Object> reservation : reservations) {
                System.out.println("\nID: " + reservation.get("reservationID") + "\nCustomer: " + reservation.get("name") + 
                    "\nSpace ID: " + reservation.get("spaceID") + "\nDate: " + reservation.get("date") + 
                    "\nTime: " + reservation.get("startTime") + " to " + reservation.get("endTime ") + "\n");
            }
        }
    }
    
    public ArrayList<HashMap<String, Object>> getScapes() {
        return spaces;
    }
}