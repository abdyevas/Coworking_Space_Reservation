import exceptions.InvalidSpaceIDException;
import database.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import java.util.*;

public class Customer {
    private Scanner scanner = new Scanner(System.in);
    private SpacesDAO spacesDAO = new SpacesDAO();
    private ReservationsDAO reservationsDAO = new ReservationsDAO();

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
                    List<Map<String, Object>> allSpaces = spacesDAO.getAllSpaces();
                    allSpaces.stream()
                        .filter(space -> (boolean) space.get("available"))
                        .forEach(space -> System.out.println("Available Space ID: " + space.get("id")));

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
                List<Map<String, Object>> reservations = reservationsDAO.getAllReservations();
                reservations.forEach(reservation -> System.out.println("Active Reservation ID: " + reservation.get("id")));
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
        List<Map<String, Object>> spaces = spacesDAO.getAllSpaces();
        if (spaces.isEmpty()) {
            System.out.println("No spaces available at the moment.\n");
        } else {
            spaces.stream()
                .filter(space -> (boolean) space.get("available"))
                .forEach(space -> System.out.println(space));
        }
    }

    public void makeReservation(String customerName, int spaceID, String date, String startTime, String endTime) 
        throws InvalidSpaceIDException {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate;
        try {
            parsedDate = sdf.parse(date);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }
        Date sqlDate = new Date(parsedDate.getTime());

        Time sqlStartTime;
        Time sqlEndTime;
        try {
            sqlStartTime = Time.valueOf(startTime + ":00"); 
            sqlEndTime = Time.valueOf(endTime + ":00");     
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time format. Please use HH:MM.");
            return;
        }

        List<Map<String, Object>> spaces = spacesDAO.getAllSpaces();
        Optional<Map<String, Object>> chosenSpaceOpt = spaces.stream()
                .filter(space -> (int) space.get("id") == spaceID && (boolean) space.get("available"))
                .findFirst();

        if (chosenSpaceOpt.isEmpty()) {
            throw new InvalidSpaceIDException("Invalid ID or space is not available.\n");
        }

        reservationsDAO.addReservation(customerName, spaceID, sqlDate, sqlStartTime, sqlEndTime);
        spacesDAO.updateSpaceAvailability(spaceID, false);

        System.out.println("Reservation made successfully!\n");
    }

    public void cancelReservation(int reservationID) {
        List<Map<String, Object>> reservations = reservationsDAO.getAllReservations();
        Optional<Map<String, Object>> reservationOpt = reservations.stream()
            .filter(reservation -> (int) reservation.get("id") == reservationID)
            .findFirst();

        if (reservationOpt.isEmpty()) {
            System.out.println("Reservation not found.\n");
            return;
        }

        Map<String, Object> reservation = reservationOpt.get();
        int spaceID = (int) reservation.get("space_id");

        reservationsDAO.removeReservation(reservationID);
        spacesDAO.updateSpaceAvailability(spaceID, true);

        System.out.println("Reservation removed successfully!\n");
    }

    public void viewMyReservations() {
        List<Map<String, Object>> reservations = reservationsDAO.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("Your reservations:");
            reservations.forEach(reservation -> System.out.println(reservation));
        }
    }
}
