import models.Reservations;
import models.Spaces;
import exceptions.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Customer {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Spaces> spaces;
    private ArrayList<Reservations> reservations;
    private int reservationID = 0;

    private static final String SPACES_DATA_FILE = "spaces.dat";
    private static final String RESERVATIONS_DATA_FILE = "reservations.dat";

    public Customer(ArrayList<Spaces> spaces) {
        this.reservations = FileUtils.loadReservaionsData(RESERVATIONS_DATA_FILE);
        this.spaces = FileUtils.loadSpacesData(SPACES_DATA_FILE);
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
        spaces = FileUtils.loadSpacesData(SPACES_DATA_FILE);

        System.out.println("\nAvailable Coworking Spaces:");
        
        if (spaces == null) {
            System.out.println("No spaces available at the moment.\n");
            return;
        }

        for (Spaces space: spaces) {
            if (space.getAvailable()) {
                System.out.println(space);
            }    
        } 
    }

    private void makeReservation() throws InvalidSpaceIDException{
        spaces = FileUtils.loadSpacesData(SPACES_DATA_FILE);

        if (spaces == null) {
            System.out.println("No spaces available at the moment.\n");
            return;
        }

        System.out.println("\nNew Reservaion: ");
        
        System.out.println("\nEnter your full name: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();
        
        System.out.println("Choose one of the available spaces: ");
        for (Spaces space : spaces) {
            if (space.getAvailable()) {
                System.out.println("Available Space ID: " + space.getSpaceID());
            }
        }
        int spaceID = scanner.nextInt();
        
        Spaces chosenSpace = null; 
        
        for (Spaces space: spaces) {
            if (space.getSpaceID() == spaceID && space.getAvailable()) {
                chosenSpace = space;
                break;
            }
        } 
        
        if (chosenSpace == null) {
            throw new InvalidSpaceIDException("Invalid ID or Space is not available.\n");
        }

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
    
        saveReservationsData();
    }

    private void cancelReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
            return;
        }

        System.out.println("\nEnter reservation ID to cancel: ");
        for (Reservations reservation : reservations) {
            System.out.println("Active reservation ID: " + reservation.getReservationID());
        }

        int id = scanner.nextInt();
        Reservations canceledReservation = null;

        for (Reservations reservation : reservations) {
            if (reservation.getReservationID() == id) {
                canceledReservation = reservation;
                break;
            }
        }

        if (canceledReservation == null) {
            System.out.println("Reservation not found.\n");
        } else {
            reservations.remove(canceledReservation);
            System.out.println("Reservation removed successfully!");

            for (Spaces space : spaces) {
                if (space.getSpaceID() == canceledReservation.getSpaceID()) {
                    space.setAvailable(true);
                    break;
                }
            }
        }
        
        saveReservationsData();    
    }

    private void viewMyReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("Your reservations:");

            for (Reservations reservation : reservations) {
                System.out.println(reservation);    
            }
        }
    }

    public void saveReservationsData() {
        FileUtils.saveReservationsData(reservations, RESERVATIONS_DATA_FILE);
    }
}