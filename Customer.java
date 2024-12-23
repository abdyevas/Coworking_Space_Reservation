import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<HashMap<String, Object>> spaces;
    private ArrayList<HashMap<String, Object>> reservations;
    private int reservationID = 0;

    public Customer(ArrayList<HashMap<String, Object>> spaces, ArrayList<HashMap<String, Object>> reservations) {
        this.spaces = spaces;
        this.reservations = reservations;
    }

    public void customerMenu() {
        while (true) {
            System.out.println("-".repeat(40));
            System.out.println("|   Customer Menu:                     |");
            System.out.println("|   1. Browse available spaces.        |");
            System.out.println("|   2. Make a reservation.             |");
            System.out.println("|   3. Cancel a reservation.           |");
            System.out.println("|   4. View my reservations.           |");
            System.out.println("|   5. Back to the Main Menu.          |");
            System.out.println("|   6. Exit.                           |");
            System.out.println("-".repeat(40));
            System.out.println("\nYour option: ");

            int optionCustomer = scanner.nextInt();

            if (optionCustomer == 1) {
                browseSpaces();
            } else if (optionCustomer == 2) {
                makeReservation();
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
        System.out.println("\nAvailable Coworking Spaces:");

        if (spaces.isEmpty()) {
            System.out.println("No spaces available at the moment.\n");
        } else {
            for (HashMap<String, Object> space: spaces) {
                System.out.println("\nID: " + space.get("spaceID") + "\nType: " + space.get("type") + 
                        "\nPrice: " + space.get("price") + " AZN"  + 
                        "\nAvailability: " + ((Boolean) space.get("isAvailable") ? "Available\n" : "Not available\n"));
            }
        }
    }

    private void makeReservation() {
        if (spaces.isEmpty()) {
            System.out.println("No spaces available at the moment.\n");
            return;
        }

        HashMap<String, Object> reservation = new HashMap<>();

        System.out.println("\nNew Reservaion: ");

        
        System.out.println("\nEnter your full name: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();
        
        System.out.println("Choose one of the available spaces: ");
        for (HashMap<String, Object> space : spaces) {
            if ((Boolean) space.get("isAvailable")) {
                System.out.println("Available Space ID: " + space.get("spaceID"));
            }
        }
        int spaceID = scanner.nextInt();
        
        System.out.println("Enter date (DD-MM-YYYY): ");
        String date = scanner.next();
        
        System.out.println("Enter start time (HH:MM): ");
        String startTime = scanner.next();
        
        System.out.println("Enter end time (HH:MM): ");
        String endTime = scanner.next();
        
        HashMap<String, Object> choosenSpace = null; 
        
        for (HashMap<String, Object> space: spaces) {
            if ((int) space.get("spaceID") == spaceID && (Boolean) space.get("isAvailable")) {
                choosenSpace = space;
                break;
            }
        } 
        
        if (choosenSpace == null) {
            System.out.println("Invalid ID or Space is not available.\n");
            return;
        }

        reservationID++; 
        reservation.put("reservationID", reservationID);
        reservation.put("name", customerName); 
        reservation.put("spaceID", spaceID);
        reservation.put("date", date);
        reservation.put("startTime", startTime);
        reservation.put("endTime", endTime);
        reservations.add(reservation);

        choosenSpace.put("isAvailable", false);
        System.out.println("Reservation made successfully!\n");
    }

    private void cancelReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
            return;
        }

        System.out.println("\nEnter reservation ID to remove: ");
        for (HashMap<String, Object> reservation : reservations) {
            System.out.println("Active reservation ID: " + reservation.get("reservationID"));
        }

        int id = scanner.nextInt();
        HashMap<String, Object> canceledReservation = null;

        for (HashMap<String, Object> reservation : reservations) {
            if ((int) reservation.get("reservationID") == id) {
                canceledReservation = reservation;
                break;
            }
        }

        if (canceledReservation == null) {
            System.out.println("Reservation not found.\n");
        } else {
            reservations.remove(canceledReservation);
            System.out.println("Reservation removed successfully!");
            for (HashMap<String, Object> space : spaces) {
                if ((int) space.get("spaceID") == (int) canceledReservation.get("reservationID")) {
                    space.put("isAvailable", true);
                    break;
                }
            }
        }
    }

    private void viewMyReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.\n");
        } else {
            System.out.println("Coworking Spaces:");

            for (HashMap<String, Object> reservation : reservations) {
                System.out.println("\nID: " + reservation.get("reservationID") + 
                    "\nSpace ID: " + reservation.get("spaceID") + "\nDate: " + reservation.get("date") + 
                    "\nTime: " + reservation.get("startTime") + " to " + reservation.get("endTime") + "\n");
            }
        }
    }
}