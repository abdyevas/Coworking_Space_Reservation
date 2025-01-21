import exceptions.*;
import models.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Optional;

public class Customer {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Spaces> spaces;
    private ArrayList<Reservations> reservations;
    private int reservationID = 0;

    private static final String SPACES_DATA_FILE = "src/main/resources/spaces.dat";
    private static final String RESERVATIONS_DATA_FILE = "src/main/resources/reservations.dat";

    public Customer(ArrayList<Spaces> spaces) {
        this.reservations = FileUtils.loadData(RESERVATIONS_DATA_FILE);
        this.spaces = FileUtils.loadData(SPACES_DATA_FILE);
    }

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
            System.out.println("\nYour option: ");

            int optionCustomer = scanner.nextInt();

            if (optionCustomer == 1) {
                browseSpaces();
            } else if (optionCustomer == 2) {
                try {
                    makeReservation();
                } catch (InvalidSpaceIDException e) {
                    System.out.println(e.getMessage());
                }
            } else if (optionCustomer == 3) {
                cancelReservation();
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

    private void browseSpaces() {
        spaces = FileUtils.loadData(SPACES_DATA_FILE);

        System.out.println("\nAvailable Coworking Spaces:");
        
        Optional.ofNullable(spaces)
                .filter(list -> !list.isEmpty())
                .orElseGet(() -> {
                    System.out.println("No spaces available at the moment.\n");
                    return new ArrayList<>();
                })
                .stream()
                .filter(Spaces::getAvailable)
                .forEach(System.out::println);
    }

    private void makeReservation() throws InvalidSpaceIDException{
        spaces = FileUtils.loadData(SPACES_DATA_FILE);

        if (spaces == null) {
            System.out.println("No spaces available at the moment.\n");
            return;
        }

        System.out.println("\nNew Reservaion: ");        
        System.out.println("\nEnter your full name: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();
        
        System.out.println("Choose one of the available spaces: ");
        spaces.stream()
              .filter(Spaces::getAvailable)
              .forEach(space -> System.out.println("Available Space ID: " + space.getSpaceID()));
        
        int spaceID = scanner.nextInt();
        
        Spaces chosenSpace = spaces.stream()
                                   .filter(space -> space.getSpaceID() == spaceID && space.getAvailable())
                                   .findFirst()
                                   .orElseThrow(() -> new InvalidSpaceIDException("Invalid ID or Space is not available.\n")); 

        System.out.println("Enter date (DD-MM-YYYY): ");
        scanner.nextLine();
        String date = scanner.next();
        
        System.out.println("Enter start time (HH:MM): ");
        String startTime = scanner.next();
        
        System.out.println("Enter end time (HH:MM): ");
        String endTime = scanner.next();

        reservationID++; 
        Reservations reservation = new Reservations(reservationID, customerName, spaceID, date, startTime, endTime);
        reservations.add(reservation);
        
        chosenSpace.setAvailable(false);
        System.out.println("Reservation made successfully!\n");
    
        updateData();
    }

    private void cancelReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
            return;
        }

        System.out.println("\nEnter reservation ID to cancel: ");
        reservations.forEach(reservation -> System.out.println("Active reservation ID: " + reservation.getReservationID()));

        int id = scanner.nextInt();

        Reservations canceledReservation = reservations.stream()
                                                       .filter(reservation -> reservation.getReservationID() == id)
                                                       .findFirst()
                                                       .orElse(null);

        if (canceledReservation == null) {
            System.out.println("Reservation not found.\n");
        } else {
            reservations.remove(canceledReservation);
            System.out.println("Reservation removed successfully!");

            spaces.stream()
                  .filter(space -> space.getSpaceID() == canceledReservation.getSpaceID())
                  .findFirst()
                  .ifPresent(space -> space.setAvailable(true));
        }
        
        updateData();    
    }

    private void viewMyReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("Your reservations:");

            reservations.forEach(reservation -> System.out.println(reservation));
        }
    }

    public void updateData() {
        FileUtils.saveData(reservations, RESERVATIONS_DATA_FILE);
        FileUtils.saveData(spaces, SPACES_DATA_FILE);
    }
}