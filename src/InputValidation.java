import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class InputValidation {
    public static int getInteger(int lowerBound, int upperBound) {
        int myInt;
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                myInt = input.nextInt();
                if (myInt > upperBound || myInt < lowerBound)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid Input Please Try again");
                input.nextLine(); // - Consume EOL Char
                continue;
            }
            return myInt;
        }
    }

    public static double getPrice() {
        double myDouble;
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                myDouble = input.nextDouble();
                if (myDouble <= 0)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid Price Please Try again");
                input.nextLine(); // - Consume the EOL Character
                continue;
            }
            return myDouble;
        }
    }

    public static String getValidDate() {
        Scanner input = new Scanner(System.in);
        String date;
        while (true) {
            date = input.nextLine();
            if (isValidDate(date)) {
                return date;
            } else {
                System.out.println("Please insert the valid date with (yyyy-mm-dd) format ");
            }
        }
    }

    public static boolean isValidDate(String dateStr) {
        String formatStr = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
        LocalDate today = LocalDate.now();
        try {
            // - Parse the input date with the specified format
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return !date.isBefore(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String getValidAddress() {
        Scanner input = new Scanner(System.in);
        String address;
        while (true) {
            address = input.nextLine();
            if (isValidAddress(address)) {
                return address;
            } else {
                System.out.println("Please insert the proper address");
            }
        }
    }

    public static boolean isValidAddress(String address) {
        String pattern = "^(\\d+\\s+)?[A-Za-z0-9]+(\\s+[A-Za-z0-9]+)*(,\\s*[A-Za-z0-9]+)*(,\\s*[A-Za-z0-9]+)*(,\\s*[A-Za-z]+,\\s*\\d{5})*$";
        return Pattern.matches(pattern, address);
    }

    public static boolean checkStock(String itemID, int requestStock) {
        FileHandling itemFiles = new FileHandling("Item.txt");

        int stock = Integer.parseInt(itemFiles.searchRow("itemID", itemID).split(",")[2]);
        return requestStock <= stock;
    }

}
