import models.Reservations;
import models.Spaces;
import exceptions.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Admin {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Spaces> spaces;
    private ArrayList<Reservations> reservations;
    private int lastId = 0;
    
    private static final String SPACES_DATA_FILE = "spaces.dat";
    private static final String RESERVATIONS_DATA_FILE = "reservations.dat";

    public Admin(ArrayList<Reservations> reservations) {
        this.reservations = FileUtils.loadReservaionsData(RESERVATIONS_DATA_FILE);
        this.spaces = FileUtils.loadSpacesData(SPACES_DATA_FILE);
    }

    public void adminMenu() {
        String adminMenu = """
                +--------------------------------------+
                |   Admin Menu:                        |
                |   1. Add a new coworking scape.      |
                |   2. Remove a coworking space.       |
                |   3. View all reservations.          |
                |   4. Back to the Main Menu.          |
                |   5. Exit.                           |
                +--------------------------------------+
                """;
                
        while (true) {
            System.out.println(adminMenu);
            System.out.println("\nYour option: ");

            int optionAdmin = scanner.nextInt();

            if (optionAdmin == 1) {
                addCoworkingSpace();
            } else if (optionAdmin == 2) {
                try {
                    removeCoworkingSpace();
                } catch (InvalidSpaceIDException e) {
                    System.out.println(e.getMessage()); 
                }
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
        lastId++;
        
        System.out.println("\nEnter space type: ");
        scanner.nextLine();
        String type = scanner.nextLine();
        
        System.out.println("Enter price: ");
        double price = scanner.nextDouble();

        spaces.add(new Spaces(lastId, type, price, true));
        System.out.println("Space added successfully!\n");

        saveSpacesData();
    }

    private void removeCoworkingSpace() throws InvalidSpaceIDException{
        if (spaces.isEmpty()) {
            System.out.println("No spaces found.\n");
            return;
        }

        System.out.println("\nEnter space ID to remove: ");

        for (Spaces space : spaces) {
            System.out.println("Available space ID: " + space.getSpaceID());
        }
        
        int id = scanner.nextInt();
        boolean isRemoved = false;

        for (Spaces space : spaces) {
            if (space.getSpaceID() == id) {
                spaces.remove(space);
                isRemoved = true;
                System.out.println("Space removed successfully!\n");
                break;
            }
        }
        if (!isRemoved) {
            throw new InvalidSpaceIDException("Space ID now found: " + id + "\n");
        }
        
        saveSpacesData();
    }

    private void viewAllReservations() {
        reservations = FileUtils.loadReservaionsData(RESERVATIONS_DATA_FILE);

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("All reservations: ");

            for (Reservations reservation : reservations) {
                System.out.println(reservation);    
            }
        }
    }
    
    public void saveSpacesData() {
        FileUtils.saveSpacesData(spaces, SPACES_DATA_FILE);
    }
}