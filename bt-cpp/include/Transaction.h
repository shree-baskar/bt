#ifndef TRANSACTION_H
#define TRANSACTION_H

#include <string>
#include <chrono>

enum class Type {
    INCOME,
    EXPENSE
};

enum class Category {
    GROCERIES,
    TRANSPORT,
    RENT,
    UTILITIES,
    SHOPPING,
    EDUCATION,
    FOOD,
    HEALTH,
    ENTERTAINMENT,
    SALARY,
    INVESTMENTS,
    GIFTS,
    INTEREST
};

class Transaction {
private:
    double amount;
    std::string description;
    Category category;
    Type type;
    int id;
    std::chrono::system_clock::time_point date;

public:
    Transaction(double amount, const std::string& description, Category category, 
                const std::chrono::system_clock::time_point& date, Type type);
    Transaction(double amount, const std::string& description, Category category, Type type);
    void alterDescription(const std::string& description);
    void alterAmount(double amount);
    void alterCategory(Category category);
    void alterDate(const std::chrono::system_clock::time_point& date);
    double getAmount() const;
    double getSignedAmount() const;
    Category getCategory() const;
    Type getType() const;
    std::chrono::system_clock::time_point getDate() const;
    std::string toString() const;
};

#endif