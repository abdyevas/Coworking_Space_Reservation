import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<HashMap<String, Object>> reservations = new ArrayList<>();

        Admin admin = new Admin(reservations);
        Customer customer = new Customer(admin.getScapes(), reservations);

        System.out.println("\nWelcome to the Coworking Space Reservation System!");
        System.out.println("Find, book, and manage coworking spaces easily.\n");
        
        while (true) {
            System.out.println("-".repeat(40));
            System.out.println("|   Choose one of the options below:   |");
            System.out.println("|   1. Login as Admin                  |");
            System.out.println("|   2. Login as User                   |");
            System.out.println("|   3. Exit                            |");
            System.out.println("-".repeat(40));
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
