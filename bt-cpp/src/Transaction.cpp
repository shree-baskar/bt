#include "Transaction.h"

#include <sstream>
#include <iomanip>

Transaction::Transaction(double amount, const std::string& description, Category category,
                        const std::chrono::system_clock::time_point& date, Type type)
    : amount(amount), description(description), category(category), date(date), type(type) {}

Transaction::Transaction(double amount, const std::string& description, Category category, Type type)
    : amount(amount), description(description), category(category), type(type) {
        date = std::chrono::system_clock::now();
}

void Transaction::alterDescription(const std::string& description) {
    this->description = description;
}

void Transaction::alterAmount(double amount) {
    this->amount = amount;
}

void Transaction::alterCategory(Category category) {
    this->category = category;
}

void Transaction::alterDate(const std::chrono::system_clock::time_point& date) {
    this->date = date;
}

double Transaction::getAmount() const {
    return amount;
}

double Transaction::getSignedAmount() const {
    return (type == Type::INCOME) ? amount : -amount;
}

Category Transaction::getCategory() const {
    return category;
}

std::chrono::system_clock::time_point Transaction::getDate() const {
    return date;
}

std::string Transaction::toString() const {
    std::ostringstream display;
    std::time_t time = std::chrono::system_clock::to_time_t(date);
    std::tm time2 = *std::localtime(&time);
    display << std::put_time(&time2, "%d-%m-%Y") << "\n";
    display << ((type == Type::EXPENSE) ? "- " : "+ ") << amount << "\n" << description << "\n";
    return display.str();
}