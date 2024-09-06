#include "Transaction.h"
#include <iostream>
#include <assert.h>

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

    return 0;
}