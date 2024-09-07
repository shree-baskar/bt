#ifndef TRACKER_H
#define TRACKER_H

#include "Transaction.h"
#include <unordered_map>
#include <vector>


class Tracker {
private:
    //static const std::string DATA = "transactions.dat";
    static const int NUM_MONTHS = 12;
    std::unordered_map<int, std::unordered_map<int, std::unordered_map<Type, std::unordered_map<Category, std::vector<Transaction>>>>> transactions;

    void loadData();
    void saveData();
    void addTransaction(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date, Type type);
    std::unordered_map<std::string, int> displayMonthlyReportHelper(int month, int year, bool concise);
    int displayByCategory(std::unordered_map<Category, std::vector<Transaction>> transactionsByCategory);
    int gatherTotal(std::unordered_map<Category, std::vector<Transaction>> transactionsByCategory);
    void displayYearlyReportHelper(int year, bool concise);
    std::string categoryToString(Category category);
    std::string intToMonth(int month);

public:
    Tracker();
    void addIncome(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date);
    void addExpense(double amount, std::string description, Category category, const std::chrono::system_clock::time_point& date);
    std::unordered_map<std::string, int> displayMonthlyReport(int month, int year);
    std::unordered_map<std::string, int> displayConciseMonthlyReport(int month, int year);
    void displayYearlyReport(int year);
    void displayConciseYearlyReport(int year);
};

#endif