import classloader.CustomClassLoader;
import exceptions.*;
import models.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Optional;

public class Admin {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Spaces> spaces;
    private ArrayList<Reservations> reservations;
    
    private static final String SPACES_DATA_FILE = "src/main/resources/spaces.dat";
    private static final String RESERVATIONS_DATA_FILE = "src/main/resources/reservations.dat";

    public Admin(ArrayList<Spaces> spaces, ArrayList<Reservations> reservations) {
        this.reservations = reservations;
        this.spaces = spaces;
    }

    public void adminMenu() {
        String adminMenu = """
                +--------------------------------------+
                |   Admin Menu:                        |
                |   1. Add a new coworking scape.      |
                |   2. Remove a coworking space.       |
                |   3. View all reservations.          |
                |   4. Load Custom Class.              |
                |   5. Back to the Main Menu.          |
                |   6. Exit.                           |
                +--------------------------------------+
                """;
                
        while (true) {
            System.out.println(adminMenu);
            System.out.println("\nYour option: ");

            int optionAdmin = scanner.nextInt();
            scanner.nextLine();

            if (optionAdmin == 1) {
                System.out.print("\nEnter space type: ");
                String type = scanner.nextLine();

                System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                addCoworkingSpace(type, price);
            } else if (optionAdmin == 2) {
                if (spaces.isEmpty()) {
                    System.out.println("No spaces found.\n");
                    break;
                }
                System.out.println("\nEnter space ID to remove: ");
                spaces.forEach(space -> System.out.println("Available space ID: " + space.getSpaceID()));

                int id = scanner.nextInt();

                try {
                    removeCoworkingSpace(id);
                } catch (InvalidSpaceIDException e) {
                    System.out.println(e.getMessage()); 
                }
            } else if (optionAdmin == 3) {
                viewAllReservations();
            } else if (optionAdmin == 4) {
                System.out.println("Enter the details of the class to be loaded.\n");
                System.out.println("Class directory: ");
                scanner.nextLine();
                String classPath = scanner.nextLine();

                System.out.println("Full class name: ");
                String className = scanner.nextLine();

                loadCustomClass(classPath, className);    
            } else if (optionAdmin == 5) {
                System.out.println("Switching back to the Main Menu...\n");
                return;
            } else if (optionAdmin == 6) {
                System.out.println("Thank you for using our system. Bye!\n");
                System.exit(0);
            } else {
                System.out.println("No such option. Please try again.\n");
            }
        }
    }

    public void addCoworkingSpace(String type, double price) {
        int lastId = spaces.stream()
                        .mapToInt(Spaces::getSpaceID)
                        .max()
                        .orElse(0) + 1;

        spaces.add(new Spaces(lastId, type, price, true));
        System.out.println("Space added successfully!\n");

        saveSpacesData();
    }

    public void removeCoworkingSpace(int id) throws InvalidSpaceIDException{
        Optional<Spaces> spaceToRemove = spaces.stream()
                                           .filter(space -> space.getSpaceID() == id)
                                           .findFirst();

        if (spaceToRemove.isPresent()) {
            spaces.remove(spaceToRemove.get());  
            System.out.println("Space removed successfully!\n");
            saveSpacesData();
        } else {
            throw new InvalidSpaceIDException("Space ID not found: " + id + "\n");
        }
    }

    public void viewAllReservations() {
        reservations = FileUtils.loadData(RESERVATIONS_DATA_FILE);

        Optional.ofNullable(reservations)
                .filter(list -> !list.isEmpty())
                .ifPresentOrElse(
                    list -> list.forEach(System.out::println), 
                    () -> System.out.println("No reservations found.\n")
                );
    }

    public void saveSpacesData() {
        FileUtils.saveData(spaces, SPACES_DATA_FILE);
    }
    
    public static void loadCustomClass(String classPath, String className) {
        CustomClassLoader customClassLoader = new CustomClassLoader(classPath, Main.class.getClassLoader());
        try {
            Class<?> loaderClass = customClassLoader.loadClass(className);
            System.out.println("Class " + loaderClass.getName() + " loaded successfully!\n");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
        }
    }
}