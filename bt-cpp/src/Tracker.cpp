#include "Tracker.h"

#include <iostream>

Tracker::Tracker() {
    loadData();
}

void Tracker::addIncome(double amount, std::string& description, Category category, std::chrono::system_clock::time_point& date) {
    addTransaction(amount, description, category, date, Type::INCOME);
}

void Tracker::addExpense(double amount, std::string& description, Category category, std::chrono::system_clock::time_point& date) {
    addTransaction(amount, description, category, date, Type::EXPENSE);
}

void Tracker::addTransaction(double amount, std::string& description, Category category, std::chrono::system_clock::time_point& date, Type type) {
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

std::unordered_map<std::string, int> displayMonthlyReport(int month, int year) {
    return displayMonthlyReportHelper(month, year, false);
}

std::unordered_map<std::string, int> displayConciseMonthlyReport(int month, int year) {
    return displayMonthlyReportHelper(month, year, true);
}

std::unordered_map<std::string, int> displayMonthlyReportHelper(int month, int year, bool concise) {
    std::unordered_map<std::string, int> retMap;
    retMap["Income"] = 0;
    retMap["Expenses"] = 0;
    
    std::cout << "\nMOnthly Report for " << "TODO:Month" << " "


    std::cout << "TODO" << std::endl;
}


// TODO: all display functions

