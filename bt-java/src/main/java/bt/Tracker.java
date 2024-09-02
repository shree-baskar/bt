package bt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker {
    private Map<Integer, Map<Integer, Map<Type, Map<Category, List<Transaction>>>>> transactions;
    private static final String DATA = "transactions.ser";
    private static final Integer NUM_MONTHS = 12;

    public Tracker() {
        this.transactions = new HashMap<>();
        loadData();
    }

    /**
     * Adds an income to the tracker
     * @param amount        the amount of income
     * @param description   the description of the transaction
     * @param category      the category from a predefined list
     * @param date          date of the transaction as a {@link LocalDate} object
     */
    public void addIncome(double amount, String description, Category category, LocalDate date) {
        addTransaction(amount, description, category, date, Type.INCOME);
    }

    /**
     * Adds an expense to the tracker
     * @param amount        the amount spent
     * @param description   the description of the transaction
     * @param category      the category from a predefined list
     * @param date          date of the transaction as a {@link LocalDate} object
     */
    public void addExpense(double amount, String description, Category category, LocalDate date) {
        addTransaction(amount, description, category, date, Type.EXPENSE);
    }

    private void addTransaction(double amount, String description, Category category, LocalDate date, Type type) {
        Transaction newTransaction = new Transaction(amount, description, category, date, type);
        int year = date.getYear();
        int month = date.getMonthValue();
        
        transactions
            .computeIfAbsent(year, k -> new HashMap<>())
            .computeIfAbsent(month, k -> new HashMap<>())
            .computeIfAbsent(type, k -> new HashMap<>())
            .computeIfAbsent(category, k -> new ArrayList<>())
            .add(newTransaction);

        saveData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (FileInputStream fileIn = new FileInputStream(DATA);
        ObjectInputStream in = new ObjectInputStream(fileIn)) {
            transactions = (Map<Integer, Map<Integer, Map<Type, Map< Category, List<Transaction>>>>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void saveData() {
        try (FileOutputStream fileOut = new FileOutputStream(DATA);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a report of the current month
     */
    public Map<String, Integer> displayMonthlyReport() {
        return displayMonthlyReport(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    /**
     * Displays a report of the specified month in the current year
     */
    public Map<String, Integer> displayMonthlyReport(Integer month) {
        return displayMonthlyReport(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    /**
     * Displays a report of the specified month and year
     */
    public Map<String, Integer> displayMonthlyReport(Integer month, Integer year) {
        return displayMonthlyReportHelper(month, year, false);
    }

    private Map<String, Integer> displayMonthlyReportHelper(Integer month, Integer year, boolean concise) {
        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("Income", 0);
        retMap.put("Expenses", 0);

        System.out.println("Monthly Report for " + Month.of(month).name() + " " + year);
        System.out.println("--------------------------------------------");

        Map<Integer, Map<Type, Map< Category, List<Transaction>>>> yearTransactions = transactions.get(year);
        if (yearTransactions == null) {
            System.out.println("\nNo transactions for the year " + year);
            return retMap;
        }

        Map<Type, Map<Category, List<Transaction>>> monthTransactions = yearTransactions.get(month);
        if (monthTransactions == null) {
            System.out.println("\nNo transactions for the month " + Month.of(month));
            return retMap;
        }

        // display transactions by category in chronological order
        Map<Category, List<Transaction>> incomeByCategory = monthTransactions.getOrDefault(Type.INCOME, null);
        Map<Category, List<Transaction>> expensesByCategory = monthTransactions.getOrDefault(Type.EXPENSE, null);

        int incomeTotal = 0, expenseTotal = 0;

        if (concise) {
            incomeTotal = gatherTotal(incomeByCategory);
            expenseTotal = gatherTotal(expensesByCategory);

        } else {
            System.out.println("\nINCOME:");
            System.out.println("--------------------------------------------");
            incomeTotal = displayByCategory(incomeByCategory);
            System.out.println("--------------------------------------------");
            System.out.println("\nEXPENSES:");
            System.out.println("--------------------------------------------");
            expenseTotal = displayByCategory(expensesByCategory);
            System.out.println("--------------------------------------------");
        }

        System.out.println("\nTotal income: " + incomeTotal + "\nTotal expenses: " + expenseTotal + "\nNet Income: " + (incomeTotal - expenseTotal));
        retMap.put("Income", incomeTotal);
        retMap.put("Expenses", expenseTotal);
        return retMap;
    }

    /**
     * displays transactions by category in chronological order
     * @param transactionsByCategory a map containing a list of transactions per category
     * @return the total amount processed
     */
    private int displayByCategory(Map<Category, List<Transaction>> transactionsByCategory) {
        if (transactionsByCategory == null) {
            System.out.println("No transactions found.");
            return 0;
        }

        int totalAmount = 0;
        for (Category category : transactionsByCategory.keySet()) {
            System.out.println(category.getName() + ":");
            List<Transaction> categoryTransactions = transactionsByCategory.get(category);
            if (categoryTransactions == null) {
                System.out.println("No transactions for this category");
                continue;
            }

            categoryTransactions.sort(Comparator.comparing(Transaction::getDate));
            for (Transaction transaction : categoryTransactions) {
                System.out.println(transaction.toString());
                totalAmount += transaction.getAmount();
            }
        }

        return totalAmount;
    }

    private int gatherTotal(Map<Category, List<Transaction>> transactionsByCategory) {
        int total = 0;
        if (transactionsByCategory == null) {
            return total;
        }

        for (List<Transaction> transactions : transactionsByCategory.values()) {
            for (Transaction transaction : transactions) {
                total += transaction.getAmount();
            }
        }

        return total;
    }

    /**
     * Displays a report for the current year
     */
    public void displayYearlyReport() {
        displayYearlyReport(LocalDate.now().getYear());
    }

    /**
     * Displays a report for the specified year
     */
    public void displayYearlyReport(Integer year) {
        displayYearlyReportHelper(year, false);
    }

    private void displayYearlyReportHelper(Integer year, boolean concise) {
        int totalIncome = 0;
        int totalExpenses = 0;
        for (int i = 1; i <= NUM_MONTHS; i++) {
            Map<String, Integer> totals = concise ? displayConciseMonthlyReport(i, year) : displayMonthlyReport(i, year);
            totalIncome += totals.get("Income");
            totalExpenses += totals.get("Expenses");
        }

        System.out.println("\n--------------------------------------------");
        System.out.println("--------------------------------------------");
        System.out.println("Average Income: " + (totalIncome / NUM_MONTHS)
            + "\nAverage Expenses: " + (totalExpenses / NUM_MONTHS)
            + "\nTotal Income: " + totalIncome
            + "\nTotal Expenses: " + totalExpenses
            + "\nNet Income: " + (totalIncome - totalExpenses));
    }

    /**
     * Displays a concise report for the current month
     */
    public Map<String, Integer> displayConciseMonthlyReport() {
        return displayConciseMonthlyReport(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    /**
     * Displays a concise report for the specified month in the current year
     */
    public Map<String, Integer> displayConciseMonthlyReport(int month) {
        return displayConciseMonthlyReport(month, LocalDate.now().getYear());
    }

    /**
     * Displays a concise report for the specified month and year
     */
    public Map<String, Integer> displayConciseMonthlyReport(int month, int year) {
        return displayMonthlyReportHelper(month, year, true);
    }

    /**
     * Displays a concise report for the current year
    */
    public void displayConciseYearlyReport() {
        displayConciseYearlyReport(LocalDate.now().getYear());
    }

    /**
     * Displays a concise report for the specified year
    */
    public void displayConciseYearlyReport(int year) {
        displayYearlyReportHelper(year, true);
    }
}
