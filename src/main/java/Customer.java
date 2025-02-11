import exceptions.InvalidSpaceIDException;
import models.*;
import repository.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Customer {

    @Autowired
    private SpacesRepository spacesRepository;

    @Autowired
    private ReservationsRepository reservationsRepository;
    
    private Scanner scanner = new Scanner(System.in);

    public Customer() {}

    public void customerMenu() {
        String customerMenu = """
                +--------------------------------------+
                |   Customer Menu:                     |
                |   1. Browse available spaces.        |
                |   2. Make a reservation.             |
                |   3. Cancel a reservation.           |
                |   4. View my reservations.           |
                |   5. Back to the Main Menu.          |
                |   6. Exit.                           |
                +--------------------------------------+
                """;

        while (true) {
            System.out.println(customerMenu);
            System.out.print("\nYour option: ");
            int optionCustomer = scanner.nextInt();
            scanner.nextLine(); 

            if (optionCustomer == 1) {
                browseSpaces();
            } else if (optionCustomer == 2) {
                try {
                    System.out.println("\nNew Reservation:");
                    
                    System.out.print("Enter your full name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("Choose one of the available spaces: ");
                    browseSpaces();

                    System.out.print("Enter Space ID: ");
                    int spaceID = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter date (DD-MM-YYYY): ");
                    String date = scanner.nextLine();

                    System.out.print("Enter start time (HH:MM): ");
                    String startTime = scanner.nextLine();

                    System.out.print("Enter end time (HH:MM): ");
                    String endTime = scanner.nextLine();

                    makeReservation(customerName, spaceID, date, startTime, endTime);
                } catch (InvalidSpaceIDException e) {
                    System.out.println(e.getMessage());
                }
            } else if (optionCustomer == 3) {
                System.out.println("\nEnter reservation ID to cancel: ");
                viewMyReservations();
                int id = scanner.nextInt();
                scanner.nextLine(); 
                    
                cancelReservation(id);
            } else if (optionCustomer == 4) {
                viewMyReservations();
            } else if (optionCustomer == 5) {
                System.out.println("Switching back to the Main Menu...\n");
                return;
            } else if (optionCustomer == 6) {
                System.out.println("Thank you for using our system. Bye!\n");
                System.exit(0);
            } else {
                System.out.println("No such option. Please try again.\n");
            }
        }
    }

    public void browseSpaces() {
        System.out.println("\nAvailable Coworking Spaces:");
        List<Spaces> spaces = spacesRepository.findAll();
        if (spaces.isEmpty()) {
            System.out.println("No spaces available at the moment.\n");
        } else {
            spaces.stream()
                .filter(Spaces::getAvailable)
                .forEach(space -> System.out.println(space));
        }
    }

    public void makeReservation(String customerName, int spaceID, String date, String startTime, String endTime) throws InvalidSpaceIDException {
        LocalDate reservationDate;
        try {
            reservationDate = LocalDate.parse(date); 
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        LocalTime reservationStartTime;
        LocalTime reservationEndTime;
        try {
            reservationStartTime = LocalTime.parse(startTime); // Парсим строку в LocalTime
            reservationEndTime = LocalTime.parse(endTime);
        } catch (Exception e) {
            System.out.println("Invalid time format. Please use HH:MM.");
            return;
        }

        Optional<Spaces> chosenSpaceOpt = spacesRepository.findById(spaceID);

        if (chosenSpaceOpt.isEmpty()) {
            throw new InvalidSpaceIDException("Invalid ID or space is not available.\n");
        }

        Reservations reservation = new Reservations(customerName, chosenSpaceOpt.get(), reservationDate, reservationStartTime, reservationEndTime);
        reservationsRepository.save(reservation);

        chosenSpaceOpt.get().setAvailable(false);
        spacesRepository.save(chosenSpaceOpt.get());
        
        System.out.println("Reservation made successfully!\n");
    }

    public void cancelReservation(int reservationID) {
        Optional<Reservations> reservationOpt = reservationsRepository.findById(reservationID);

        if (reservationOpt.isEmpty()) {
            System.out.println("Reservation not found.\n");
            return;
        }

        Reservations reservation = reservationOpt.get();
        Spaces space = reservation.getSpace();

        reservationsRepository.delete(reservation);
        space.setAvailable(true);
        spacesRepository.save(space);

        System.out.println("Reservation removed successfully!\n");
    }

    public void viewMyReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("Your reservations:");
            reservations.forEach(reservation -> System.out.println(reservation));
        }
    }
}
