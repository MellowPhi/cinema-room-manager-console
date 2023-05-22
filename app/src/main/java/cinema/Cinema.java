package cinema;

import java.util.Scanner;

public class Cinema {
    /**
     * Limit seats for $10 ticket
     */
    static final int MAX_SEATS = 60;

    /**
     * Normal ticket price for the front half
     */
    static final int TICKET_NORMAL_PRICE = 10;

    /**
     * Low ticket price for the back half
     */
    static final int TICKET_LOW_PRICE = 8;

    /**
     * Total ticket sold
     *
     */
    static int ticketsSold = 0;

    /**
     * Total earnings
     */
    static int currentIncome = 0;


    public static void main(String[] args) {
        // Write your code here

        int[] userInput = getUserInput();
        int numRows = userInput[0];
        int numSeats = userInput[1];
        String[][] cinema = new String[numRows + 1][numSeats + 1];
        loadCinema(cinema);

        // Display the menu
        int userOption;
        while (true){
            switch (userOption = showMenu()) {
                case 1 -> printCinema(cinema);
                case 2 -> {
                    int[] seatChoice = validSeat(cinema);
                    int row = seatChoice[0];
                    int col = seatChoice[1];
                    ticketPrice(cinema, numRows, numSeats, row, col);
                }
                case 3 -> statistics(numRows, numSeats);
                case 0 -> {
                    return;
                }
            }

        }


    }

    /**
     * Load the initial status of cinema
     * @param cinema 2D array that represents a cinema.
     */
    public static void loadCinema(String[][] cinema){
        for (int i = 0; i < cinema.length; i ++){
            for (int j = 0; j <cinema[i].length; j++){
                if (i == 0 && j == 0){
                    cinema[i][j] = " ";
                }
                else if(i == 0){
                    cinema[i][j] = String.valueOf(j);
                } else if (j == 0) {
                    cinema[i][j] = String.valueOf(i);
                }
                else{
                    cinema[i][j] = "S";
                }
            }
        }


    }


    /**
     * Prints the status of the cinema
     * @param cinema a 2D array representing the cinema
     */
    public static void printCinema(String[][] cinema) {
        System.out.println("Cinema:");
        for (String[] row : cinema){
            for (String col : row){
                System.out.print(col + " ");
            }
            System.out.println();
        }


    }

    public static int[] getUserSeatChoice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a row number: ");
        int rowNum = sc.nextInt();
        System.out.println("Enter a seat number in that row: ");
        int seatNum = sc.nextInt();
        return new int[] {rowNum, seatNum};
    }

    public static int[] getUserInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        int numRows = sc.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int numRowSeats = sc.nextInt();
        return new int[] {numRows, numRowSeats};
    }

    public static int[] validSeat(String[][] cinema){
        while (true){
            int[] userChoice = getUserSeatChoice();
            int row = userChoice[0];
            int col = userChoice[1];
            // Check if the row or col is larger than the current cinema
            if (row >= cinema.length || col >= cinema[0].length){
                System.out.println("Wrong input!");
            } else if (cinema[row][col].equals("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                return new int[] {row, col};
            }
        }
    }

    public static void ticketPrice(String[][] cinema, int numRows, int numSeats, int row, int col){
        int totalSeats = numRows * numSeats;
        if (totalSeats < MAX_SEATS || row <= (numRows/2)) {
            System.out.println("Ticket price: $" + TICKET_NORMAL_PRICE);
            currentIncome += TICKET_NORMAL_PRICE;
        } else{
            System.out.println("Ticket price: $" + TICKET_LOW_PRICE);
            currentIncome += TICKET_LOW_PRICE;
        }
        cinema[row][col] = "B";
        ticketsSold += 1;

    }

    public static int showMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
        return sc.nextInt();
    }



    /**
     * Calculate the total income for the cinema
     * @param numRows Rows in the cinema
     * @param numRowSeats Seats in each row in the Cinema
     * @return income the total potential income to be earned
     */
    public static int calculateIncome(int numRows, int numRowSeats){
        int income;
        int totalSeats = numRows * numRowSeats;
        if (totalSeats >= 60){
            if (numRows % 2 == 0) {
                income = ((10 * numRows/2) + (8 * numRows/2)) * numRowSeats;
            }
            else{
                income = ((10 * (numRows - 1)/2) + ((8 * (numRows + 1)/2))) * numRowSeats;
            }
        }
        else{
            income = 10 * numRows * numRowSeats;
        }
        return income;
    }

    public static double calculatePercentage(int numRows, int numRowSeats){
        double percentage;
        if (ticketsSold != 0) {
            percentage = (double) ticketsSold / (numRows * numRowSeats) * 100;

        } else {
            return 0;
        }
        return percentage;
    }

    public static void statistics(int numRows, int numRowSeats){
        int income = calculateIncome(numRows, numRowSeats);
        System.out.println("Number of purchased tickets: " + ticketsSold);
        double percentage = calculatePercentage(numRows, numRowSeats);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + income);

    }



}