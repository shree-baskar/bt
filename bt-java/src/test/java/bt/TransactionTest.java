package bt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {
    private Transaction transaction;
   
    @BeforeEach
    void setUp() {
        transaction = new Transaction(100.0, "Test", ExpenseCategory.FOOD, LocalDate.of(2024, 9, 1), Type.EXPENSE);
    }

    @Test
    void testCreateTransaction() {
        assertEquals(100.0, transaction.getAmount());
        assertEquals(-100.0, transaction.getSignedAmount());
        assertEquals(ExpenseCategory.FOOD, transaction.getCategory());
        assertEquals(LocalDate.of(2024, 9, 1), transaction.getDate());
        assertEquals(Type.EXPENSE, transaction.getType());
    }

    @Test
    void testCreateTransactionNow() {
        transaction = new Transaction(100.0, "Test", ExpenseCategory.FOOD, Type.EXPENSE);
        assertEquals(100.0, transaction.getAmount());
        assertEquals(-100.0, transaction.getSignedAmount());
        assertEquals(ExpenseCategory.FOOD, transaction.getCategory());
        assertEquals(LocalDate.now(), transaction.getDate());
        assertEquals(Type.EXPENSE, transaction.getType());
    }

@Test
    void testAlterDescription() {
        String expected = "2024-09-01\n- 100.0\nTest\n";
        assertEquals(expected, transaction.toString());

        transaction.alterDescription("New");
        expected = "2024-09-01\n- 100.0\nNew\n";
        assertEquals(expected, transaction.toString());
    }

    @Test
    void testAlterAmount() {
        transaction.alterAmount(200.0);
        assertEquals(200.0, transaction.getAmount());
    }

    @Test
    void testAlterCategory() {
        transaction.alterCategory(ExpenseCategory.ENTERTAINMENT);
        assertEquals(ExpenseCategory.ENTERTAINMENT, transaction.getCategory());
    }

    @Test
    void testAlterDate() {
        LocalDate newDate = LocalDate.of(2024, 10, 1);
        transaction.alterDate(newDate);
        assertEquals(newDate, transaction.getDate());
    }

    @Test
    void testGetSignedAmountForIncome() {
        Transaction income = new Transaction(150.0, "Test", IncomeCategory.SALARY, Type.INCOME);
        assertEquals(150.0, income.getSignedAmount());
    }

    @Test
    void testToString() {
        String expected = "2024-09-01\n- 100.0\nTest\n";
        assertEquals(expected, transaction.toString());
    }

    @Test
    void testNotEquals() {
        Transaction other = new Transaction(200.0, "New", ExpenseCategory.FOOD, LocalDate.of(2024, 9, 1), Type.INCOME);
        assertNotEquals(transaction, other);
    }
}
