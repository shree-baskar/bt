package bt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrackerTest {
    private Tracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new Tracker();
    }

    @Test
    void testAddIncome() {
        Map<String, Integer> report = tracker.displayMonthlyReport(9, 2024);
        int oldIncome = report.get("Income");
        int oldExpense = report.get("Expenses");
        tracker.addIncome(500.0, "Salary", IncomeCategory.SALARY, LocalDate.of(2024, 9, 1));
        report = tracker.displayMonthlyReport(9, 2024);
        assertEquals(500 + oldIncome, report.get("Income"));
        assertEquals(0 + oldExpense, report.get("Expenses"));
    }

    @Test
    void testAddExpense() {
        Map<String, Integer> report = tracker.displayMonthlyReport(9, 2024);
        int oldExpense = report.get("Expenses");
        int oldIncome = report.get("Income");
        tracker.addExpense(100, "Groceries", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 2));
        report = tracker.displayMonthlyReport(9, 2024);
        assertEquals(oldIncome, report.get("Income"));
        assertEquals(100 + oldExpense, report.get("Expenses"));
    }

    @Test
    void testDisplayMonthlyReport() {
        Map<String, Integer> report = tracker.displayMonthlyReport(9, 2024);
        int oldIncome = report.get("Income");
        int oldExpense = report.get("Expenses");
        tracker.addIncome(100.0, "Bonus", IncomeCategory.SALARY, LocalDate.of(2024, 9, 4));
        tracker.addIncome(3000.0, "Monthly Salary", IncomeCategory.SALARY, LocalDate.of(2024, 9, 10));
        tracker.addIncome(50, "Interest", IncomeCategory.INTEREST, LocalDate.of(2024, 9, 1));
        tracker.addIncome(20, "Birthday", IncomeCategory.GIFTS, LocalDate.of(2024, 9, 15));
        tracker.addExpense(112, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 1));
        tracker.addExpense(83, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 8));
        tracker.addExpense(152, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 15));
        tracker.addExpense(57, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 22));
        tracker.addExpense(96, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 9, 29));
        tracker.addExpense(2000, "Rent", ExpenseCategory.RENT, LocalDate.of(2024, 9, 20));
        tracker.addExpense(80, "Doctor Appt", ExpenseCategory.HEALTH, LocalDate.of(2024, 9, 18));
        tracker.addExpense(50, "Clothes", ExpenseCategory.SHOPPING, LocalDate.of(2024, 9, 5));
        tracker.addIncome(100.0, "Bonus", IncomeCategory.SALARY, LocalDate.of(2024, 10, 4));
        tracker.addIncome(3000.0, "Monthly Salary", IncomeCategory.SALARY, LocalDate.of(2024, 10, 10));
        tracker.addIncome(50, "Interest", IncomeCategory.INTEREST, LocalDate.of(2024, 10, 1));
        tracker.addIncome(20, "Birthday", IncomeCategory.GIFTS, LocalDate.of(2024, 10, 15));
        tracker.addExpense(112, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 10, 1));
        tracker.addExpense(83, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 10, 8));
        tracker.addExpense(152, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 10, 15));
        tracker.addExpense(57, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 10, 22));
        tracker.addExpense(96, "Weekly Grocery", ExpenseCategory.GROCERIES, LocalDate.of(2024, 10, 29));
        tracker.addExpense(2000, "Rent", ExpenseCategory.RENT, LocalDate.of(2024, 10, 20));
        tracker.addExpense(80, "Doctor Appt", ExpenseCategory.HEALTH, LocalDate.of(2024, 10, 18));
        tracker.addExpense(50, "Clothes", ExpenseCategory.SHOPPING, LocalDate.of(2024, 10, 5));
        report = tracker.displayMonthlyReport(9, 2024);
        assertEquals(3170 + oldIncome, report.get("Income"));
        assertEquals(2630 + oldExpense, report.get("Expenses"));
    }

    @Test
    void testDisplayYearlyReport() {
        tracker.addIncome(1200.0, "Annual Salary", IncomeCategory.SALARY, LocalDate.of(2024, 1, 1));
        tracker.addExpense(600.0, "Rent", ExpenseCategory.RENT, LocalDate.of(2024, 1, 15));
        tracker.displayYearlyReport(2024);
    }

    @Test
    void testDisplayConciseMonthlyReport() {
        Map<String, Integer> report = tracker.displayConciseMonthlyReport(9, 2024);
        int oldIncome = report.get("Income");
        int oldExpense = report.get("Expenses");
        tracker.addIncome(400.0, "Freelance", IncomeCategory.SALARY, LocalDate.of(2024, 9, 10));
        tracker.addExpense(200.0, "Utilities", ExpenseCategory.UTILITIES, LocalDate.of(2024, 9, 15));
        report = tracker.displayConciseMonthlyReport(9, 2024);
        assertEquals(400 + oldIncome, report.get("Income"));
        assertEquals(200 + oldExpense, report.get("Expenses"));
    }

    @Test
    void testDataStore() {
        Map<String, Integer> report = tracker.displayMonthlyReport(9, 2024);
        int oldIncome = report.get("Income");
        int oldExpense = report.get("Expenses");
        tracker.addIncome(1000.0, "Investment", IncomeCategory.INVESTMENTS, LocalDate.of(2024, 9, 20));
        tracker.addExpense(500.0, "Travel", ExpenseCategory.TRANSPORT, LocalDate.of(2024, 9, 22));

        Tracker newTracker = new Tracker();
        report = newTracker.displayMonthlyReport(9, 2024);
        assertEquals(1000 + oldIncome, report.get("Income"));
        assertEquals(500 + oldExpense, report.get("Expenses"));
    }
}
