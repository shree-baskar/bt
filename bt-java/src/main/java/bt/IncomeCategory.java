package bt;

public enum IncomeCategory implements Category {
    SALARY("Salary"),
    INVESTMENTS("Investments"),
    GIFTS("Gifts"),
    INTEREST("Interest");

    private final String name;

    private IncomeCategory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
