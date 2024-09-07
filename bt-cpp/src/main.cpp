#include "Transaction.h"
#include "Tracker.h"
#include <iostream>
#include <assert.h>
#include <format>

int main() {
    ///////// TEST TRANSACTION //////////
    Transaction transaction(123.45, "groceries", Category::GROCERIES,
        std::chrono::system_clock::now(), Type::EXPENSE);

    std::cout << "\n" << transaction.toString() << "\n\n";
    assert(transaction.getCategory() == Category::GROCERIES);

    transaction.alterDescription("different");
    transaction.alterAmount(54.32);
    transaction.alterCategory(Category::EDUCATION);
    transaction.alterDate(transaction.getDate() - std::chrono::hours(24));
    
    std::cout << transaction.toString() << "\n";
    assert(transaction.getCategory() == Category::EDUCATION);

    ///////// TEST TRACKER //////////
    Tracker tracker;
    tracker.addIncome(5000.0, "Salary", Category::SALARY, std::chrono::system_clock::now());
    tracker.addExpense(100.0, "Groceries", Category::GROCERIES, std::chrono::system_clock::now());

    tracker.displayMonthlyReport(9, 2024);
    tracker.displayConciseMonthlyReport(9, 2024);
    tracker.displayYearlyReport(2024);
    tracker.displayConciseYearlyReport(2024);

    return 0;
}