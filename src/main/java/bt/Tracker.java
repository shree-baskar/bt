package bt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Tracker {
    private Map<Integer, Map<Integer, Map<Type, Map<Category, List<Transaction>>>>> transactions;
    private static final String DATA = "transactions.ser";
    private static final Integer NUM_MONTHS = 12;

    public Tracker() {
    }

    /**
     * Adds an income to the tracker
     * @param amount        the amount of income
     * @param description   the description of the transaction
     * @param category      the category from a predefined list
     * @param date          date of the transaction as a {@link LocalDate} object
     */
    public void addIncome(double amount, String description, Category category, LocalDate date) {
    }

    /**
     * Adds an expense to the tracker
     * @param amount        the amount spent
     * @param description   the description of the transaction
     * @param category      the category from a predefined list
     * @param date          date of the transaction as a {@link LocalDate} object
     */
    public void addExpense(double amount, String description, Category category, LocalDate date) {
    }

    /**
     * Displays a report of the current month
     */
    public Map<String, Integer> displayMonthlyReport() {
        return null;
    }

    /**
     * Displays a report of the specified month in the current year
     */
    public Map<String, Integer> displayMonthlyReport(Integer month) {
        return null;
    }

    /**
     * Displays a report of the specified month and year
     */
    public Map<String, Integer> displayMonthlyReport(Integer month, Integer year) {
        return null;
    }

    /**
     * Displays a report for the current year
     */
    public void displayYearlyReport() {
    }

    /**
     * Displays a report for the specified year
     */
    public void displayYearlyReport(Integer year) {
    }

    /**
     * Displays a concise report for the current month
     */
    public Map<String, Integer> displayConciseMonthlyReport() {
        return null;
    }

    /**
     * Displays a concise report for the specified month in the current year
     */
    public Map<String, Integer> displayConciseMonthlyReport(int month) {
        return null;
    }

    /**
     * Displays a concise report for the specified month and year
     */
    public Map<String, Integer> displayConciseMonthlyReport(int month, int year) {
        return null;
    }

    /**
     * Displays a concise report for the current year
    */
    public void displayConciseYearlyReport() {
    }

    /**
     * Displays a concise report for the specified year
    */
    public void displayConciseYearlyReport(int year) {
    }
}
