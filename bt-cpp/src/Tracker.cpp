#include "Tracker.h"

#include <iostream>
#include <algorithm>
#include <iomanip>
#include <sstream>

Tracker::Tracker() {
    loadData();
}

void Tracker::addIncome(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date) {
    addTransaction(amount, description, category, date, Type::INCOME);
}

void Tracker::addExpense(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date) {
    addTransaction(amount, description, category, date, Type::EXPENSE);
}

void Tracker::addTransaction(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date, Type type) {
    Transaction transaction(amount, description, category, date, type);
    std::time_t timet = std::chrono::system_clock::to_time_t(date);
    std::tm tm = *std::localtime(&timet);
    int year = tm.tm_year + 1900;
    int month = tm.tm_mon + 1;

    transactions[year][month][type][category].push_back(transaction);
    saveData();
}

void Tracker::loadData() {
    std::cout << "TODO" << std::endl;
}

void Tracker::saveData() {
    std::cout << "TODO" << std::endl;
}

std::unordered_map<std::string, int> Tracker::displayMonthlyReport(int month, int year) {
    return displayMonthlyReportHelper(month, year, false);
}

std::unordered_map<std::string, int> Tracker::displayConciseMonthlyReport(int month, int year) {
    return displayMonthlyReportHelper(month, year, true);
}

std::unordered_map<std::string, int> Tracker::displayMonthlyReportHelper(int month, int year, bool concise) {
    std::unordered_map<std::string, int> retMap;
    retMap["Income"] = 0;
    retMap["Expenses"] = 0;
    
    std::cout << "\nMonthly Report for " << intToMonth(month) << " " << std::to_string(year) << "\n";
    std::cout << "--------------------------------------------" << "\n";

    auto yearTransactions = transactions.find(year);
    if (yearTransactions == transactions.end()) {
        std::cout << "\nNo transactions for the year " << year << "\n";
        return retMap;
    }

    auto monthTransactions = yearTransactions->second.find(month);
    if (monthTransactions == yearTransactions->second.end()) {
        std::cout << "\nNo transactions for the month " << intToMonth(month) << "\n";
        return retMap;
    }
    
    auto incomeByCategory = monthTransactions->second.find(Type::INCOME) != monthTransactions->second.end()
                            ? monthTransactions->second.at(Type::INCOME)
                            : std::unordered_map<Category, std::vector<Transaction>>();

    auto expenseByCategory = monthTransactions->second.find(Type::EXPENSE) != monthTransactions->second.end()
                                ? monthTransactions->second.at(Type::EXPENSE)
                                : std::unordered_map<Category, std::vector<Transaction>>();
    
    int incomeTotal = 0, expenseTotal = 0;

    if (concise) {
        incomeTotal = gatherTotal(incomeByCategory);
        expenseTotal = gatherTotal(expenseByCategory);
    } else {
        std::cout << "\nINCOME:\n";
        std::cout << "--------------------------------------------\n";
        incomeTotal = displayByCategory(incomeByCategory);
        std::cout << "--------------------------------------------\n";
        std::cout << "\nEXPENSES:\n";
        std::cout << "--------------------------------------------\n";
        expenseTotal = displayByCategory(expenseByCategory);
        std::cout << "--------------------------------------------\n\n";
    }

    std::cout << "\nTotal income: " << std::to_string(incomeTotal) << "\nTotal expenses: " << std::to_string(expenseTotal) << "\nNet Income: " << std::to_string(incomeTotal - expenseTotal) << "\n\n";
    retMap["Income"] = incomeTotal;
    retMap["Expenses"] = expenseTotal;
    return retMap;
}

int Tracker::gatherTotal(std::unordered_map<Category, std::vector<Transaction>> transactionsByCategory) {
    int total = 0;
    for (const auto& [category, transactions] : transactionsByCategory) {
        for (const auto& transaction : transactions) {
            total += transaction.getAmount();
        }
    }

    return total;
}

int Tracker::displayByCategory(std::unordered_map<Category, std::vector<Transaction>> transactionsByCategory) {
    if (transactionsByCategory.empty()) {
        std::cout << "No transactions found.\n";
        return 0;
    }

    int total = 0;
    for (const auto& [category, transactions] : transactionsByCategory) {
        std::cout << categoryToString(category) << ":\n";
        if (transactions.empty()) {
            std::cout << "No transactions for this category\n";
            continue;
        }

        auto sortedTransactions = transactions;
        std::sort(sortedTransactions.begin(), sortedTransactions.end(),
            [](const Transaction& t1, const Transaction& t2) {
                return t1.getDate() > t2.getDate();
            });

        for (const auto& transaction : sortedTransactions) {
            std::cout << transaction.toString() << "\n";
            total += transaction.getAmount();
        }
    }

    return total;
}

std::string Tracker::categoryToString(Category category) {
    switch(category) {
        case Category::GROCERIES: return "Groceries";
        case Category::TRANSPORT: return "Transport";
        case Category::RENT: return "Rent";
        case Category::UTILITIES: return "Utilities";
        case Category::SHOPPING: return "Shopping";
        case Category::EDUCATION: return "Education";
        case Category::FOOD: return "Food";
        case Category::HEALTH: return "Health";
        case Category::ENTERTAINMENT: return "Entertainment";
        case Category::SALARY: return "Salary";
        case Category::INVESTMENTS: return "Investments";
        case Category::GIFTS: return "Gifts";
        case Category::INTEREST: return "Interest";
        default: return "Unknown";
    }
}

std::string Tracker::intToMonth(int month) {
    switch (month) {
        case 1:
            return "January";
        case 2:
            return "February";
        case 3:
            return "March";
        case 4:
            return "April";
        case 5:
            return "May";
        case 6:
            return "June";
        case 7:
            return "July";
        case 8:
            return "August";
        case 9:
            return "September";
        case 10:
            return "October";
        case 11:
            return "November";
        case 12:
            return "December";
    }

    return "Invalid";
}

void Tracker::displayYearlyReport(int year) {
    displayYearlyReportHelper(year, false);
}

void Tracker::displayConciseYearlyReport(int year) {
    displayYearlyReportHelper(year, true);
}

void Tracker::displayYearlyReportHelper(int year, bool concise) {
    int totalIncome = 0;
    int totalExpenses = 0;

    for (int i = 1; i <= NUM_MONTHS; i++) {
        std::unordered_map<std::string, int> totals = concise ? displayConciseMonthlyReport(i, year) : displayMonthlyReport(i, year);
        totalIncome += totals.at("Income");
        totalExpenses += totals.at("Expenses");
    }

    std::cout << "\n--------------------------------------------\n";
    std::cout << "--------------------------------------------\n\n";

    std::cout << "Average Income: " << std::to_string(totalIncome / (float) NUM_MONTHS) << "\n";
    std::cout << "Average Expenses: " << std::to_string(totalExpenses / (float) NUM_MONTHS) << "\n";
    std::cout << "Total Income: " << std::to_string(totalIncome) << "\nTotal Expenses: " << std::to_string(totalExpenses) << "\n";
    std::cout << "Net Income: " << std::to_string(totalIncome - totalExpenses) << "\n\n";
}
