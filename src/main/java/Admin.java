import classloader.CustomClassLoader;
import exceptions.*;

import database.*; 
import java.util.*;

public class Admin {
    private Scanner scanner = new Scanner(System.in);
    
    private SpacesDAO spacesDAO = new SpacesDAO();
    private ReservationsDAO reservationsDAO = new ReservationsDAO();
    
    public Admin() {}

    public void adminMenu() {
        String adminMenu = """
                +--------------------------------------+ 
                |   Admin Menu:                        | 
                |   1. Add a new coworking space.      | 
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
                System.out.println("\nEnter space ID to remove: ");
                spacesDAO.getAllSpaces().forEach(space -> 
                    System.out.println("Available space ID: " + space.get("id"))
                );

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
        spacesDAO.addSpace(type, price, true); 
        System.out.println("Space added successfully!\n");
    }

    public void removeCoworkingSpace(int id) throws InvalidSpaceIDException {
        List<Map<String, Object>> spaces = spacesDAO.getAllSpaces();
        Optional<Map<String, Object>> spaceToRemove = spaces.stream()
                                               .filter(space -> (int) space.get("id") == id)
                                               .findFirst();

        if (spaceToRemove.isPresent()) {
            spacesDAO.removeSpace(id);  
            System.out.println("Space removed successfully!\n");
        } else {
            throw new InvalidSpaceIDException("Space ID not found: " + id + "\n");
        }
    }

    public void viewAllReservations() {
        List<Map<String, Object>> reservations = reservationsDAO.getAllReservations();
        if (!reservations.isEmpty()) {
            reservations.forEach(reservation -> System.out.println(reservation));
        } else {
            System.out.println("No reservations found.\n");
        }
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
