
public class Node {
    // Private member variables
    private Seat Payload;  // The 'Seat' object stored in this node
    private Node Next;     // Reference to the next node in the linked list
    private Node Down;     // Reference to the downward node in a 2D linked list

    // Constructor to initialize the node with seat details
    public Node(char col, char tick, int r){
        Payload = new Seat(col, tick, r);  // Initializes Payload with a new Seat
        Next = null;                       // Initially, the next node is set to null
        Down = null;                       // Initially, the down node is set to null
    }

    // Setter method for the Next node
    public void setNext(Node A){
        Next = A;  // Sets the Next node to the specified node A
    }

    // Setter method for the Down node
    public void setDown(Node D){
        Down = D;  // Sets the Down node to the specified node D
    }

    // Getter method for the Next node
    public Node getNext(){
        return Next;  // Returns the next node
    }

    // Getter method for the Down node
    public Node getDown(){
        return Down;  // Returns the down node
    }

    // Method to get the seat information as a String
    public String getSeat(){
        String row = String.valueOf(Payload.getRow() + 1); // Gets the row number, adds 1 for human-readable format
        row += Character.toString(Payload.getColChar());    // Appends the column character to the row string
        return row;                                        // Returns the seat information
    }

    // Getter method for the row number
    public int getRow(){
        return Payload.getRow();  // Returns the row number from Payload
    }

    // toString method overridden to return the string representation of Payload
    public String toString(){
        return Payload.toString();  // Returns the string representation of Payload
    }

    // Getter method for the column character
    public char getChar(){
        return Payload.getColChar();  // Returns the column character from Payload
    }

    // Getter method for the ticket type
    public char getTicketType(){
        return Payload.getSeat();  // Returns the ticket type from Payload
    }

    // Setter method for the Seat character
    public void setSeat(char X){
        Payload.setSeat(X);  // Sets the seat character in Payload
    }

    // An alternative string representation of the Node
    public String toString2(){
        return "" + (Payload.getRow() + 1) + Payload.getColChar();  // Returns a string representation combining row and column
    }
}
