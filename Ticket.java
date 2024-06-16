import java.io.*;
public class Ticket {
    private String row;
    private int seat;
    private Person person;

    // Create ticket constructor
    public Ticket(String row, int seat, Person person) {
        this.row = row;
        this.seat = seat;
        this.person = person;
    }

    // Getters for row, seat, price and person
    public String getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return W2052117_PlaneManagement.calculatePrice(seat);
    }

    public Person getPerson() {
        return person;
    }

    // Setters for row, seat and price
    public void setRow(String row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void save() {
        // Create a file with row letter and seat number
        String fileName = row + seat + ".txt";

        try {
            // Create a file object
            File file = new File(row + seat);

            // Create FileWriter object with append mode
            FileWriter writer = new FileWriter(file, true);

            // Write ticket information to the file
            writer.write("Ticket Information : \n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + getPrice() + "\n");
            writer.write("\nPerson Information : \n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");

            // Close the FileWriter
            writer.close();

        } catch (IOException e) {
            System.out.println("Error occurred while saving ticket information.");
        }
    }
}