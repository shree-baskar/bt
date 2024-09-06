package bt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

enum Type {
    INCOME,
    EXPENSE
}

public class Transaction implements Serializable {
    private double amount;
    private String description;
    private Category category;
    private LocalDate date;
    private Type type;
    private UUID id;

    private static final long serialVersionUID = 1L;

    public Transaction(double amount, String description, Category category, LocalDate date, Type type) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.type = type;
        this.id = UUID.randomUUID();
    }

    public Transaction(double amount, String description, Category category, Type type) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = LocalDate.now();
        this.type = type;
        this.id = UUID.randomUUID();
    }

    public void alterDescription(String description) {
        this.description = description;
    }

    public void alterAmount(double amount) {
        this.amount = amount;
    }

    public void alterCategory(Category category) {
        this.category = category;
    }

    public void alterDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public double getSignedAmount() {
        return (type == Type.INCOME) ? amount : -1 * amount;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        String display = date.toString() + "\n";
        display += (this.type == Type.EXPENSE) ? "- " : "+ ";
        display += amount + "\n" + description + "\n";
        return display;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj.getClass() == this.getClass()) {
            if (this.id == ((Transaction) obj).id) return true;
        }
        return false;
    }
}
