
import java.util.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static class Orders{
        private int Auditorium; // What auditorium the order is present in
        private int adult; // number of adult tickets
		private int child; // number of child tickets
		private int senior; // number of senior tickets
        private ArrayList<String> seats; // List of seats
        Orders(int A, int aT, int cT, int sT, int T, String C){ // overloaded constructor
            Auditorium = A;
            adult = aT;
            child = cT;
            senior = sT;
            seats = new ArrayList<>();
            char columnString = C.charAt(1);
            while(T != 0){// adding seats via adding 1 to the ASCII value of the char representing column
                seats.add("" + C.charAt(0) + "" + (columnString));
                columnString = (char)(columnString + 1);
                T--;
            }
        }
        public int getAuditorium(){ // Getter
            return Auditorium;
        }

        public ArrayList<String> getSeats(){ // Getter
            return seats;
        }

        public void addO(String S){ // Mutator
            seats.add(S);
        }

        public void removeO(String S){ // Mutator
            seats.remove(S);
        }
        public int getAdult(){ // Getter
            return adult;
        }

        public int getSenior(){ // Getter
            return senior;
        }

        public int getChild(){ // Getter
            return child;
        }

        public void decrementChild(){ // Mutator
            child--;
        }

        public void decrementAdult(){ // Mutator
            adult--;
        }

        public void decrementSenior(){ // Mutator
            senior--;
        }

        public void setChild(int A){ // Setter
            child = A;
        }
        public void setAdult(int A){ // Setter
            adult = A;
        }
        public void setSenior(int A){ // Setter
            senior = A;
        }

    }
    public static class User{
        private String login, password; // Strings holding a User's login information
        ArrayList<Orders> Orders; // List of orders
        User(String L, String P){ // Overloaded constructor
            login = L;
            password = P;
            Orders = new ArrayList<>();
        }

        public String getPassword(){ // Getter
            return password;
        }

        public String getName(){ // Getter
            return login;
        }

        public ArrayList<Orders> getOrders(){ // Getter
            return Orders;
        }
        
        public void addOrder(Orders C){ // Mutator
            Orders.add(C);
        }
    }

    public static int convertCol(char seat) { // Convert a character (Column) to an Integer (index of seat + 1)
        return seat - 'A' + 1;
    }

    public static void displayRep(ArrayList <Auditorium> A){ // Admin Menu - display data for all auditoriums
        int totalAdult = 0; // initialize to 0
        int totalChild = 0; // initialize to 0
        int totalSenior = 0; // initialize to 0
        int totalOpen = 0; // initialize to 0
        int totalReserved = 0; // initialize to 0
        double totalCost = 0; // initialize to 0
        DecimalFormat df = new DecimalFormat("0.00"); // format for output
        for(int i = 0; i < A.size(); i++){ // Outer for loop iterating over the Auditoriums
            int adultT, childT, seniorT, open, reserved = 0; // store data for that auditorium
            double total = 0.0; // store data for that auditorium
            int[] B = A.get(i).displayRep(); // displayRep() has been modified to return the amount of each ticket type
            adultT = B[0]; // set value
            childT = B[1]; // set value
            seniorT = B[2]; // set value
            reserved = adultT + childT + seniorT; // total amount of reserved seats
            open = B[3] - reserved; // total seats minus amount of reserved seats
            total = (seniorT * 7.5) + (adultT * 10.00) + (childT * 5.00); // calculate the cost for this order
            totalCost+=total; // add the cost for this order to the cost for all orders
            totalAdult+=adultT; // add the adult tickets for this order to the amount for all orders
            totalChild+=childT; // add the child tickets for this order to the amount for all orders
            totalSenior+=seniorT; // add the senior tickets for this order to the amount for all orders
            totalOpen+=open; // add the amount of open seats for this order to the amount for all orders
            totalReserved+=reserved; // add the amount of reserved seats for this order to the amount for all orders
            // write to console
            System.out.print("Auditorium " + (i + 1) + "\t" + (open) + "\t" + (reserved) + "\t" + (adultT) + "\t" + (childT) + "\t" + seniorT + "\t$" + df.format(total)  + "\n");
        }
         System.out.print("Total " + "\t\t" + (totalOpen) + "\t" + (totalReserved) + "\t" + (totalAdult) + "\t" + (totalChild) + "\t" + (totalSenior)  + "\t$" + df.format(totalCost) + "\n");
    }

    public static HashMap<String, User> createHash(){ //Create and populate hashmap
        HashMap<String, User> A = new HashMap<>(); // initialize empty hashmap
        try{
            Scanner fileRead = new Scanner(new File("userdb.dat")); // read in from file
            while(fileRead.hasNextLine()){// while there are lines to read
                String[] line = fileRead.nextLine().split(" "); // string array to hold parsed input
                User B = new User(line[0], line[1]); // create user object
                A.put(B.getName(), B); // add key and user object to hashmap
            }
        }
        catch (FileNotFoundException X){ // if the file is not found
            System.out.println("Error: Unable to open file."); // notify the user
        }
        return A; // return hashmap to main
    }

    public static void makeOrder(Scanner sc, Auditorium test, User B, int chosenAuditorium, Boolean Update, Orders chosenOrder){ // Customer Menu - Reserve Seats & Update Order
        int adultT, childT, seniorT; // initialize variables
        test.displayAudi(); // display Auditorium to user
        int chosenRow = -1; // initialize variables
        char seat; // initialize variables
        System.out.print("Enter starting row: "); // Prompt user for input
        while (true) { // while loop iterating until valid input
            try {
                chosenRow = Integer.parseInt(sc.nextLine().trim()); // Parse next line
                if (chosenRow>  0 && chosenRow <= test.getRows()) { // upon valid input, break out of loop
                    break;
                }
                System.out.println();
            }
            catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                System.out.println("Please enter a valid row number."); // Prompt user for input
                }
            }
        System.out.print("Enter starting seat: "); // Prompt user for input
        while (true) { // while loop iterating until valid input
            try {
                String seatString = sc.nextLine(); // String length will be used for validation
                seat = seatString.charAt(0); // Char value will be used for validation
                if (convertCol(seat) >= 1 && convertCol(seat) <= test.getCol() && seatString.length() == 1) { // upon valid input, break out of loop
                    break;
                }
                System.out.println();
            }
            catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                System.out.println("Please enter a valid seat."); // Prompt user for input
            }
        }
        System.out.print("Enter number of adult tickets: "); // Prompt user for input
        while (true) { // while loop iterating until valid input
            try {
                adultT = Integer.parseInt(sc.nextLine().trim()); // read in value
                if (adultT >= 0) { // upon valid input, break out of loop
                    break;
                }
                System.out.println();
            }
            catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                System.out.println("Please enter a valid number of adult tickets."); // Prompt user for input
            }
        }
        System.out.print("Enter number of child tickets: "); // Prompt user for input
        while (true) { // while loop iterating until valid input
            try {
                childT = Integer.parseInt(sc.nextLine().trim()); // read in value
                if (childT >= 0) { // upon valid input, break out of loop
                    break;
                }
                System.out.println();
            }
            catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                System.out.println("Please enter a valid number of child tickets."); // Prompt user for input
            }
        }
        System.out.print("Enter number of senior tickets: "); // Prompt user for input
        while (true) { // while loop iterating until valid input
            try {
                seniorT = Integer.parseInt(sc.nextLine().trim()); // read in value
                if (seniorT >= 0) { // upon valid input, break out of loop
                    break;
                }
                System.out.println();
            }
            catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                System.out.println("Please enter a valid number of senior tickets.");
            }
        }
        if(test.checkSeat(adultT + seniorT + childT, chosenRow - 1, convertCol(seat)- 1)){// if the seats are available
            test.reserveSeat(chosenRow-1, adultT, seniorT, childT, convertCol(seat)-1); // reserve them in the auditorium
            if(!Update){ // if it is not updating an order
                B.addOrder(new Orders(chosenAuditorium, adultT, childT, seniorT,adultT + childT + seniorT, "" + chosenRow + seat)); // add the order to the user's preexisting orders
            }
            else{ // else, it is updating an order
                while(adultT + childT + seniorT != 0){
                    chosenOrder.addO("" + chosenRow + seat); // add the first seat
                    seat++; // increment to add the next seat
                    if(adultT != 0){
                        chosenOrder.setAdult(chosenOrder.getAdult() + 1); // increment count of adults in the order
                        adultT--;
                        continue;
                    }
                    if(childT != 0){
                        chosenOrder.setChild(chosenOrder.getChild() + 1);  // increment count of children in the order
                        childT--;
                        continue;
                    }
                    if(seniorT != 0){
                        chosenOrder.setSenior(chosenOrder.getSenior() + 1);  // increment count of seniors in the order
                        seniorT--;
                    }
                }
            }
        }
        else{
            if(!Update){ // if this is not updating an order
                System.out.println("Seats not available");
                Node newSeat = test.bestSeats(adultT + seniorT + childT); // find the best seat
                Node lastSeat = newSeat; // Pointer will be used to print the final seat of the new reservation
                if (newSeat != null) {
                    for(int i = 0; i < adultT + seniorT + childT - 1; i++) {
                        lastSeat = lastSeat.getNext(); // get to the true final seat
                    }
                    if (adultT + childT + seniorT == 1) { // if there is only one seat in the order
                        System.out.println(newSeat.toString2()); // write to console
                    }
                    else { // if there are multiple seats
                        System.out.println(newSeat.toString2() + " - " + lastSeat.toString2()); // write to console
                    }
                    System.out.println("Would you like to reserve these seats? Yes or No"); // prompt user
                    char response = sc.nextLine().charAt(0); // read in response
                    if (response == 'Y' || response == 'y') {
                        test.reserveSeat(newSeat.getRow(), adultT, seniorT, childT, convertCol(newSeat.getChar()) - 1); // reserve the seats in auditorium
                        B.addOrder(new Orders(chosenAuditorium, adultT, childT, seniorT,adultT + childT + seniorT, "" + newSeat.getSeat())); //add the order to the user's orders
                        System.out.println("\nSeats have been reserved"); // notify user
                    }
                }
            }
            else{
                System.out.println("Seats not available!"); // if this is an update order and the seats were not available, notify the user
            }
        }
    }
    
    public static void displayReceipt(User B){ // Customer Menu - Display Receipt
        ArrayList<Orders> A = B.getOrders();
        if(A.isEmpty()){ // if the user has no orders
            System.out.println("Customer Total: $0.00");
        }
        else{ // if the user has orders
            double total, bigTotal = 0; // initialize variables
            for (Orders orders : A) { // iterate through the orders
                ArrayList<String> seats = orders.getSeats(); // get list of seats from order
                System.out.print("Auditorium " + orders.getAuditorium() + ", "); // Print Auditorium of order
                for (int x = 0; x < seats.size(); x++) { // iterate through the seats within the order
                    System.out.print(seats.get(x)); // Print seats
                    if (x + 1 < seats.size()) { // if it is not the last order
                        System.out.print(","); // then print comma
                    }
                }
                System.out.print("\n" + orders.getAdult() + " adult, " + orders.getChild() + " child, " + orders.getSenior() + " senior\n"); // Print the amount of each ticket type
                total = (orders.getChild() * 5.0) + (orders.getSenior() * 7.5) + (orders.getAdult() * 10.0); // calculate the total cost of the order
                bigTotal += total; // add the cost to the customer's total
                System.out.printf("Order Total: $%.2f\n", total); // Print the total cost of this order
            }
            System.out.printf("\nCustomer Total: $%.2f\n", bigTotal); // Print the customer's total
        }
    }

    public static void viewOrder(User B){ // Customer Menu - View Orders
        ArrayList<Orders> A = B.getOrders(); // Retrieve orders from the User object
        if(A.isEmpty()){ // if there are no orders
            System.out.println("No orders");
        }
        else{ // if there are order(s)
            // get the current order
            for (Orders C : A) { // iterate through the orders
                System.out.print("Auditorium " + C.getAuditorium() + ", "); // Print the auditorium for the order
                ArrayList<String> test = C.getSeats(); // Retrieve the seats for the order
                for (int x = 0; x < test.size(); x++) { // iterate through the seats
                    System.out.print(test.get(x)); // Print the seat to the console
                    if (x + 1 < test.size()) { // if it is not the last seat
                        System.out.print(","); // Print a comma
                    }
                }
                System.out.print("\n" + C.getAdult() + " adult, " + C.getChild() + " child, " + C.getSenior() + " senior\n"); // Print the amount of each ticket type
            }
        }
    }

    public static void displayOrderList(User B){
        ArrayList<Orders> A = B.getOrders(); // Retrieve orders from the User object
        if(A.isEmpty()){ // if there are no orders
            System.out.println("No orders");
        }
        else{ // if there are order(s)
            // get the current order
            for (Orders C : A) { // iterate through the orders
                System.out.print("Auditorium " + C.getAuditorium() + ", "); // Print the auditorium for the order
                ArrayList<String> test = C.getSeats(); // Retrieve the seats for the order
                for (int x = 0; x < test.size(); x++) { // iterate through the seats
                    System.out.print(test.get(x)); // Print the seat to the console
                    if (x + 1 < test.size()) { // if it is not the last seat
                        System.out.print(","); // Print a comma
                    }
                }
                System.out.print("\n"); // Spacing
            }
        }
    } // big change here
    public static Comparator<String> seatComparator = (s1, s2) -> {
        // used to sort the Seats arraylist within the Order object
        // Point of sorting is so that the seats print in chronological order (1B, 1C instead of 1C, 1B)
        // Parses the string s1 to extract the numerical part (assuming the last character is a letter)
        int number1 = Integer.parseInt(s1.substring(0, s1.length() - 1));
        // Parses the string s2 in the same way to extract the numerical part
        int number2 = Integer.parseInt(s2.substring(0, s2.length() - 1));

        // Compares the numerical parts of the strings
        if (number1 != number2) {
            // If they are not equal, returns the difference (this effectively sorts by the numerical part)
            return number1 - number2;
        } else {
            // If the numerical parts are equal, compares the last character of the strings
            // This character is expected to be a letter, and this comparison sorts alphabetically
            return s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1);
        }
    };

    public static void main(String[] args) throws IOException, InputMismatchException {
        Scanner sc = new Scanner(System.in); // initialize scanner
        ArrayList<Auditorium> Auditoriums = new ArrayList<>(Arrays.asList(
                new Auditorium("A1.txt"),
                new Auditorium("A2.txt"),
                new Auditorium("A3.txt")
        )); // initialize Auditoriums within ArrayList
        HashMap<String,User> A = createHash(); // catch the returned hashmap
        Boolean valid = false; // initialize variables
        Boolean exit = false; // initialize variables
        Boolean innerLoop = true; // initialize variables
        String user, pass; // initialize variables
        int failedLogins = 0; // initialize variables
        while(!valid){ // Boolean only changed via Admin Menu - Exit | only way to exit the program
                failedLogins = 0; // initialize variables
                exit = false; // initialize variables
                User B; // initialize variables
                System.out.println("Enter username: "); // Prompt user
                user = sc.nextLine(); // read input
                if(A.containsKey(user)){ // if the user is found within the hashmap
                    B = A.get(user); // get the user
                    while(failedLogins < 3){ // loop until either the correct password is entered or too many failed attempts
                        System.out.println("Enter password: "); // Prompt user
                        pass = sc.nextLine(); // read input
                        if(pass.equals(B.getPassword())){ // if the password is the User's password
                            exit = false; // Enter the Admin / Customer loop
                            failedLogins = 0; // Reset Value
                            break; // Break out of loop
                        }
                        System.out.println("Invalid password"); // Invalid password, notify user
                        failedLogins++; // Increment invalid password count
                        exit = true; // Prevent entering the Admin / Customer loop | reset value
                    }
                }
                else{
                    System.out.println("username does not exist, try again"); // user was not found within hashmap
                    continue; // return to start of loop
                }
                while(!exit){ // while the user has not logged out
                    if(user.equals("admin")){ // if the user was an admin
                        int choice = 0; // initialize variable
                        System.out.println("1. Print Report\n2. Logout\n3. Exit"); // prompt user
                        try{
                            choice = Integer.parseInt(sc.nextLine().trim()); // read input
                            if(choice == 1){ // Admin Menu - Print Report
                                displayRep(Auditoriums);
                            }
                            if(choice == 2){ // Admin Menu - Logout
                                break;
                            }
                            if(choice == 3){ // Admin Menu - Exit (End Of Program)
                                exit = true;
                                valid = true;
                            }
                        }
                        catch(InputMismatchException | NumberFormatException X){ // catch exceptions
                            System.out.println("Invalid input"); // notify user prior to looping again
                        }
                    }
                    else{ // if the user is a customer
                        int choice = 0; // initialize variable
                        System.out.println("1. Reserve Seats\n2. View Orders\n3. Update Order\n4. Display Receipt\n5. Log Out"); // Customer Menu
                        while(true){ // validation loop, broken upon valid input
                            try{
                                choice = Integer.parseInt(sc.nextLine().trim()); // read input
                                if(choice > 0 && choice < 6){ // valid input
                                     break; // break out of validation loop
                                }
                            }
                            catch(InputMismatchException | NumberFormatException ABC){ // catch exceptions
                                System.out.println("Invalid input");
                            }
                        }
                        if (choice == 1) { // Customer Menu - Reserve Seats
                            int chosenAuditorium = 0; // Initialize variable
                            System.out.println("choose an auditorium");
                            while(true){ // validation loop, broken upon valid input
                                try{
                                    chosenAuditorium = Integer.parseInt(sc.nextLine().trim()); // read input
                                    if((chosenAuditorium >= 1 && chosenAuditorium <= 3)){ // valid input
                                        break; // break out of validation loop
                                    }
                                    else{
                                        System.out.println("Invalid input"); // notify user
                                    }
                                }
                                catch(NumberFormatException ABC){ // catch exceptions
                                    System.out.println("Invalid input"); // notify user
                                }
                            }
                            Auditorium test = Auditoriums.get(chosenAuditorium - 1); // retrieve user's selected auditorium
                            makeOrder(sc, test, B, chosenAuditorium, false, null); // call method to prompt user to select seat
                            // false indicates this is a new order, not an update to an order
                            // null would normally be the order being updated, but since this is a new order it will be null
                        }
                        else if (choice == 2){ // Customer Menu - View Orders
                            viewOrder(B);
                        }
                        else if (choice == 3){ // Customer Menu - Update Order
                            innerLoop = true; // Seat selection is not valid - return to update order submenu
                            while(innerLoop){
                                innerLoop = false; // assume selection is valid
                                ArrayList<Orders> userOrders = B.getOrders(); // check for orders
                                if (userOrders.isEmpty()){ // if there are no orders
									System.out.println("No orders\n"); // notify user
									break; // end of loop
								}
                                displayOrderList(B); // since there are orders, display them to the user
                                System.out.println("Enter order number: "); // prompt the user
                                int oUpdate = -1; // initialize variable
                                while(true){ // validation loop, broken upon valid input
                                    try{
                                    oUpdate = Integer.parseInt(sc.nextLine()); // read input
                                    if((oUpdate > 0 && oUpdate < userOrders.size() + 1)){ // valid input
                                        break; // break validation loop
                                    }
                                }
                                    catch(InputMismatchException | NumberFormatException ABC){ // catch exceptions
                                        System.out.println("Invalid input"); // notify user
                                    }
                                }
                                Orders toUpdate = userOrders.get(oUpdate - 1); // Retrieve the order they will be updating
                                System.out.println("\n1. Add tickets to order\n2. Delete tickets from order\n3. Cancel order"); // prompt the user
                                while(true){ // validation loop, broken upon valid input
                                    try{
                                        choice = Integer.parseInt(sc.nextLine().trim()); // read input
                                        if(choice > 0 && choice < 4){ // valid input
                                            break; // break validation loop
                                        }
                                    }
                                    catch(InputMismatchException | NumberFormatException ABC){ // catch exceptions
                                        System.out.println("input mismatch, try again"); // notify user
                                    }
                                }
                                    System.out.println(); // space
                                if(choice == 1){ // Update Menu - Add tickets
                                    Auditorium test = Auditoriums.get(toUpdate.getAuditorium()- 1); // Retrieve auditorium from order object
                                    makeOrder(sc, test, B, choice, true, toUpdate); // Update bool is true, since this is updating an order (the one being passed in)
                                    Collections.sort(toUpdate.getSeats(),seatComparator); // Sort the seats to be 1B 1C instead of 1C 1B
                                }
                                else if(choice == 2){ // Update Menu - Delete tickets
                                    Auditorium test = Auditoriums.get(toUpdate.getAuditorium()- 1); // Retrieve auditorium from order object
                                    int chosenRow = -1; // initialize variable
                                    char seat; // initialize variable
                                    System.out.print("Enter starting row: ");
                                    try {
                                        chosenRow = Integer.parseInt(sc.nextLine());
                                        if (chosenRow < 0 || chosenRow > test.getRows()) { // invalid input
                                            innerLoop = true; // loop to Update sub menu
                                            continue; // continue to next iteration of loop
                                        }
                                        System.out.println();
                                    } 
                                    catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                                        System.out.println("Invalid row number."); // notify user
                                        innerLoop = true; // loop to Update sub menu
                                        continue; // continue to next iteration of loop
                                    }
                                    System.out.print("Enter starting seat: ");
                                    try {
                                        seat = sc.nextLine().charAt(0);
                                        if (convertCol(seat) < 1 || convertCol(seat) > test.getCol()) {
                                            innerLoop = true; // loop to Update sub menu
                                            continue; // continue to next iteration of loop
                                        }
                                    } 
                                    catch (InputMismatchException | NumberFormatException XD) { // catch exceptions
                                        System.out.println("Invalid seat."); // notify user
                                        innerLoop = true; // loop to Update sub menu
                                        continue; // continue to next iteration of loop
                                    }
                                    String findSeat = "" + chosenRow + "" + seat; // initialize variable
                                    toUpdate.removeO(findSeat); // remove seat from order
                                    char C = test.resetSeat(findSeat); // set seat within auditorium to '.'
                                    if(C == 'A'){ // if the seat was an adult, adjust adult ticket value accordingly
                                        toUpdate.decrementAdult();
                                    }
                                    else if(C == 'C'){ // if the seat was a child, adjust child ticket value accordingly
                                        toUpdate.decrementChild();
                                    }
                                    else{ // if the seat was a senior, adjust senior ticket value accordingly
                                        toUpdate.decrementSenior();
                                    }
                                    for(int i = 0; i < B.getOrders().size(); i++){ // iterate over the Customer's orders
                                        if(userOrders.get(i).getSeats().isEmpty()){ // if the order's arrayList of Seats is empty
                                            userOrders.remove(i); // remove the order from the customer object
                                        }
                                    }
                                }
                                else{ // Update Menu - Cancel Order
                                    Auditorium test = Auditoriums.get(toUpdate.getAuditorium()- 1); // Retrieve auditorium
                                    ArrayList<String> toUpdateSeats = toUpdate.getSeats(); // retrieve seats from order
                                    for (String toUpdateSeat : toUpdateSeats) { // for all sets within the order
                                        test.resetSeat(toUpdateSeat); // reset the seat within the auditorium
                                    }
                                    userOrders.remove(oUpdate- 1); // delete the order from the user's orders
                            }
                        }
                    }
                        else if(choice == 4){ // Customer Menu - Display Receipt
                            displayReceipt(B);
                        }
                        else{ // Customer Menu - Log Out
                            exit = true; // Breaks customer menu loop, takes you back to login menu
                        }
                    }
                }
        }
    }
}
