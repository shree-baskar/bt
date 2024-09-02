package bt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        String helpMsg = "    Actions:\n    -------\n"
            + "\t+ <amount> <category> <yyyy-MM-dd> <one-line description> : add income\n"
            + "\t- <amount> <category> <yyyy-MM-dd> <one-line description> : add expense\n"
            + "\trm <month> <year> <true/false download> : view concise monthly report\n"
            + "\try <year> <true/false download> : view concise yearly report\n"
            + "\trm+ <month> <year> <true/false download> : view detailed monthly report\n"
            + "\try+ <year> <true/false download> : view detailed yearly report\n\n"
            + "    Available income categories: SALARY, INVESTMENTS, GIFTS, INTEREST\n"
            + "    Available expense categories: GROCERIES, TRANSPORT, RENT, UTILITIES, SHOPPING, EDUCATION, FOOD, HEALTH, ENTERTAINMENT\n\n"
            + "    Type 'h' to view this menu again\n    Type 'q' to save and exit\n\n";

        System.out.println(helpMsg);

        Tracker tracker = new Tracker();

        while(true) {
            System.out.print("Enter an Action: ");
            input = scanner.nextLine();

            String[] arguments = input.split("\\s+", 5);
            String action = arguments[0];

            switch(action) {
                case "q":
                    System.out.println("Saving and exiting...");
                    scanner.close();
                    return;
                case "h":
                    System.out.println(helpMsg);
                    break;
                case "+":
                    tracker.addIncome(Double.valueOf(arguments[1]), arguments[4], IncomeCategory.valueOf(arguments[2]), LocalDate.parse(arguments[3]));
                    break;
                case "-":
                    tracker.addExpense(Double.valueOf(arguments[1]), arguments[4], ExpenseCategory.valueOf(arguments[2]), LocalDate.parse(arguments[3]));
                    break;
                case "rm":
                    Main.printOrSave(Boolean.parseBoolean(arguments[3]), () -> tracker.displayConciseMonthlyReport(Integer.valueOf(arguments[1]), Integer.valueOf(arguments[2])));
                    break;
                case "ry":
                    Main.printOrSave(Boolean.parseBoolean(arguments[2]), () -> tracker.displayConciseYearlyReport(Integer.valueOf(arguments[1])));
                    break;
                case "rm+":
                    Main.printOrSave(Boolean.parseBoolean(arguments[3]), () -> tracker.displayMonthlyReport(Integer.valueOf(arguments[1]), Integer.valueOf(arguments[2])));
                    break;
                case "ry+":
                    Main.printOrSave(Boolean.parseBoolean(arguments[2]), () -> tracker.displayYearlyReport(Integer.valueOf(arguments[1])));
                    break;
                default:
                    System.out.println("Please enter a valid action.\n");
                    break;
            }
        }
    }

    public static void printOrSave(boolean save, Runnable displayReport) {
        PrintStream stdOut = System.out;
        try {
            if (save) {
                FileOutputStream fos = new FileOutputStream(new File("report.txt"), false);
                PrintStream fileOut = new PrintStream(fos);
                System.setOut(fileOut);
            }

            displayReport.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(stdOut);
        }
    }
}
