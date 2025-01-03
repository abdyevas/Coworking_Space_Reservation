import models.Reservations;
import models.Spaces;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Spaces> spaces = new ArrayList<>();
        ArrayList<Reservations> reservations = new ArrayList<>();

        Admin admin = new Admin(reservations);
        Customer customer = new Customer(spaces);

        System.out.println("\nWelcome to the Coworking Space Reservation System!");
        System.out.println("Find, book, and manage coworking spaces easily.\n");

        String mainMenu = """
                +--------------------------------------+       
                |   Choose one of the options below:   |       
                |   1. Login as Admin.                 |       
                |   2. Login as User.                  |       
                |   3. Exit.                           |       
                +--------------------------------------+ 
                """;
        
        while (true) {
            System.out.println(mainMenu);
            System.out.println("\nYour option: ");

            int option = scanner.nextInt();

            if (option == 1) {
                System.out.println("Switching to the Admin menu...\n");
                admin.adminMenu();
            } else if (option == 2) {
                System.out.println("Switching to the User menu...\n");
                customer.customerMenu();
            } else if (option == 3) {
                System.out.println("Quiting the system... Bye!\n");
                System.exit(0);
            } else { 
                System.out.println("No such option. Please try again.\n");
            }
        }
    }
}
