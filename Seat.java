
public class Seat {
    // Private member variables
    private char colChar;     // Character representing the column of the seat
    private char ticketType;  // Character representing the type of ticket
    private int row;          // Integer representing the row number of the seat

    // Default constructor
    public Seat(){
        ticketType = 'X';    // Default ticket type set to 'X'
        colChar = '!';       // Default column character set to '!'
        row = -1;            // Default row number set to -1
    }

    // Constructor with parameters for seat, ticket type, and row
    public Seat(char seat, char ticket, int r){
        ticketType = ticket; // Sets the ticket type
        colChar = seat;      // Sets the column character
        row = r;             // Sets the row number
    }

    // Setter method for ticket type
    public void setSeat(char t) {
        ticketType = t;      // Sets the ticket type to the specified character
    }

    // Getter method for ticket type
    public char getSeat(){
        return ticketType;   // Returns the ticket type
    }

    // Getter method for column character
    public char getColChar(){
        return colChar;      // Returns the column character
    }

    // Getter method for row number
    public int getRow(){
        return row;          // Returns the row number
    }

    // Overridden toString method
    public String toString() {
        return String.valueOf(ticketType); // Returns the string representation of the ticket type
    }
}
