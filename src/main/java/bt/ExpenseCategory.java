package bt;

public enum ExpenseCategory implements Category {
    GROCERIES("Groceries"),
    TRANSPORT("Transport"),
    RENT("Rent"),
    UTILITIES("Utilities"),
    SHOPPING("Shopping"),
    EDUCATION("Education"),
    FOOD("Food"),
    HEALTH("Health"),
    ENTERTAINMENT("Entertainment");

    private final String name;

    private ExpenseCategory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
