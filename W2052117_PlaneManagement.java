import java.util.InputMismatchException;
import java.util.Scanner;

public class W2052117_PlaneManagement {
    // Seat plan Array
    private static char[][] seats = {
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'}
    };

    // Ticket Array to save ticket data
    private static Ticket[][] tickets = new Ticket[4][14];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nWELCOME TO PLANE MANAGEMENT APPLICATION");

        // User menu
        System.out.println("\n**************************************************");
        System.out.println("*                  MENU OPTION                   *");
        System.out.println("**************************************************");
        System.out.println("1) Buy a seat");
        System.out.println("2) Cancel a seat");
        System.out.println("3) Find first available seat");
        System.out.println("4) Show seating plan");
        System.out.println("5) Print tickets information and total sales");
        System.out.println("6) Search ticket");
        System.out.println("0) Quit");
        System.out.println("**************************************************");

        while (true) {
            int choice = 0;

            try {
                System.out.print("\nEnter your choice: ");
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid integer number between 0 - 6");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_ticket_info();
                    break;
                case 6:
                    search_ticket(input);
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
            }
        }
    }

    public static void buy_seat() {
        // Declare variables
        Scanner input = new Scanner(System.in);
        String rowLetter = "";
        boolean rowValidation = false;
        int seatNumber = 0;
        boolean seatValidation = false;
        int row;
        int price;
        Person personOne = new Person("", "", "");

        System.out.print("\nInsert a row letter (A, B, C, or D): ");

        // Validate the row letter
        while (!rowValidation) {
            try {
                rowLetter = input.next().toUpperCase();

                if (rowLetter.matches("[ABCD]")) {
                    rowValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid row letter");
                }
            } catch (IllegalArgumentException e) {
                System.out.print("\nInsert a row letter (A, B, C, or D): ");
                input.nextLine();
            }
        }

        // Convert row letters to numbers
        switch (rowLetter) {
            case "A":
                row = 0;
                break;
            case "B":
                row = 1;
                break;
            case "C":
                row = 2;
                break;
            case "D":
                row = 3;
                break;
            default:
                System.out.println("Invalid row letter.");
                return;
        }

        // Validate seat number
        while (!seatValidation) {
            try {
                System.out.print("Insert a seat number (1-14 for row A/D, 1-12 for row B/C): ");
                seatNumber = input.nextInt();

                if ((row == 0 || row == 3) && seatNumber >= 1 && seatNumber <= 14) {
                    seatValidation = true;
                } else if ((row == 1 || row == 2) && seatNumber >= 1 && seatNumber <= 12) {
                    seatValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid seat number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number ");
                input.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        input.nextLine();

        // Setting person details
        System.out.print("Enter your name : ");
        String name = input.nextLine();
        personOne.setName(name);

        System.out.print("Enter your surname : ");
        String surname = input.nextLine();
        personOne.setSurname(surname);

        System.out.print("Enter your email : ");
        String email = input.nextLine();
        personOne.setEmail(email);

        // Getting person details
        System.out.println("\nName : " + personOne.getName());
        System.out.println("Surname : " + personOne.getSurname());
        System.out.println("Email : " + personOne.getEmail());

        // seat pricing calculation
        if (seatNumber <= 5) {
            price = 200;
            System.out.println("Price - £" + price);

        } else if (seatNumber <= 9) {
            price = 150;
            System.out.println("Price - £" + price);

        } else {
            price = 180;
            System.out.println("Price - £" + price);
        }

        // Check the row and seat number already booked or not using tickets array
        if (tickets[row][seatNumber-1] != null) {
            System.out.println("Sorry, the seat is already booked.");
        } else {
            Ticket ticket = new Ticket(rowLetter, seatNumber, personOne);
            tickets[row][seatNumber-1] = ticket;
            seats[row][seatNumber - 1] = 'X';
            System.out.println("Your seat is booked.");

            // create a text file to save the ticket and person information
            ticket.save();
        }
    }

    private static void cancel_seat() {
        // Declare variables
        Scanner input = new Scanner(System.in);
        String rowLetter = "";
        boolean rowValidation = false;
        int seatNumber = 0;
        boolean seatValidation = false;
        int row;

        System.out.print("\nInsert a row letter (A, B, C, or D): ");

        // Validate seat number
        while (!rowValidation) {
            try {
                rowLetter = input.next().toUpperCase();

                if (rowLetter.matches("[ABCD]")) {
                    rowValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid row letter");
                }
            } catch (IllegalArgumentException e) {
                System.out.print("\nInsert a row letter (A, B, C, or D): ");
                input.nextLine();
            }
        }

        // Convert row to numbers
        switch (rowLetter) {
            case "A":
                row = 0;
                break;
            case "B":
                row = 1;
                break;
            case "C":
                row = 2;
                break;
            case "D":
                row = 3;
                break;
            default:
                System.out.println("Invalid row letter.");
                return;
        }

        // Validate seat number
        while (!seatValidation) {
            try {
                System.out.print("Insert a seat number (1-14 for row A/D, 1-12 for row B/C): ");
                seatNumber = input.nextInt();

                if ((row == 0 || row == 3) && seatNumber >= 1 && seatNumber <= 14) {
                    seatValidation = true;
                } else if ((row == 1 || row == 2) && seatNumber >= 1 && seatNumber <= 12) {
                    seatValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid seat number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number ");
                input.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Check if the seat is booked and cancelling the seat
        if (tickets[row][seatNumber-1] == null) {
            System.out.println("Sorry, the seat is already available.");
        } else {
            Ticket canceledTicket = tickets[row][seatNumber -1];
            tickets[row][seatNumber-1] = null;
            seats[row][seatNumber - 1] = '0';
            System.out.println("The seat is now available.");
        }
    }

    private static void find_first_available() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == '0') {
                    char rowLetter = (char) ('A' + i);
                    System.out.println("The first available seat is in row " + rowLetter + ", seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    private static void show_seating_plan() {
        // printing seating plan
        System.out.println("Seating Plan:");
        for (int i = 0; i < seats.length; i++) {
            char rowLetter = (char) ('A' + i);
            System.out.print(rowLetter + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void print_ticket_info() {
        // Declare variables
        int totalSales = 0;

        for (int i = 0; i < tickets.length; i++) {
            for (int j = 0; j < tickets[i].length; j++) {
                Ticket ticket = tickets[i][j];
                if (ticket != null) {
                    // Getting ticket information using getters
                    String row = ticket.getRow();
                    int seat = ticket.getSeat();
                    double price = ticket.getPrice();

                    // Print ticket information
                    System.out.println("Ticket for seat " + row + seat + " - £" + price);

                    // Calculate total sales
                    totalSales += price;
                }
            }
        }

        System.out.println("Total sales - £" + totalSales);
    }

    public static int calculatePrice(int seatNumber) {
        // Declare variables
        int price;

        // Seat price calculation
        if (seatNumber >= 1 && seatNumber <= 5) {
            price = 200;
        }
        else if (seatNumber >= 6 && seatNumber <= 9) {
            price = 150;
        }
        else if (seatNumber >= 10 && seatNumber <= 14) {
            price = 180;
        }
        else {
            System.out.println("Invalid seat number.");
            return -1;
        }
        return price;
    }

    public static void search_ticket(Scanner input) {
        // Declare variables
        boolean rowValidation = false;
        String rowLetter = "";
        int row;
        int seatNumber = 0;
        boolean seatValidation = false;

        System.out.print("\nInsert a row letter (A, B, C, or D): ");

        // Validate row letter
        while (!rowValidation) {
            try {
                rowLetter = input.next().toUpperCase();

                if (rowLetter.matches("[ABCD]")) {
                    rowValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid row letter");
                }
            } catch (IllegalArgumentException e) {
                System.out.print("\nInsert a row letter (A, B, C, or D): ");
                input.nextLine();
            }
        }

        // Convert row letter to numbers
        switch (rowLetter) {
            case "A":
                row = 0;
                break;
            case "B":
                row = 1;
                break;
            case "C":
                row = 2;
                break;
            case "D":
                row = 3;
                break;
            default:
                System.out.println("Invalid row letter.");
                return;
        }

        // Validate seat number
        while (!seatValidation) {
            try {
                System.out.print("Insert a seat number (1-14 for row A/D, 1-12 for row B/C): ");
                seatNumber = input.nextInt();

                if ((row == 0 || row == 3) && seatNumber >= 1 && seatNumber <= 14) {
                    seatValidation = true;
                } else if ((row == 1 || row == 2) && seatNumber >= 1 && seatNumber <= 12) {
                    seatValidation = true;
                } else {
                    throw new IllegalArgumentException("Please enter a valid seat number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number ");
                input.next(); // Consume the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Show ticket information using ticket class
        if (tickets[row][seatNumber-1] != null) {
            Ticket ticket = tickets[row][seatNumber-1];
            System.out.println("\nTicket information:");
            System.out.println("Row: " + ticket.getRow());
            System.out.println("Seat: " + ticket.getSeat());
            System.out.println("Price: £" + ticket.getPrice());
            System.out.println("\nPerson Details : ");
            ticket.getPerson().showPerson();
        }
        else {
            System.out.println("This seat is available.");
        }
    }
}