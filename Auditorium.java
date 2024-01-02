
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Auditorium {
    // Private member variables
    private Node First;        // Reference to the first node in the linked structure
    private int rowCount;      // The number of rows in the auditorium
    private int colSize;       // The number of columns in the auditorium

    // Constructor to initialize the auditorium from a file
    public Auditorium(String FileName) throws IOException {
        rowCount = 0;
        colSize = 0;
        First = null;           // Initially, the first node is null
        Node tail = null;       // A temporary tail node for row-wise traversal
        Node prevHead = null;   // Head of the previous row
        Node currHead = null;   // Head of the current row
        File A = new File(FileName);
        Scanner in = new Scanner(A);

        // Check if the file exists
        if (!(A.exists())) {
            System.out.println("File failed");
        }
        else {
            int count = 0;
            // Loop through each line in the file
            while (in.hasNextLine()) {
                rowCount++;
                String line = in.nextLine();
                colSize = line.length();
                // Loop through each character in the line
                for (int i = 0; i < line.length(); i++) {
                    Node curr = new Node((char) (65 + i), line.charAt(i), count);

                    // Set the first node
                    if (i == 0 && First == null) {
                        First = curr;
                        tail = curr;
                        prevHead = curr;
                        currHead = curr;
                    } else if (i == 0) {
                        currHead = curr;
                    }
                    // Linking nodes row-wise and column-wise
                    if (tail != null) {
                        tail.setNext(curr);
                    }
                    tail = curr;
                    if (prevHead != null) {
                        prevHead.setDown(curr);
                        prevHead = prevHead.getNext();
                    }
                }
                // Moving to the next row
                if (currHead != null) {
                    prevHead = currHead;
                    currHead = null;
                }
                count++;
            }
        }
        in.close();
    }

    // Method to display the auditorium seating
    public void displayAudi(){
        String period = ".";
        Node curr = First;
        int count = 1;

        // Printing column headers
        System.out.print("  ");
        for(int i = 65; i < colSize + 65; i++)
        {
            char c = (char) i;
            System.out.print(c);
        }
        System.out.println();

        // Printing the seating layout
        int row = First.getRow();
        System.out.print(count + " ");
        count++;
        while(curr != null){
            if(!(period.equals(curr.toString()))){
                System.out.print('#'); // Seat is taken
            }
            else{
                System.out.print('.'); // Seat is available
            }
            curr = curr.getNext();
            // Moving to the next row
            if(curr != null && row != curr.getRow()){
                System.out.println();
                System.out.print(count + " ");
                count++;
                row = curr.getRow();
            }
        }
        System.out.println();
    }

    // Method to reserve seats
    public void reserveSeat(int row, int A, int S, int C, int seat){
        int total = A + S + C;
        Node Curr = First;
        char seatNum = (char) ((char)65 + seat);
        // Find the starting seat
        while(Curr.getRow() != row || Curr.getChar() != seatNum){
            Curr = Curr.getNext();
        }
        // Reserve the seats
        while(total != 0) {
            if (A != 0) {
                Curr.setSeat('A');
                A--;
            } else if (C != 0) {
                Curr.setSeat('C');
                C--;
            } else if (S != 0) {
                Curr.setSeat('S');
                S--;
            }
            Curr = Curr.getNext();
            total--;
        }
    }

    // Method to check if a seat is available
    public Boolean checkSeat(int total, int row, int seat){
        Node Curr = First;
        char seatNum = (char) ((char)65 + seat);
        // Find the starting seat
        while(Curr.getRow() != row || Curr.getChar() != seatNum){
            Curr = Curr.getNext();
        }
        // Check availability
        while(total != 0){
            if(Curr.getTicketType() != '.'){
                return false;
            }
            Curr = Curr.getNext();
            total--;
        }
        return true;
    }

    // Method to generate a sales report
    public int[] displayRep() {
        int adult = 0;
        int child = 0;
        int senior = 0;
        int count = 0;
        Node curr = First;
        // Counting tickets
        while(curr!=null){
            char T = curr.getTicketType();
            if(T == 'A'){
                adult++;
            }
            else if(T == 'C'){
                child++;
            }
            else if(T == 'S'){
                senior++;
            }
            curr = curr.getNext();
            count++;
        }
        int[] word = {adult, child, senior, count};
        return word;
    }

    // Method to find the best seats
    public Node bestSeats(int total) {
        int middleRow = (rowCount / 2) + 1;
        double middleCol = colSize / 2.0;
        double minDistance = Double.MAX_VALUE;
        int closestRowDifference = Integer.MAX_VALUE;
        Node startSeat = null;

        Node currentRow = First;
        int currentRowIndex = 1;

        // Loop through each row
        while (currentRow != null) {
            Node currentSeat = currentRow;

            // Loop through each seat in the row
            for (int i = 0; i <= colSize - total; i++) {
                boolean allSeatsEmpty = true;
                Node checker = currentSeat;

                // Check if all seats in the range are empty
                for (int j = 0; j < total; j++) {
                    if (checker.getTicketType() != '.') {
                        allSeatsEmpty = false;
                        break;
                    }
                    checker = checker.getNext();
                }

                // Calculate the distance of the seat selection from the middle
                if (allSeatsEmpty) {
                    double midSeatOfSelection = (i + (total/2.0));
                    double distance = Math.sqrt(Math.pow(middleRow - currentRowIndex, 2) + Math.pow(middleCol - midSeatOfSelection, 2));
                    int rowDifference = Math.abs(currentRowIndex - middleRow);

                    // Update if this selection is closer
                    if (distance < minDistance || (distance == minDistance && rowDifference < closestRowDifference)) {
                        minDistance = distance;
                        closestRowDifference = rowDifference;
                        startSeat = currentSeat;
                    }
                }

                currentSeat = currentSeat.getNext();
            }

            currentRow = currentRow.getDown();
            currentRowIndex++;
        }

        return startSeat;
    }

    // Getter methods for row and column counts
    public int getRows(){
        return rowCount;
    }
    public int getCol(){
        return colSize;
    }

    // Method to reset a seat
    public char resetSeat(String remove){
        int findRow = remove.charAt(0) - '0';
        findRow--;
        char findCol = remove.charAt(1);
        Node curr = First;
        // Find the specified seat
        while(curr.getRow() != findRow){
            curr = curr.getDown();
        }
        while(curr.getChar() != findCol){
            curr = curr.getNext();
        }
        char C = curr.getTicketType();
        curr.setSeat('.');  // Reset the seat
        return C;
    }

}
