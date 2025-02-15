import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private Admin adminService;

    @Autowired
    private Customer customerService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Coworking Space Reservation System is running...");
    }

    @Override
    public void run(String... args) throws Exception {
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

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(mainMenu);
                System.out.println("\nYour option: ");
                int option = scanner.nextInt();

                if (option == 1) {
                    System.out.println("Switching to the Admin menu...\n");
                    adminService.adminMenu(); 
                } else if (option == 2) {
                    System.out.println("Switching to the User menu...\n");
                    customerService.customerMenu();  
                } else if (option == 3) {
                    System.out.println("Quiting the system... Bye!\n");
                    return;
                } else {
                    System.out.println("No such option. Please try again.\n");
                }
            }
        }
    }
}
